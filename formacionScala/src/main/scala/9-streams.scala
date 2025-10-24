/**
 * 9-streams.scala
 * 
 * Streams en Scala - Evaluación perezosa (Lazy Evaluation)
 * 
 * Este archivo demuestra:
 * - Qué son los Streams y LazyList
 * - Evaluación perezosa vs eagrr
 * - Streams infinitos
 * - Ventajas de la evaluación perezosa
 * - Casos de uso prácticos
 * - Comparación con colecciones normales
 */

object StreamsYEvaluacionPerezosa {
  
  def main(args: Array[String]): Unit = {
    
    println("STREAMS Y EVALUACIÓN PEREZOSA EN SCALA")
    println("=" * 39)
    
    // Introducción a streams
    introduccionStreams()
    
    // Comparación lazy vs eager
    comparacionLazyEager()
    
    // Streams infinitos
    streamsInfinitos()
    
    // Operaciones con streams
    operacionesStreams()
    
    // Casos de uso prácticos
    casosDeUsoPracticos()
    
    // Performance y memoria
    performanceYMemoria()
  }
  
  /**
   * Introducción a los streams
   */
  def introduccionStreams(): Unit = {
    println("INTRODUCCIÓN A STREAMS:")
    println("-" * 23)
    
    // LazyList (antes Stream en versiones anteriores)
    val stream1 = LazyList(1, 2, 3, 4, 5)
    println(s"Stream simple: $stream1")
    
    // Stream desde range
    val streamRange = LazyList.range(1, 10)
    println(s"Stream desde range: $streamRange")
    
    // Stream usando #:: (cons perezoso)
    val stream2 = 1 #:: 2 #:: 3 #:: LazyList.empty
    println(s"Con #:: $stream2")
    
    // ¿Qué hace especial a un stream?
    println("\n¿Qué hace especial a un stream?")
    
    // Lista normal (evaluación eagrr)
    val listaNormal = List(1, 2, 3, 4, 5).map { x =>
      println(s"  Procesando $x en lista normal")
      x * 2
    }
    println(s"Lista normal creada: ${listaNormal.take(2)}")
    
    println()
    
    // Stream (evaluación perezosa)
    val streamPerezoso = LazyList(1, 2, 3, 4, 5).map { x =>
      println(s"  Procesando $x en stream")
      x * 2
    }
    println(s"Stream perezoso creado: $streamPerezoso")
    println("Notaste que no se procesó nada aún?")
    
    println("\nAhora tomemos 2 elementos del stream:")
    val primerosDos = streamPerezoso.take(2).toList
    println(s"Primeros dos: $primerosDos")
    
    println()
  }
  
  /**
   * Comparación entre evaluación perezosa y eager
   */
  def comparacionLazyEager(): Unit = {
    println("COMPARACIÓN LAZY vs EAGER:")
    println("-" * 26)
    
    // Función costosa para demostrar la diferencia
    def operacionCostosa(x: Int): Int = {
      println(s"    Calculando operación costosa para $x")
      Thread.sleep(100)  // Simular operación lenta
      x * x
    }
    
    println("=== EVALUACIÓN EAGER (Lista normal) ===")
    val inicioEager = System.currentTimeMillis()
    
    val listaEager = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
      .map(operacionCostosa)
      .filter(_ > 10)
    
    val tiempoCreacionEager = System.currentTimeMillis() - inicioEager
    println(s"Tiempo de creación lista eager: ${tiempoCreacionEager}ms")
    
    val inicioUsoEager = System.currentTimeMillis()
    val resultadoEager = listaEager.take(2)
    val tiempoUsoEager = System.currentTimeMillis() - inicioUsoEager
    
    println(s"Resultado eager: $resultadoEager")
    println(s"Tiempo de uso: ${tiempoUsoEager}ms")
    println(s"Tiempo total eager: ${tiempoCreacionEager + tiempoUsoEager}ms\n")
    
    println("=== EVALUACIÓN PEREZOSA (LazyList) ===")
    val inicioLazy = System.currentTimeMillis()
    
    val streamLazy = LazyList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
      .map(operacionCostosa)
      .filter(_ > 10)
    
    val tiempoCreacionLazy = System.currentTimeMillis() - inicioLazy
    println(s"Tiempo de creación stream lazy: ${tiempoCreacionLazy}ms")
    
    val inicioUsoLazy = System.currentTimeMillis()
    val resultadoLazy = streamLazy.take(2).toList
    val tiempoUsoLazy = System.currentTimeMillis() - inicioUsoLazy
    
    println(s"Resultado lazy: $resultadoLazy")
    println(s"Tiempo de uso: ${tiempoUsoLazy}ms")
    println(s"Tiempo total lazy: ${tiempoCreacionLazy + tiempoUsoLazy}ms")
    
    println("\n💡 OBSERVACIÓN: El stream solo calculó lo que necesitaba!")
    println()
  }
  
