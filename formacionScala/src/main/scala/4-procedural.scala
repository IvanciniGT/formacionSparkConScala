/**
 * 4-procedural.scala
 * 
 * Programación procedural en Scala - Funciones simples
 * 
 * Este archivo demuestra:
 * - Definición de funciones básicas
 * - Parámetros y tipos de retorno
 * - Funciones con parámetros por defecto
 * - Funciones con número variable de parámetros
 * - Funciones recursivas
 * - Ámbito de variables (scope)
 * - Funciones anidadas
 */

object ProgramacionProcedural {
  
  def main(args: Array[String]): Unit = {
    
    println("PROGRAMACIÓN PROCEDURAL EN SCALA")
    println("=" * 35)
    
    // Funciones básicas
    demostrarFuncionesBasicas()
    
    // Parámetros y valores por defecto
    parametrosYDefectos()
    
    // Funciones con múltiples parámetros
    funcionesParametrosMultiples()
    
    // Recursividad
    ejemplosRecursividad()
    
    // Ámbito de variables
    demostrarAmbito()
    
    // Funciones anidadas
    funcionesAnidadas()
  }
  
  /**
   * Funciones básicas - sintaxis y ejemplos
   */
  def demostrarFuncionesBasicas(): Unit = {
    println("FUNCIONES BÁSICAS:")
    println("-" * 18)
    
    // Llamar a funciones simples
    saludar()
    val suma = sumar(5, 3)
    println(s"Suma de 5 + 3 = $suma")
    
    val esPar = esNumeroPar(10)
    println(s"¿Es 10 par? $esPar")
    
    val mensaje = crearMensaje("Ana", 25)
    println(mensaje)
    
    // Función que no devuelve nada (Unit)
    mostrarInfo("Este es un mensaje informativo")
    
    println()
  }
  
  /**
   * Función simple sin parámetros y sin retorno
   */
  def saludar(): Unit = {
    println("¡Hola desde una función!")
  }
  
  /**
   * Función con parámetros que devuelve un valor
   * 
   * @param a primer número
   * @param b segundo número
   * @return la suma de a y b
   */
  def sumar(a: Int, b: Int): Int = {
    a + b  // En Scala, la última expresión es el valor de retorno
  }
  
  /**
   * Función con lógica condicional
   * 
   * @param numero el número a verificar
   * @return true si es par, false si es impar
   */
  def esNumeroPar(numero: Int): Boolean = {
    numero % 2 == 0
  }
  
  /**
   * Función que construye un String
   * 
   * @param nombre nombre de la persona
   * @param edad edad de la persona
   * @return mensaje formateado
   */
  def crearMensaje(nombre: String, edad: Int): String = {
    s"Hola $nombre, tienes $edad años"
  }
  
  /**
   * Función que solo realiza una acción (devuelve Unit)
   * 
   * @param info mensaje a mostrar
   */
  def mostrarInfo(info: String): Unit = {
    println(s"INFO: $info")
  }
  
  /**
   * Demostrar parámetros por defecto y sobrecarga
   */
  def parametrosYDefectos(): Unit = {
    println("PARÁMETROS Y VALORES POR DEFECTO:")
    println("-" * 33)
    
    // Funciones con parámetros por defecto
    println(crearSaludo("Juan"))                    // Usa saludo por defecto
    println(crearSaludo("María", "Buenos días"))    // Saludo personalizado
    
    // Parámetros nombrados
    println(formatearNombre("juan", capitalizar = true, espacios = false))
    println(formatearNombre("ana luisa", capitalizar = true, espacios = true))
    
    // Función con múltiples parámetros por defecto
    val config = configurarServidor()  // Todos por defecto
    println(s"Configuración por defecto: $config")
    
    val configPersonalizada = configurarServidor(puerto = 8080, ssl = true)
    println(s"Configuración personalizada: $configPersonalizada")
    
    println()
  }
  
  /**
   * Función con parámetro por defecto
   * 
   * @param nombre nombre de la persona
   * @param saludo saludo a usar (por defecto "Hola")
   * @return mensaje de saludo
   */
  def crearSaludo(nombre: String, saludo: String = "Hola"): String = {
    s"$saludo $nombre!"
  }
  
  /**
   * Función con múltiples parámetros y lógica
   * 
   * @param nombre nombre a formatear
   * @param capitalizar si debe capitalizar
   * @param espacios si debe limpiar espacios extra
   * @return nombre formateado
   */
  def formatearNombre(nombre: String, capitalizar: Boolean = false, espacios: Boolean = false): String = {
    var resultado = nombre
    
    // Limpiar espacios si se solicita
    if (espacios) {
      resultado = resultado.trim.replaceAll("\\s+", " ")
    }
    
    // Capitalizar si se solicita
    if (capitalizar) {
      resultado = resultado.split(" ").map(_.capitalize).mkString(" ")
    }
    
    resultado
  }
  
