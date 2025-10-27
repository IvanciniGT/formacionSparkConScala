object Colecciones {

    def imprimirNumero(numero:Int) : Unit = {
        println(s"El número es: $numero")
    }

    def main(args: Array[String]): Unit = {

        // Arrays
        val numeros = Array(1,3,5,7,9)
        println(s"Tamaño: ${numeros.length}")
        println(s"Primer elemento: ${numeros(0)}")
        println(s"Último elemento: ${numeros(numeros.length - 1)}")
        println(s"Contiene el 3: ${numeros.contains(3)}")
        println(s"Contiene el 2: ${numeros.contains(2)}")
        println(s"Posición que ocupa el dato '3': ${numeros.indexOf(3)}")

        // Forma más tradicional
        for( posicion <- 0 until numeros.length){
            println(s"- El elemento en la posición $posicion es: ${numeros(posicion)}")
        }
        // Bucle: foreach
        for( numero <- numeros){
            println(s"- Elemento $numero")
        }
        // Bucle con programación funcional
        numeros.foreach( /* FUNCIÓN */ println _ )   // Aquí no estamos ejecutando la función println,
                                                    // la pasamos como argumento a la función forall
        imprimirNumero(17)
        numeros.foreach( imprimirNumero _ )         // Aquí no estamos ejecutando la función println,
        // En muchas ocasiones, me es más conveniente definir la propia función dentro de la línea de código
        // Eso lo podemos hacer con una expresión lambda

        val funcionImpresora = (numero:Int)  =>  { // En las lambda no especificamos el tipo de dato que devuelve
                                                    // Qué devuelve la función println? Nada (Unit) ...
                                                    // pues entonces la función que yo estoy definiendo devuelve Unit,
                                                    // que es lo que devuelve la última linea de código que tiene la función.
                                                    // Se infiere el tipo de dato que devuelve la función. No es necesario explicitarlo.
            println(s"El número es: $numero")
        }

        numeros.foreach( funcionImpresora )         // Aquí no estamos ejecutando la función println,


        numeros.foreach( (numero:Int)  =>  {
                                                println(s"El número es: $numero")
                                            }
                       )
        numeros.foreach( (numero:Int)  =>  println(s"El número es: $numero") )
        numeros.foreach( (numero)  =>  println(s"El número es: $numero") ) // El tipo de dato del argumento, también se infiere..
                                                                           // en este caso de la variable que va a apunatr a nuestra función

        numeros.foreach( numero  =>  println(s"El número es: $numero") ) // El tipo de dato del argumento, también se infiere..

        //numeros.foreach( _ => println( _ ) ) // Esto es un truco muy sucio... Que deja un código muy poco intuitivo
                                       // Mi recomendación es que no lo useis.
                                        // En println(_) seguimos definiendo una expresión lambda (una función)
        // Pero como veis, ni hemos puesto la =>. Cuando algo debería ser una función, y yo no pongo ni la flecha
        // El compilador de scala, asume que simplemente estoy llamando a mi variable _
        // Cuidado, ya que la sintaxis _ => println( _ ) no funciona directamente.
        // Si explicitamente pongo "_" como nombre de un argumento/parametro de la función,
        // lo que el compilador de scala interpreta es que ese dato no me hace falta
        // Y que lo estoy tirando a la basura.
        // En cambio, si no pongo el nombre del argumento, ni la flecha,
        // entonces SI Que el compilador me permite usar el argumento, con el nombre "_"
        // En scala, hay más de 25 usos del "_" dependiendo de donde esté
        numeros.foreach( println( _ ) ) // Esto es un truco muy sucio... Que deja un código muy poco intuitivo
        // Y esta es la gran dificulta de scala. Os dije que tiene una curva de aprendizaje muy alta.
        // El problema es que la gente usa mucho esta sintaxis... y cosas similares.
        // Y hace bien. Es la gracia de un lenguaje como scala... que me da formas muy  compactas de escribir mucho código.
        // A costa de que el compilador es muy listo
        // Y esa lógica que porta el compilador, es la necesito aprender... y es mucha! Son muchos casos especiales.

        // Para empezar, es mejor no abusar el operador _
        // Y buscar sintaxis más clásicas... más explicitas, hasta que vayamos cogiendo confianza en el lenguaje.
        //     println( _ )  Sería igual que haber escrito:        (numero:Int) => println(numero)




        // Listas

        val otros_numeros = LazyList(1,2,3,4,5) // Las listas son inmutables, no así los arrays.
        // Puedo cambiar los datos de un array, pero no puedo cambiar los datos de una lista
        // Una lista scala sería el equivalente en python a tupla ()
        // En cambio el array de scala sería equivalente en python a lista []
        // Y Esto es complejo. Porque lista en python y scala son opuestos.
        // Python Lista es mutable
        // Scala Lista es inmutable

        // Sobre la lista, puedo hacer lo mismo que sobre el array:
        println(otros_numeros.length)
        println(otros_numeros(0))
        println(otros_numeros.last)
        println(otros_numeros.head)
        println(otros_numeros(otros_numeros.length -1))


        // Scala lleva el mapreduce en vena... en su genética.

        val resultado = otros_numeros
            // doblarla                 // transformaciones simples que hacemos a cada dato:  map
            .map( (numero:Int) => numero * 2  )
                    // (2,4,6,8,10)
            // Quitarle 5 a cada dato   // transformaciones simples que hacemos a cada dato:  map
            .map( numero => numero - 5  )
                                        //  transformaciones simples que hacemos a cada dato:  map
                    // (-3,-1,1,3,5)
            .map( _ / 2  )
            // (-1.5, -0.5, 0.5, 1.5, 2.5
            // quedarnos con los positivos  // filter (otra funcion de tipo map)
            // .filter( (numero:Int) =>  numero >= 0)
            .filter( _ >= 0)
                    // (0.5, 1.4, 2.5)
        // Aquí, en scala, por defecto, no estamos teniendo map reduce real sobre estas listas.
        // En este caso, no estamos metiendo ninguna función de tipo reduce.. todas esas funciones son de tipo map
        println(resultado.toList) // En este punto es donde estamos aplicando el Reduce... que fuerza el calculo de los maps anteriores.

        // Por qué? qué es lo que realmente está pasando? y que es eso de la función reduce!
    }

}

/*

    17 | 2
   -16 +----
     1  8
     ^
     RESTO.. en scala y python y en java: % ese es el operador %

*/

