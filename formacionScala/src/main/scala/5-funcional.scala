/**
 * 5-funcional.scala
 * 
 * Programación funcional en Scala
 * 
 * Este archivo demuestra:
 * - Funciones como valores de primera clase
 * - Funciones de orden superior (higher-order functions)
 * - Expresiones lambda (funciones anónimas)
 * - Función map y conceptos MapReduce
 * - Closures y currying
 * - Similitudes con el ejemplo de Python
 */

object ProgramacionFuncional {
  
  def main(args: Array[String]): Unit = {
    
    println("PROGRAMACIÓN FUNCIONAL EN SCALA")
    println("=" * 35)
    
    // Funciones como valores
    funcionesComoValores()
    
    // Funciones de orden superior
    funcionesOrdenSuperior()
    
    // El ejemplo clásico: doblar y triplicar
    ejemploDoblarTriplicar()
    
    // Función map personalizada
    funcionMapPersonalizada()
    
    // Expresiones lambda
    expresionesLambda()
    
    // Funciones map nativas de Scala
    funcionesMapNativas()
    
    // Conceptos avanzados
    conceptosAvanzados()
  }
  
  /**
   * Demostrar que las funciones son valores de primera clase
   */
  def funcionesComoValores(): Unit = {
    println("FUNCIONES COMO VALORES:")
    println("-" * 23)
    
    // Definir una función normal
    def saluda(): Unit = {
      println("Hola")
    }
    
    // Llamar a la función normalmente
    saluda()
    saluda()
    
    // Asignar la función a una variable (función como valor)
    val miVariable: () => Unit = saluda  // Nota: () => Unit es el tipo de función
    println(s"Referencia a función: $miVariable")
    
    // Llamar a la función a través de la variable
    miVariable()
    
    // Otro ejemplo con función que devuelve valor
    def cuadrado(x: Int): Int = x * x
    
    // Asignar función a variable
    val funcionCuadrado: Int => Int = cuadrado
    println(s"Cuadrado de 5: ${funcionCuadrado(5)}")
    
    // Funciones que reciben funciones como parámetros
    def aplicarFuncion(func: Int => Int, numero: Int): Int = {
      func(numero)
    }
    
    println(s"Aplicar cuadrado a 7: ${aplicarFuncion(cuadrado, 7)}")
    
    println()
  }
  
  /**
   * Funciones de orden superior - Ejemplo de saludos (como en Python)
   */
  def funcionesOrdenSuperior(): Unit = {
    println("FUNCIONES DE ORDEN SUPERIOR:")
    println("-" * 28)
    
    // Funciones generadoras de saludos (equivalente al ejemplo Python)
    def saludoFormal(nombre: String): String = {
      s"Buenos días, $nombre. Es un placer saludarle."
    }
    
    def saludoInformal(nombre: String): String = {
      s"¡Hola, $nombre! ¿Qué tal?"
    }
    
    // Función que recibe otra función como parámetro
    def imprimirSaludo(generadorSaludo: String => String, nombre: String): Unit = {
      val saludo = generadorSaludo(nombre)
      println(saludo)
    }
    
    // Función que aplica saludo a múltiples nombres
    def imprimirSaludos(generadorSaludo: String => String, nombres: List[String]): Unit = {
      for (nombre <- nombres) {
        val saludo = generadorSaludo(nombre)
        println(saludo)
      }
    }
    
    // Uso de las funciones
    imprimirSaludo(saludoFormal, "Señor Pérez")
    imprimirSaludo(saludoInformal, "Ana")
    
    println("\nSaludos formales para grupo:")
    val nombres = List("Ana", "Luis", "María", "Pedro")
    imprimirSaludos(saludoFormal, nombres)
    
    println("\nSaludos informales para grupo:")
    imprimirSaludos(saludoInformal, nombres)
    
    println("\n💡 Esto permite inyectar lógica a una función desde fuera!")
    println()
  }
  
  /**
   * El ejemplo clásico: doblar y triplicar números (como en Python)
   */
  def ejemploDoblarTriplicar(): Unit = {
    println("EJEMPLO DOBLAR Y TRIPLICAR:")
    println("-" * 27)
    
    // Definir funciones de operación
    def doblar(numero: Int): Int = numero * 2
    def triplicar(numero: Int): Int = numero * 3
    def cuadriplicar(numero: Int): Int = numero * 4
    
    // Funciones específicas (código duplicado - MAL)
    def imprimirResultadoDoblar(listaNumeros: List[Int]): Unit = {
      for (numero <- listaNumeros) {
        val resultado = doblar(numero)
        println(s"El doble de $numero es $resultado")
      }
    }
    
    def imprimirResultadoTriplicar(listaNumeros: List[Int]): Unit = {
      for (numero <- listaNumeros) {
        val resultado = triplicar(numero)
        println(s"El triple de $numero es $resultado")
      }
    }
    
    // Función genérica (código reutilizable - BIEN)
    def imprimirResultadoOperacion(operacion: Int => Int, listaNumeros: List[Int]): Unit = {
      for (numero <- listaNumeros) {
        val resultado = operacion(numero)
        println(s"El resultado de aplicar la operación sobre $numero es $resultado")
      }
    }
    
    val numeros = List(1, 2, 3, 4, 5)
    
    println("Usando funciones específicas (duplicación de código):")
    imprimirResultadoDoblar(numeros)
    imprimirResultadoTriplicar(numeros)
    
    println("\nUsando función genérica (código reutilizable):")
    imprimirResultadoOperacion(doblar, numeros)
    imprimirResultadoOperacion(triplicar, numeros)
    imprimirResultadoOperacion(cuadriplicar, numeros)
    
    println()
  }
  