  /**
   * Función con múltiples parámetros por defecto
   * 
   * @param host dirección del servidor
   * @param puerto puerto del servidor
   * @param ssl si usar SSL
   * @return configuración como string
   */
  def configurarServidor(host: String = "localhost", puerto: Int = 3000, ssl: Boolean = false): String = {
    val protocolo = if (ssl) "https" else "http"
    s"$protocolo://$host:$puerto"
  }
  
  /**
   * Funciones con parámetros variables
   */
  def funcionesParametrosMultiples(): Unit = {
    println("FUNCIONES CON PARÁMETROS VARIABLES:")
    println("-" * 35)
    
    // Función con número variable de parámetros
    println(s"Suma variable: ${sumarVarios(1, 2, 3, 4, 5)}")
    println(s"Máximo: ${encontrarMaximo(10, 5, 8, 15, 3)}")
    
    // Pasar array como parámetros variables
    val numeros = Array(1, 2, 3, 4, 5)
    println(s"Suma de array: ${sumarVarios(numeros: _*)}")  // _* expande el array
    
    // Función con diferentes tipos de parámetros
    imprimirReporte("Ventas", "Enero", 1000.0, 1200.0, 900.0, 1100.0)
    
    println()
  }
  
  /**
   * Función con número variable de parámetros enteros
   * 
   * @param numeros secuencia variable de números
   * @return suma de todos los números
   */
  def sumarVarios(numeros: Int*): Int = {
    numeros.sum  // Método sum de la colección
  }
  
  /**
   * Encontrar el máximo entre varios números
   * 
   * @param numeros secuencia variable de números
   * @return el número máximo
   */
  def encontrarMaximo(numeros: Int*): Int = {
    if (numeros.isEmpty) 0 else numeros.max
  }
  
  /**
   * Función con parámetros fijos y variables
   * 
   * @param titulo título del reporte
   * @param mes mes del reporte
   * @param valores valores variables para el reporte
   */
  def imprimirReporte(titulo: String, mes: String, valores: Double*): Unit = {
    println(s"=== REPORTE DE $titulo - $mes ===")
    valores.zipWithIndex.foreach { case (valor, indice) =>
      println(s"  Día ${indice + 1}: $$$valor")
    }
    println(s"Total: $${valores.sum}")
    println(s"Promedio: $${valores.sum / valores.length}")
  }
  
  /**
   * Ejemplos de recursividad
   */
  def ejemplosRecursividad(): Unit = {
    println("RECURSIVIDAD:")
    println("-" * 13)
    
    // Factorial
    println(s"Factorial de 5: ${factorial(5)}")
    println(s"Factorial de 7: ${factorial(7)}")
    
    // Fibonacci
    println(s"Fibonacci de 8: ${fibonacci(8)}")
    println("Secuencia Fibonacci hasta 10:")
    for (i <- 0 to 10) {
      print(s"${fibonacci(i)} ")
    }
    println()
    
    // Contar dígitos
    println(s"Dígitos en 12345: ${contarDigitos(12345)}")
    
    // Potencia
    println(s"2 elevado a 8: ${potencia(2, 8)}")
    
    println()
  }
  
  /**
   * Función recursiva para calcular factorial
   * 
   * @param n número para calcular factorial
   * @return n!
   */
  def factorial(n: Int): Long = {
    if (n <= 1) 1 else n * factorial(n - 1)
  }
  
  /**
   * Función recursiva para calcular Fibonacci
   * 
   * @param n posición en la secuencia
   * @return número de Fibonacci en posición n
   */
  def fibonacci(n: Int): Long = {
    if (n <= 1) n else fibonacci(n - 1) + fibonacci(n - 2)
  }
  
  /**
   * Función recursiva para contar dígitos
   * 
   * @param numero número a analizar
   * @return cantidad de dígitos
   */
  def contarDigitos(numero: Int): Int = {
    if (numero < 10) 1 else 1 + contarDigitos(numero / 10)
  }
  
  /**
   * Función recursiva para calcular potencia
   * 
   * @param base base de la potencia
   * @param exponente exponente
   * @return base^exponente
   */
  def potencia(base: Int, exponente: Int): Long = {
    if (exponente == 0) 1 else base * potencia(base, exponente - 1)
  }
  