  /**
   * Streams infinitos
   */
  def streamsInfinitos(): Unit = {
    println("STREAMS INFINITOS:")
    println("-" * 18)
    
    // Stream de números naturales
    def numerosNaturales(n: Int = 1): LazyList[Int] = n #:: numerosNaturales(n + 1)
    
    val naturales = numerosNaturales()
    println("Stream de números naturales (infinito):")
    println(s"Primeros 10: ${naturales.take(10).toList}")
    println(s"Del 100 al 105: ${naturales.drop(99).take(5).toList}")
    
    // Secuencia de Fibonacci
    def fibonacci: LazyList[BigInt] = {
      def fibonacciHelper(a: BigInt, b: BigInt): LazyList[BigInt] = 
        a #:: fibonacciHelper(b, a + b)
      fibonacciHelper(0, 1)
    }
    
    val fib = fibonacci
    println(s"Primeros 15 Fibonacci: ${fib.take(15).toList}")
    println(s"Fibonacci 50: ${fib(50)}")  // ¡Acceso directo!
    
    // Stream de números primos
    def esPrimo(n: Int): Boolean = {
      if (n < 2) false
      else !(2 until math.sqrt(n).toInt + 1).exists(n % _ == 0)
    }
    
    val primos = naturales.filter(esPrimo)
    println(s"Primeros 10 primos: ${primos.take(10).toList}")
    
    // Stream de potencias de 2
    def potenciasDe2: LazyList[Long] = LazyList.iterate(1L)(_ * 2)
    
    val potencias = potenciasDe2
    println(s"Primeras 10 potencias de 2: ${potencias.take(10).toList}")
    println(s"2^20 = ${potencias(20)}")
    
    // Stream cíclico
    val coloresCiclicos = LazyList.continually(List("rojo", "verde", "azul")).flatten
    println(s"Colores cíclicos (15): ${coloresCiclicos.take(15).toList}")
    
    // Stream aleatorio
    val random = LazyList.continually(scala.util.Random.nextInt(100))
    println(s"10 números aleatorios: ${random.take(10).toList}")
    
    println()
  }
  
  /**
   * Operaciones con streams
   */
  def operacionesStreams(): Unit = {
    println("OPERACIONES CON STREAMS:")
    println("-" * 24)
    
    val numeros = LazyList.range(1, 1000000)  // Un millón de números
    
    println("Stream de 1 millón de números creado instantáneamente!")
    
    // Map perezoso
    val cuadrados = numeros.map { x =>
      // Esta función se ejecutará solo cuando se necesite
      x * x
    }
    
    println(s"Primeros 5 cuadrados: ${cuadrados.take(5).toList}")
    
    // Filter perezoso
    val pares = numeros.filter(_ % 2 == 0)
    println(s"Primeros 5 pares: ${pares.take(5).toList}")
    
    // Operaciones combinadas
    val resultado = numeros
      .filter(_ % 2 == 0)           // Solo pares
      .map(_ * 3)                   // Multiplicar por 3
      .filter(_ > 100)              // Solo mayores que 100
      .take(5)                      // Solo 5 elementos
      .toList
    
    println(s"Operación combinada: $resultado")
    
    // TakeWhile y dropWhile
    val numerosHasta50 = numeros.takeWhile(_ <= 50)
    val numerosDesde50 = numeros.dropWhile(_ < 50).take(5)
    
    println(s"Números hasta 50: ${numerosHasta50.toList}")
    println(s"Primeros 5 desde 50: ${numerosDesde50.toList}")
    
    // Scan - acumulación perezosa
    val sumas = LazyList.range(1, 11).scan(0)(_ + _)
    println(s"Sumas acumuladas: ${sumas.toList}")
    
    // Zip con streams infinitos
    val letras = LazyList.continually(List("a", "b", "c")).flatten
    val numerosConLetras = numeros.zip(letras).take(8)
    println(s"Números con letras: ${numerosConLetras.toList}")
    
    println()
  }
  
