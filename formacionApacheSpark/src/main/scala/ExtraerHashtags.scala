import org.apache.spark.{SparkConf, SparkContext}

object ExtraerHashtags {


    def main(args: Array[String]): Unit = {

        val url = "local[2]"
        val propiedadesDeLaConexion = new SparkConf().setAppName("Extraer Hashtags")
                                                     .setMaster(url)
        val conexion = new SparkContext(propiedadesDeLaConexion)


        // Configurar en spark las operaciones que necesitamos

        val tweets = List(
            "En la playa con mis amig@s #veranito#GoodVibes#FriendForever#PedoPis",
            "En casa con mi perrito #DogLover#PetLover#GoodVibes#CuloCaca",
            "Preparando la oposición #MierdaVibes#Estudio#QuieroSerFuncionaria",
            "1, 2,3 nos vamos otra vez! #MasVerano#GoodVibes #PartyAllNight#OtroCaca"
        ) // Partimos de una lista de tweets (String)

        val tweetsEnSpark = conexion.parallelize(tweets)

        // var numeroDeHashtagsEliminados = 0 Esto no vale
        // Esa variable estaría definida en mi máquina.
        // Cada no de trabajo tendrá su propia copia de esa variable
        // Y la función donde incremento esa variable se ejecuta en los nodos trabajadores.. e incrementan su propia copia local
        // Al final, cuando muestro el valor de esa variable, estoy mostrando la copia local de mi máquina, que nunca se ha incrementado
        // Y por eso vemos un 0
        // Para resolver este problema, propio de estar trabajando en un entorno distribuido, Con Spark, Spark nos ofrece los acumuladores (Accumulators)
        // Un acumulador es una variable que defino en mi máquina.. y que los nodos trabajadores pueden acumularle cosas
        // Hay diferentes tipos de acumuladores:
        // Acumuladores contadores (de enteros, de doubles, etc) Sumando cantidades
        // Acumuladores de colección (listas, sets, etc) Añadiendo elementos a una colección (Quiero saber cuales son las palabras prohibidas que filtran hashtags)
        val numeroDeHashtagsEliminados = conexion.longAccumulator("Número de hashtags eliminados")

        val palabrasProhibidas = List("caca", "culo", "pedo", "pis", "mierda")
        // Debemos hacer un broadcast de esta lista
        val palabrasProhibidasBroadcast = conexion.broadcast(palabrasProhibidas) // Con esto, estamos mandando previamente el conjunto de datos a los nodos trabajadores
           // Esta variable es una referencia al conjunto de datos que se ha mandado a los nodos trabajadores
        // podre acceder a la lista de datos con palabrasProhibidasBroadcast.value

        // Trabajo con los datos
        val resultado = tweetsEnSpark.map( tweet => tweet.replace("#"," #")) // Separar los hashtags: "En la playa con mis amig@s #veranito #GoodVibes #FriendForever"
                                                                      // ^ Devuelve una lista de tweets (strings)
                              //.map( tweet => tweet.split("[ ,.;:_(){}\\[\\]<>/\\\\|-]+") ) // Dividir el tweet en palabras
                                                                // "En la playa con mis amig@s #veranito#GoodVibes#FriendForever"
                                                                //                  vvvvvvvv
                                                                // ["En","la","playa","con","mis","amig@s","#veranito","#GoodVibes","#FriendForever"]
                                                                // ^ Devuelve una lista de listas de palabras
                              //.flatten
                              //.flatMap( tweet => tweet.split("[ ,.;:_()¡!¿?{}\\[\\]<>/\\\\|-]+") ) // Dividir el tweet en palabras
                              .flatMap( tweet => tweet.split("[ ,.;:_()¡!¿?{}\\[\\]<>/\\\\|-]+") ) // Dividir el tweet en palabras
                              .filter( palabra => palabra.startsWith("#"))
                              .map( hashtag => hashtag.toLowerCase() ) // Pasar a minúsculas
                              .map( hashtag => hashtag.substring(1) ) // Quitar el símbolo #
                              .filter( hashtag => {
                                  val loMantengo = !palabrasProhibidasBroadcast.value.exists( palabrasProhibida => hashtag.contains(palabrasProhibida) )
                                  if (!loMantengo) {
                                      //numeroDeHashtagsEliminados += 1
                                      numeroDeHashtagsEliminados.add(1) // Esto no edita la variable local del nodo, sino la variable que tengo en mi máquina
                                      println(s"Eliminado hashtag por contener palabra prohibida: ${hashtag}")
                                  }
                                  loMantengo
                              } )
            // Las funciones se envían al cluster y desde ahí a los nodos
            // Pero eso se hace en paquetes atómicos, es decir, con cada paquete de datos se mandan todas las funciones necesarias para procesar ese paquete de datos
            // Eso implica que las palabras prohibidas a priori se mandarían a los nodos trabajadores con cada paquete de datos que deben proicesar..
            // Una y otra y otra vez la misma lista de palabras prohibidas (COLAPSO LA RED innecesariamente)
            // Para eso, spark tiene un mecanismo llamado broadcast, que me permite mandar datos a los nodos trabajadores y que se queden allí, hasta que todo el trabajo
            // haya terminado.
                              //.groupMapReduce(hashtag => hashtag)(_ => 1)(_+_)   Esto funciona directamente con listas de scala... pero no en spark
                              .groupBy( hashtag => hashtag ) // Agrupar por hashtag
                              .map( tupla => (tupla._1, tupla._2.size) ) // Contar ocurrencias
                              .collect()
        // Cerra la sesión con el cluster
        conexion.stop()
        println(s"Número de hashtags eliminados por contener palabras prohibidas: ${numeroDeHashtagsEliminados.value}")

        // Hacer lo que nos interese con el resultado
        resultado.foreach( tupla => println(s"${tupla._1} -> ${tupla._2}") )

    }
    /*
    def suma(numero1: Integer, numero2: Integer): Integer = {
        return numero1 + numero2
    }
    def reemplazo(datoNoMeImporta:String): Integer = {
        return 1
    }
    def identidad(dato:String): String = {
        return dato
    }
    */
/*
    def verificarQueNoContienePalabrasProhibidas(hashtag: String): Boolean = {
                                // GoodVibes
        val palabrasProhibidas = List("caca", "culo", "pedo", "pis", "mierda")
        // Para cada palabra prohibida, mirar si está incluida en el hashtag.
        // Si alguna lo está devolvemos false, si ninguna lo está devolvemos true
        /*for (palabraProhibida <- palabrasProhibidas) {
            if (hashtag.contains(palabraProhibida)) {
                return false
            }
        }
        return true*/
        //return palabrasProhibidas.filter( palabrasProhibida => hashtag.contains(palabrasProhibida) ).length == 0
        !palabrasProhibidas.exists( palabrasProhibida => hashtag.contains(palabrasProhibida) )
    }
*/
}