  /**
   * Función map personalizada (equivalente al ejemplo Python)
   */
  def funcionMapPersonalizada(): Unit = {
    println("FUNCIÓN MAP PERSONALIZADA:")
    println("-" * 25)
    
    // Implementar nuestra propia función map (como en el ejemplo Python)
    def aplicarOperacionALista[T, R](operacion: T => R, lista: List[T]): List[R] = {
      var resultados = List[R]()
      for (dato <- lista) {
        val resultado = operacion(dato)
        resultados = resultados :+ resultado  // Agregar al final
      }
      resultados
    }
    
    // Versión más funcional usando recursión
    def aplicarOperacionAListaRecursiva[T, R](operacion: T => R, lista: List[T]): List[R] = {
      lista match {
        case Nil => Nil  // Lista vacía
        case head :: tail => operacion(head) :: aplicarOperacionAListaRecursiva(operacion, tail)
      }
    }
    
    // Funciones para probar
    def doblar(numero: Int): Int = numero * 2
    def triplicar(numero: Int): Int = numero * 3
    def ponerEnMayusculas(texto: String): String = texto.toUpperCase
    
    val numeros = List(1, 2, 3, 4, 5)
    val textos = List("hola", "adiós", "hasta luego")
    
    println("Con nuestra función map personalizada:")
    println(s"Doblar: ${aplicarOperacionALista(doblar, numeros)}")
    println(s"Triplicar: ${aplicarOperacionAListaRecursiva(triplicar, numeros)}")
    println(s"Mayúsculas: ${aplicarOperacionALista(ponerEnMayusculas, textos)}")
    
    println("\n🎯 ¡Acabamos de implementar la función MAP de MapReduce!")
    println()
  }
  
  /**
   * Expresiones lambda (funciones anónimas)
   */
  def expresionesLambda(): Unit = {
    println("EXPRESIONES LAMBDA:")
    println("-" * 18)
    
    println("Comparación Python vs Scala:")
    println("Python: lambda texto: texto.lower()")
    println("Scala:  texto => texto.toLowerCase")
    println()
    
    // Usar nuestra función map con lambdas
    val numeros = List(1, 2, 3, 4, 5)
    val textos = List("HOLA", "ADIÓS", "HASTA LUEGO")
    
    // Función lambda simple
    val doblar = (x: Int) => x * 2
    println(s"Lambda doblar: ${aplicarOperacionALista(doblar, numeros)}")
    
    // Lambda inline (sin asignar a variable)
    println(s"Lambda inline triplicar: ${aplicarOperacionALista((x: Int) => x * 3, numeros)}")
    
    // Lambda con inferencia de tipos
    println(s"Lambda minúsculas: ${aplicarOperacionALista((texto: String) => texto.toLowerCase, textos)}")
    
    // Syntax sugar - cuando el parámetro se usa una sola vez
    println(s"Syntax sugar (_): ${aplicarOperacionALista((x: Int) => x * 4, numeros)}")
    println(s"Syntax sugar (_.toLowerCase): ${aplicarOperacionALista((x: String) => x.toLowerCase, textos)}")
    
    // Lambdas más complejas
    val formatear = (numero: Int) => s"Número: $numero"
    println(s"Lambda compleja: ${aplicarOperacionALista(formatear, numeros)}")
    
    println()
  }
  