  /**
   * Casos de uso prácticos
   */
  def casosDeUsoPracticos(): Unit = {
    println("CASOS DE USO PRÁCTICOS:")
    println("-" * 24)
    
    // 1. Procesamiento de archivo grande (simulado)
    def procesarLineaArchivo(numeroLinea: Int): String = {
      // Simular lectura de línea costosa
      s"Línea $numeroLinea procesada"
    }
    
    val lineasArchivo = LazyList.from(1).map(procesarLineaArchivo)
    
    println("=== Procesamiento de archivo grande ===")
    println("Solo procesamos las líneas que necesitamos:")
    val primeras3Lineas = lineasArchivo.take(3).toList
    println(primeras3Lineas.mkString("\n"))
    
    // 2. Generador de IDs únicos
    def generadorIds(prefijo: String = "ID"): LazyList[String] = {
      LazyList.from(1).map(i => s"$prefijo-${"%06d".format(i)}")
    }
    
    val idsUsuarios = generadorIds("USER")
    val idsProductos = generadorIds("PROD")
    
    println("\n=== Generador de IDs ===")
    println(s"IDs usuarios: ${idsUsuarios.take(5).toList}")
    println(s"IDs productos: ${idsProductos.take(3).toList}")
    
    // 3. Pipeline de transformaciones de datos
    case class Persona(nombre: String, edad: Int, salario: Double)
    
    val personas = LazyList(
      Persona("Ana", 25, 30000),
      Persona("Juan", 30, 45000),
      Persona("María", 28, 35000),
      Persona("Pedro", 35, 50000),
      Persona("Carmen", 27, 32000)
    )
    
    val pipelinePersonas = personas
      .filter(_.edad >= 28)                    // Solo >= 28 años
      .map(p => p.copy(salario = p.salario * 1.1))  // Aumento 10%
      .filter(_.salario > 35000)               // Solo salarios > 35K
      .map(p => s"${p.nombre}: €${p.salario.toInt}") // Formatear
    
    println("\n=== Pipeline de transformaciones ===")
    println("Personas con aumento que cumplen criterios:")
    pipelinePersonas.foreach(println)
    
    // 4. Generador de datos de prueba
    def generarDatosPrueba: LazyList[Map[String, Any]] = {
      val nombres = List("Ana", "Juan", "María", "Pedro", "Carmen")
      val departamentos = List("IT", "RRHH", "Ventas", "Marketing")
      
      LazyList.from(1).map { i =>
        Map(
          "id" -> i,
          "nombre" -> nombres(scala.util.Random.nextInt(nombres.length)),
          "departamento" -> departamentos(scala.util.Random.nextInt(departamentos.length)),
          "salario" -> (25000 + scala.util.Random.nextInt(25000))
        )
      }
    }
    
    val datosPrueba = generarDatosPrueba
    
    println("\n=== Generador de datos de prueba ===")
    println("Primeros 3 registros:")
    datosPrueba.take(3).foreach(println)
    
    // 5. Cache con expiración simulada
    case class CacheEntry[T](valor: T, timestamp: Long)
    
    def crearCacheStream[T](generador: () => T, ttlMs: Long = 5000): LazyList[T] = {
      LazyList.continually {
        val entry = CacheEntry(generador(), System.currentTimeMillis())
        entry.valor
      }
    }
    
    val timestampCache = crearCacheStream(() => System.currentTimeMillis())
    
    println("\n=== Cache simulado ===")
    println(s"Timestamp 1: ${timestampCache.head}")
    Thread.sleep(100)
    println(s"Timestamp 2: ${timestampCache.head}")
    
    println()
  }
  
