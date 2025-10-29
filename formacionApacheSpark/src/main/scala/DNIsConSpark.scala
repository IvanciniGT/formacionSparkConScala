import org.apache.spark.{SparkConf, SparkContext}

object DNIsConSpark {


    def main(args: Array[String]): Unit = {

        // Abrir conexión con un cluster de spark
        //val url = "spark://<IP o NOMBRE del maestro>:7077" // Esto sería si tuvieramos un cluster de spark
        val url = "local[2]" // Esto abre un cluster de spark en local para pruebas. Me lo regala la librería
        val propiedadesDeLaConexion = new SparkConf().setAppName("Prueba") // Nos ayuda a identificar mi app en el cluster
                                                     .setMaster(url)
        // Una vez definidas, creamos la conexión usando esas propiedades
        // A la conexión en Spark se le llama Contexto
        val conexion = new SparkContext(propiedadesDeLaConexion)

        val opciones= OpcionesDeFormateoDNI( puntos = true , separador = "-", cerosDelante = false)

        // Configurar en spark las operaciones que necesitamos
        val listaDNIsScala= List("12345678A", "87.654321B", "11223344%C", "Menchu", "55667788E", "230000T", "340000R", "2300023T")
        val listaDNIs = conexion.parallelize(listaDNIsScala)
        listaDNIs.filter(DNI.esValido).foreach(println)
        listaDNIs.filter(DNI.esInvalido).map( dni => (dni, DNI.crearDNI(dni).validez)).foreach(println)
        listaDNIs.filter(DNI.esValido).map(dni => DNI.crearDNI(dni).asInstanceOf[DNIValido].formatear(opciones)).foreach(println)
        listaDNIs.filter(DNI.esValido).map(dni => DNI.crearDNI(dni).asInstanceOf[DNIValido].formatear()).foreach(println)

        // Cerra la sesión con el cluster
        conexion.stop()


    }

}

