import org.apache.spark.sql.{Row, SparkSession}

object ProcesarPersonasSparkSQL {

    def main(args: Array[String]): Unit = {

        val conexion = SparkSession.builder()
                                    .appName("IntroSparkSQL")
                                    //.master("local[2]")
                                    .getOrCreate()

        val personas = conexion.read.json("src/main/resources/personas.json") // Crea un dataframe a partir de un fichero JSON
        // Dependendiendo del tipo de archivo o del origen de los datos, puedo necesitar librerías adicionales:
        // - Para leer archivos JSON, necesito de un programa que entienda JSON
        //         jackson-core
        // - Para conectar con un Oracle, necesito la librería con el driver de Oracle

        // En este caso, que es un triste ejemplo, estamos usando una ruta local... un fichero dentro de mi máquina
        // En un entorno de producción ésto no tiene ningún sentido!
        // Siempre vamos a trabajar con rutas EN RED:     nfs://servidor/carpeta/fichero.json
        // O con sistemas de ficheros distribuidos:       hdfs://servidor/carpeta/fichero.json / Parquet / Avro
        val cps = conexion.read.option("header","true").csv("src/main/resources/cps.csv") // Crea un dataframe a partir de un fichero JSON
        // Esas opciones ya dependen del origen de datos

        personas.show()
        cps.show()

        // Podríamos hacer la join a nivel programático usando las funciones de los dataframes
        val resultado = personas.join(cps, personas("cp") === cps("cp"), "inner")
                 .select( personas("nombre"), personas("apellidos"), personas("edad"), personas("dni"), cps("cp"), personas("email"), cps("municipio"), cps("provincia"))
                 .filter( personas("edad") > 30 )
        resultado.show()

        // Para trabajar cómodo usando sintaxis SQL tradicional, me basta con registrar los dataframes como tablas temporales
        personas.createOrReplaceTempView("personas")
        cps.createOrReplaceTempView("cps")

        val datosEnriquecidos = conexion.sql(
            """
                SELECT p.nombre, p.apellidos, p.edad, p.dni, c.cp, p.email, c.municipio, c.provincia
                FROM personas p FULL OUTER JOIN cps c ON p.cp = c.cp
            """
        ) // {"nombre":"Juan","apellidos":"García","edad":25, "dni": "23000000T", "cp": "28001", "email": "juan@garcia.es"}

        // INNER, LEFT, RIGHT, FULL JOIN
        // Nos toma datos que existan en ambas tablas (INNER)
        // Nos toma todos los datos de la tabla izquierda (LEFT), aunque no haya relación en la tabla derecha ... dejando datos nulos
        // Nos toma todos los datos de la tabla derecha (RIGHT), aunque no haya relación en la tabla izquierda ... dejado datos nulos
        // Nos toma todos los datos de ambas tablas (FULL), aunque no haya relación en ninguna de las dos tablas... dejando datos nulos
        datosEnriquecidos.show()
        // COSA IMPORTANTE!

        // Podemos pasar de DataFRames a RDDs y viceversa
        val rddDePersonas = personas.rdd // Paso de DataFrame a RDD[Row]
        // Row. Es una clase que nos ofrece Spark para representar una fila de datos genérica
        // A una fila podemos acceder a sus columnas por índice o por nombre
        /*
        val primerFila = rddDePersonas.first()
        val nombreDeLaPrimeraFila = primerFila.getAs[String]("nombre") // Acceder a la columna "nombre" como String
        val edadDeLaPrimeraFila = primerFila.getAs[Long]("edad")       // Acceder a la columna "edad"
        println(s"Nombre de la primera fila: ${nombreDeLaPrimeraFila}, Edad: ${edadDeLaPrimeraFila}")
        */

        // Validemos los DNIS... sin SQL (Ya vimos como hacerlo con un UDF)
        // Sobre el RDD ya aplico map-reduce
        val rddDePersonasConDNIValido = rddDePersonas.filter( fila => {
            val dni = fila.getAs[String]("dni")
            DNI.esValido(dni)
        })
        // Ese RDD lo puedo pasar de nuevo a DataFrame
        val dataframePersonasConDNIValido = conexion.createDataFrame(rddDePersonasConDNIValido, personas.schema)
        dataframePersonasConDNIValido.show


        // Cerra la sesión con el cluster
        conexion.stop()


    }

}