  /**
   * Performance y uso de memoria
   */
  def performanceYMemoria(): Unit = {
    println("PERFORMANCE Y MEMORIA:")
    println("-" * 22)
    
    // Demostración de uso eficiente de memoria
    println("=== Uso de memoria ===")
    
    // Lista normal - todos los elementos en memoria
    val runtime = Runtime.getRuntime
    val memoriaInicial = runtime.totalMemory() - runtime.freeMemory()
    
    println(s"Memoria inicial: ${memoriaInicial / 1024}KB")
    
    // Crear lista grande
    val listaGrande = (1 to 1000000).toList
    val memoriaConLista = runtime.totalMemory() - runtime.freeMemory()
    
    println(s"Memoria con lista de 1M elementos: ${memoriaConLista / 1024}KB")
    println(s"Diferencia: ${(memoriaConLista - memoriaInicial) / 1024}KB")
    
    // Stream equivalente
    val streamGrande = LazyList.range(1, 1000001)
    val memoriaConStream = runtime.totalMemory() - runtime.freeMemory()
    
    println(s"Memoria con stream de 1M elementos: ${memoriaConStream / 1024}KB")
    println(s"Diferencia para stream: ${(memoriaConStream - memoriaInicial) / 1024}KB")
    
    // Procesamiento diferido
    println("\n=== Procesamiento diferido ===")
    
    def funcionLenta(x: Int): Int = {
      Thread.sleep(1)  // Simular operación lenta
      x * x
    }
    
    val inicioStream = System.currentTimeMillis()
    val streamProcesado = LazyList.range(1, 1001).map(funcionLenta)
    val tiempoCreacionStream = System.currentTimeMillis() - inicioStream
    
    println(s"Tiempo crear stream con operación lenta: ${tiempoCreacionStream}ms")
    
    val inicioUso = System.currentTimeMillis()
    val resultado = streamProcesado.take(5).sum
    val tiempoUso = System.currentTimeMillis() - inicioUso
    
    println(s"Tiempo usar solo 5 elementos: ${tiempoUso}ms")
    println(s"Resultado: $resultado")
    
    // Memoización automática
    println("\n=== Memoización ===")
    
    var contadorEvaluaciones = 0
    val streamConContador = LazyList.range(1, 6).map { x =>
      contadorEvaluaciones += 1
      println(s"  Evaluando $x (evaluación #$contadorEvaluaciones)")
      x * 2
    }
    
    println("Primera vez - tomamos 3 elementos:")
    val primeros3 = streamConContador.take(3).toList
    println(s"Resultado: $primeros3")
    
    println("\nSegunda vez - tomamos los mismos 3 elementos:")
    val nuevamentePrimeros3 = streamConContador.take(3).toList
    println(s"Resultado: $nuevamentePrimeros3")
    println("¡No se volvieron a evaluar porque están memoizados!")
    
    // Recomendaciones
    println("\n🎯 CUÁNDO USAR STREAMS:")
    println("  ✅ Datos grandes que no caben en memoria")
    println("  ✅ Solo necesitas parte de los datos")
    println("  ✅ Operaciones costosas que quieres diferir")
    println("  ✅ Datos infinitos o muy grandes")
    println("  ✅ Pipelines de transformación complejos")
    
    println("\n🎯 CUÁNDO NO USAR STREAMS:")
    println("  ❌ Datos pequeños (overhead innecesario)")
    println("  ❌ Necesitas todos los elementos inmediatamente")
    println("  ❌ Acceso aleatorio frecuente")
    println("  ❌ Operaciones que requieren todo el dataset")
    
    println("\n🎯 CONEXIÓN CON SPARK:")
    println("  🔗 Spark RDD usa evaluación perezosa similar")
    println("  🔗 Las transformaciones se definen pero no se ejecutan")
    println("  🔗 Solo se ejecutan cuando hay una acción (collect, save, etc.)")
    println("  🔗 Permite optimización automática del plan de ejecución")
  }
}

/*
 * NOTAS IMPORTANTES:
 * 
 * 1. EJECUTAR ESTE ARCHIVO:
 *    mvn exec:java -Dexec.mainClass="StreamsYEvaluacionPerezosa"
 * 
 * 2. CONCEPTOS CLAVE:
 *    - Evaluación perezosa: solo se calculan los valores cuando se necesitan
 *    - LazyList: estructura de datos con evaluación perezosa
 *    - Memoización: una vez calculado, el valor se guarda
 *    - #:: operador cons perezoso (equivale a :: para List)
 * 
 * 3. VENTAJAS DE STREAMS:
 *    - Uso eficiente de memoria
 *    - Permite trabajar con datos infinitos
 *    - Solo calcula lo que necesitas
 *    - Optimización automática
 * 
 * 4. SINTAXIS IMPORTANTE:
 *    - LazyList(1, 2, 3)              // Stream finito
 *    - LazyList.from(1)               // Stream infinito desde 1
 *    - LazyList.continually(expr)     // Repetir expresión infinitamente
 *    - LazyList.iterate(init)(func)   // Aplicar función repetidamente
 * 
 * 5. CONEXIÓN CON SPARK:
 *    - Spark RDD usa evaluación perezosa
 *    - Las transformaciones son lazy, las acciones eager
 *    - Permite optimización del plan de ejecución
 *    - Fundamental para el rendimiento en Big Data
 * 
 * 6. PRÓXIMO PASO:
 *    - Ver 10-mapreduce.scala para conceptos MapReduce sin Spark
 */