  /**
   * Demostrar ámbito de variables (scope)
   */
  def demostrarAmbito(): Unit = {
    println("ÁMBITO DE VARIABLES:")
    println("-" * 20)
    
    val variableGlobal = "Soy global del método"
    
    // Bloque con ámbito local
    {
      val variableLocal = "Soy local del bloque"
      println(s"Desde bloque - Global: $variableGlobal")
      println(s"Desde bloque - Local: $variableLocal")
    }
    
    // Esta línea daría error si la descomentamos:
    // println(variableLocal)  // ERROR: variableLocal no existe aquí
    
    // Función que usa variables del ámbito superior
    def funcionInterna(parametro: String): String = {
      s"$variableGlobal + $parametro"  // Puede acceder a variableGlobal
    }
    
    println(funcionInterna("parámetro local"))
    
    // Demostrar shadowing (ocultación de variables)
    val numero = 10
    
    def mostrarShadowing(): Unit = {
      val numero = 20  // Oculta la variable del ámbito superior
      println(s"Número en función interna: $numero")  // Imprime 20
    }
    
    println(s"Número en ámbito superior: $numero")  // Imprime 10
    mostrarShadowing()
    
    println()
  }
  
  /**
   * Funciones anidadas
   */
  def funcionesAnidadas(): Unit = {
    println("FUNCIONES ANIDADAS:")
    println("-" * 19)
    
    // Ejemplo de función con funciones internas
    val resultado = calcularEstadisticas(Array(10, 20, 30, 40, 50))
    println(s"Estadísticas: $resultado")
    
    // Función que devuelve otra función
    val multiplicadorPor3 = crearMultiplicador(3)
    println(s"5 multiplicado por 3: ${multiplicadorPor3(5)}")
    
    val multiplicadorPor7 = crearMultiplicador(7)
    println(s"4 multiplicado por 7: ${multiplicadorPor7(4)}")
    
    println()
    println("🎯 RESUMEN: Las funciones organizan y reutilizan código")
    println("🎯 RESUMEN: Usa parámetros por defecto para mayor flexibilidad")
    println("🎯 RESUMEN: La recursividad es útil para problemas que se dividen")
  }
  
  /**
   * Función con funciones anidadas para cálculos estadísticos
   * 
   * @param datos array de números
   * @return string con estadísticas
   */
  def calcularEstadisticas(datos: Array[Int]): String = {
    
    // Función anidada para calcular suma
    def suma(numeros: Array[Int]): Int = {
      numeros.sum
    }
    
    // Función anidada para calcular promedio
    def promedio(numeros: Array[Int]): Double = {
      suma(numeros).toDouble / numeros.length
    }
    
    // Función anidada para encontrar máximo
    def maximo(numeros: Array[Int]): Int = {
      numeros.max
    }
    
    // Función anidada para encontrar mínimo
    def minimo(numeros: Array[Int]): Int = {
      numeros.min
    }
    
    // Usar las funciones anidadas
    val s = suma(datos)
    val p = promedio(datos)
    val max = maximo(datos)
    val min = minimo(datos)
    
    s"Suma: $s, Promedio: $p, Máximo: $max, Mínimo: $min"
  }
  
  /**
   * Función que devuelve otra función (closure)
   * 
   * @param factor factor de multiplicación
   * @return función que multiplica por el factor
   */
  def crearMultiplicador(factor: Int): Int => Int = {
    // Función anónima que "recuerda" el valor de factor
    (numero: Int) => numero * factor
  }
}

/*
 * NOTAS IMPORTANTES :
 * 
 * 1. EJECUTAR ESTE ARCHIVO:
 *    mvn exec:java -Dexec.mainClass="ProgramacionProcedural"
 * 
 * 2. SINTAXIS DE FUNCIONES:
 *    def nombreFuncion(parametro: Tipo): TipoRetorno = {
 *      // cuerpo de la función
 *      valor_retorno  // última expresión
 *    }
 * 
 * 3. CONCEPTOS CLAVE:
 *    - def: Define una función
 *    - Unit: Equivale a void en Java
 *    - Parámetros por defecto: param: Tipo = valorPorDefecto
 *    - Parámetros variables: param: Tipo*
 *    - Última expresión es el valor de retorno
 *    - Funciones anidadas: funciones dentro de funciones
 * 
 * 4. BUENAS PRÁCTICAS:
 *    - Funciones pequeñas y enfocadas en una tarea
 *    - Usa parámetros por defecto para mayor flexibilidad
 *    - Prefiere immutabilidad (val sobre var)
 *    - Usa funciones anidadas para organizar código complejo
 *    - Evita recursión profunda sin optimización de cola
 * 
 * 5. PRÓXIMO PASO:
 *    - Ver 5-funcional.scala para programación funcional avanzada
 */