import org.apache.spark.sql.SparkSession

object IntroSparkSQL {

    def main(args: Array[String]): Unit = {

        val conexion = SparkSession.builder()
                                    .appName("IntroSparkSQL")
                                    .master("local[2]")
                                    .getOrCreate()
        val personas = List(
            Persona("Menchu",   45, "Madrid", "12345678A"),
            Persona("Felipe",   20, "Madrid", "230000T"),
            Persona("Ana",      30, "Bilbao", "23.000.023T"),
            Persona("Feredico", 70, "Bilbao", "2300000T")
        )
        // En SparkSQL ya no se trabajan con RDDs, sino con DataFrames (similares a los dataframes de Pandas)
        // Un dataframe, representa una tabla de datos con filas y columnas
        val datos = conexion.createDataFrame(personas) // Crea un dataframe a partir de una lista de objetos

        datos.show()                                   // Muestra el contenido del dataframe en forma tabular
        datos.printSchema()                            // Los dataframes tienen asociado un Esquema de datos (tipos de datos de cada columna y si acepta nulos)
        // Spark por defecto crea un esquema que infiere los tipos de datos a partir de los datos que se le pasan
        // Puede ser que en algunos casos no infiera correctamente los tipos de datos, o me interese cambiar algo.
        // Por ejemplo, quiero que el campo nombre no acepte nulos:
        // datos.schema.add("nombre", "string", nullable = false) // Modifico el esquema para que el campo nombre no acepte nulos

        // Qué podemos hacer con un dataframe?
        // Tenemos ahora una sintaxis muy inspirada en SQL para hacer consultas sobre los datos.. pero desde sacala
        datos.select("nombre", "edad").show()         // Selecciona solo las columnas nombre y edad
        datos.filter( datos("edad") > 30 ).show()     // Filtra las filas donde la edad es mayor a 30
        datos.groupBy("provincia").count().show()          // Agrupa por provincia y cuenta el número de personas por provincia
        datos.groupBy("provincia").avg("edad").show()      // Agrupa por provincia y calcula la edad media por provincia

        // Todas esas funciones que os enseñaba, admiten pasarle las columnas de 2 formas:
        // - Por su nombre como string
        // - Usando la función col("nombre_columna") que devuelve un objeto columna. Esa función hay que importarla
        import org.apache.spark.sql.functions._
        datos.select( col("nombre"), col("edad") ).show()
        datos.filter( col("edad") > 30 ).show()
        datos.groupBy( col("provincia") ).count().show()
        // En esos caso no interesa usar la función col... es escribir más por escribir. No tiene sentido
        // Cuando tiene sentido usar col? Cuando quiero hacer operaciones más complejas con las columnas
        // Quiero el nombre, pero en mayúsculas
        // Quiero la edad, pero sumandole 10 años
        datos.select( upper(col("nombre")).as("NOMBRE_EN_MAYUSCULAS"), (col("edad") + 10).as("EDAD_MAS_10") ).show()
        /*
            El equivalente en SQL sería:
            SELECT UPPER(nombre) AS NOMBRE_EN_MAYUSCULAS, (edad + 10) AS EDAD_MAS_10
            FROM personas;
        */

        // Funciones que podemos aplicar sobre columnas hay un montón:
        // https://spark.apache.org/docs/latest/api/sql/index.html#available-functions
        // upper, lower, substring, length, trim, ltrim, rtrim, concat, concat_ws, lit, abs, sqrt, round, ceil, floor, rand, randn, date_format, year, month, dayofmonth, current_date, current_timestamp, ...

        // Esto ya nos permite quitar de en medio parte de la complejidad de los RDDs: MAP-REDUCE
        // Pero internamente, Spark SQL lo que hace es transformar esas operaciones en operaciones de map-reduce sobre RDDs

        // Pero la gran gracia de SparkSQL es que me permite de hecho escribir directamente consultas SQL sobre los datos
        // Para ello, primero tengo que registrar el dataframe como una tabla temporal
        datos.createOrReplaceTempView("personas")                   // Registro el dataframe como una tabla temporal llamada "personas"
        // Y A partir de este momento, puedo hacer consultas SQL sobre esa tabla
        val dataframeConLosResultados = conexion.sql("SELECT nombre, edad FROM personas WHERE edad > 30")
        // Ese dataframe sería el mismo que si hubiera escrito:
        val dataframeConLosResultados2 = datos.select("nombre", "edad").filter( datos("edad") > 30 )
        // Ambos son idénticos. Uno es generado con sintaxis de programación Spark/Scala, y el otro con SQL
        // Y claro... estamos mucho más acostumbrados a escribir SQL que a usar la API de Spark
        // Esta es la forma GUAY de poder trabajar con SparkSQL: escribir consultas SQL directamente
        // En esas consultas puedo usar también esas funciones que os he comentado antes
        val dataframeConResultados3 = conexion.sql("""
            SELECT UPPER(nombre) AS NOMBRE_EN_MAYUSCULAS, (edad + 10) AS EDAD_MAS_10
            FROM personas
        """)

        dataframeConResultados3.show()

        // Aqui ya me puedo olvidar de los map-reduce y de los RDDs... y trabajar directamente con SQL
        // Claro.. siempre y cuando, lo que quiera hacer sea representable en SQL
        // Si tengo una lista de tweets y quiero extraer los hashtags... lo que hicimos el otro día... eso lo puedo hacer con SQL????


        // Una gracia que tiene SparkSQL es que me permite usar funciones que tyo tenga en Scala dentro de mis consultas SQL
        // Por ejemplo, nuestra funcion de validar un DNI. Eso se hace con el concepto de User Defined Functions (UDFs)
        // Creo mi funcion de validacion. En nuestro caso es: DNI.esValido(dni: String): Boolean
        // La registramos en SparkSQL
        conexion.udf.register("validarDNI", udf((dni:String) => DNI.esValido(dni)))
        // A partir de este momento, podemos usar esta función en las queries SQL
        conexion.sql("""
            SELECT nombre, edad, provincia, dni, validarDNI(dni) AS dni_valido
            FROM personas
        """).show()

        conexion.udf.register("formatear", udf((dni:String) => {
            val dniComoObjeto = DNI.crearDNI(dni)
            if (dniComoObjeto.esValido()) {
                dniComoObjeto.asInstanceOf[DNIValido].formatear()
            } else if (dniComoObjeto.validez == ValidezDNI.INCORRECTO_FORMATO_NO_RECONOCIDO) {
                "Formato no reconocido del DNI"
            } else{
                "La letra no coincide"
            }
        }))
        val resultado =conexion.sql("""
            SELECT nombre, edad, provincia, dni, formatear(dni) AS dni_formateado
            FROM personas
        """)

        // Guardamos el resultado en un fichero Parquet
        resultado.write.mode("overwrite").parquet("/home/ubuntu/environment/personas_con_dni_formateado.parquet")

        // Cerra la sesión con el cluster
        conexion.stop()

        // Esto está guay, ya que aunque a priori hay muchas cosas que en SQL me sería dificil hacer, con las UDF
        // Puedo meter lógica escrita en SCALA dentro de mis consultas SQL, de forma muy sencilla.

        // Esto no resuelve todos los escenarios. Cuando los datos NO SON FACILMENTE (o es imposible directamente) representarlos en forma tabular
        // El SQL no vale... nos olvidamos.

        // Lo que pasa es que el 90% de las veces, los datos se pueden representar en forma tabular, y las operaciones que queremos hacer sobre ellos
        // también se pueden representar en SQL.
        // Así que SparkSQL es una herramienta muy potente para trabajar con Big Data de forma sencilla

    }

}

case class Persona(nombre:String, edad:Int, provincia:String, dni: String)