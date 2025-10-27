import org.apache.spark.{SparkConf, SparkContext}

object MapReduceConSpark {

    def mitad(numero:Int) : Int = {
        return numero /2;
    }

    def main(args: Array[String]): Unit = {

        // Abrir conexión con un cluster de spark
        //val url = "spark://<IP o NOMBRE del maestro>:7077" // Esto sería si tuvieramos un cluster de spark
        val url = "local[2]" // Esto abre un cluster de spark en local para pruebas. Me lo regala la librería
        val propiedadesDeLaConexion = new SparkConf().setAppName("Prueba") // Nos ayuda a identificar mi app en el cluster
                                                     .setMaster(url)
        // Una vez definidas, creamos la conexión usando esas propiedades
        // A la conexión en Spark se le llama Contexto
        val conexion = new SparkContext(propiedadesDeLaConexion)

        // Configurar en spark las operaciones que necesitamos
        val otros_numeros = conexion.parallelize(List(1,2,3,4,5),       5)
        val resultado = otros_numeros
            .map( (numero:Int) => numero * 2  )
            .map( numero => numero - 5  )
            //.map( mitad  )
            .map( _ / 2 )
            .filter( _ >= 0 )
            .collect()

        // Cerra la sesión con el cluster
        conexion.stop()

        // Hacer lo que nos interese con el resultado
        println(resultado.toList)

    }

}