  /**
   * Usar las funciones map nativas de Scala
   */
  def funcionesMapNativas(): Unit = {
    println("FUNCIONES MAP NATIVAS DE SCALA:")
    println("-" * 31)
    
    val numeros = List(1, 2, 3, 4, 5)
    val textos = List("hola", "adiós", "hasta luego")
    
    // map nativo de Scala (equivalente a map() de Python)
    println("Con map nativo de Scala:")
    println(s"Doblar: ${numeros.map(_ * 2)}")
    println(s"Triplicar: ${numeros.map(_ * 3)}")
    println(s"Mayúsculas: ${textos.map(_.toUpperCase)}")
    
    // Encadenar operaciones map
    println("\nEncadenar operaciones:")
    val resultado = numeros
      .map(_ * 2)        // Doblar
      .map(_ + 1)        // Sumar 1
      .map(x => s"[$x]") // Formatear
    
    println(s"Numeros -> doblar -> +1 -> formatear: $resultado")
    
    // Otras funciones funcionales útiles
    println("\nOtras funciones funcionales:")
    println(s"Filter (pares): ${numeros.filter(_ % 2 == 0)}")
    println(s"Reduce (suma): ${numeros.reduce(_ + _)}")
    println(s"FlatMap: ${List(1, 2, 3).flatMap(x => List(x, x * 2))}")
    
    println("\n🎯 En Spark vamos a encadenar 10-30 operaciones como estas en UNA LÍNEA!")
    println()
  }
  
  /**
   * Función map personalizada reutilizable (como en Python)
   */
  def aplicarOperacionALista[T, R](operacion: T => R, lista: List[T]): List[R] = {
    var resultados = List[R]()
    for (dato <- lista) {
      val resultado = operacion(dato)
      resultados = resultados :+ resultado
    }
    resultados
  }
  
  /**
   * Conceptos avanzados: closures, currying, etc.
   */
  def conceptosAvanzados(): Unit = {
    println("CONCEPTOS AVANZADOS:")
    println("-" * 19)
    
    // CLOSURES - funciones que "capturan" variables del entorno
    val factor = 10
    val multiplicarPorFactor = (x: Int) => x * factor  // "Captura" la variable factor
    
    println(s"Closure: ${List(1, 2, 3).map(multiplicarPorFactor)}")
    
    // CURRYING - funciones que devuelven otras funciones
    def multiplicador(factor: Int): Int => Int = {
      (x: Int) => x * factor
    }
    
    val multiplicarPor3 = multiplicador(3)
    val multiplicarPor5 = multiplicador(5)
    
    println(s"Currying x3: ${List(1, 2, 3, 4).map(multiplicarPor3)}")
    println(s"Currying x5: ${List(1, 2, 3, 4).map(multiplicarPor5)}")
    
    // PARTIAL APPLICATION
    def sumar(a: Int, b: Int, c: Int): Int = a + b + c
    val sumarCon10 = sumar(10, _: Int, _: Int)  // Fijamos el primer parámetro
    
    println(s"Partial application: ${sumarCon10(5, 3)}")  // 10 + 5 + 3 = 18
    
    // FUNCIÓN COMPOSE
    val doblar = (x: Int) => x * 2
    val sumar5 = (x: Int) => x + 5
    val doblarYSumar5 = doblar andThen sumar5  // Primero doblar, luego sumar 5
    
    println(s"Compose: ${List(1, 2, 3).map(doblarYSumar5)}")
    
    println("\n🎯 RESUMEN: Scala es ideal para programación funcional")
    println("🎯 RESUMEN: Las funciones son valores de primera clase")
    println("🎯 RESUMEN: map/filter/reduce son la base de MapReduce")
    println("🎯 RESUMEN: Spark usa estos conceptos intensivamente")
  }
}

/*
 * NOTAS IMPORTANTES :
 * 
 * 1. EJECUTAR ESTE ARCHIVO:
 *    mvn exec:java -Dexec.mainClass="ProgramacionFuncional"
 * 
 * 2. COMPARACIÓN PYTHON vs SCALA:
 *    Python: lambda x: x * 2
 *    Scala:   x => x * 2
 *    Scala:   _ * 2  (syntax sugar)
 * 
 * 3. CONCEPTOS CLAVE:
 *    - Función de orden superior: función que recibe/devuelve otras funciones
 *    - Lambda: función anónima definida inline
 *    - Map: transformar cada elemento de una colección
 *    - Closure: función que captura variables del entorno
 *    - Currying: función que devuelve otra función
 * 
 * 4. TIPOS DE FUNCIÓN EN SCALA:
 *    - () => Unit          // Sin parámetros, sin retorno
 *    - Int => String       // Un parámetro Int, retorna String
 *    - (Int, String) => Boolean  // Dos parámetros, retorna Boolean
 * 
 * 5. PATRONES IMPORTANTES PARA SPARK:
 *    - data.map(transformacion)
 *    - data.filter(condicion)
 *    - data.reduce(operacion)
 *    - Encadenar operaciones: data.map(...).filter(...).reduce(...)
 * 
 * 6. PRÓXIMO PASO:
 *    - Ver 6-textos.scala para manipulación de strings
 * 
 * RELACIÓN CON MAPREDUCE:
 * - MAP: transformar cada elemento (función map)
 * - REDUCE: combinar elementos (función reduce)
 * - Spark implementa MapReduce de forma distribuida y en memoria
 * - Todo lo que aprenden aquí se aplica directamente en Spark
 */