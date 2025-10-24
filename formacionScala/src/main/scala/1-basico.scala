/**
 * 1-basico.scala
 * 
 * Conceptos básicos de Scala - Introducción al lenguaje
 * 
 * Este archivo demuestra:
 * - Qué es un object en Scala
 * - La función main como punto de entrada
 * - Sintaxis básica del lenguaje
 * - Diferencias con Java
 */

// En Scala, todo es un objeto. No existe el concepto de métodos estáticos como en Java.
// En su lugar, utilizamos "object" que son singletons (una sola instancia)
object BasicoScala {
  
  /**
   * La función main es el punto de entrada de la aplicación
   * Equivale al public static void main(String[] args) de Java
   * 
   * @param args Array de argumentos de línea de comandos
   */
  def main(args: Array[String]): Unit = {
    
    // Imprimir en consola - println es más conciso que System.out.println
    println("¡Hola mundo desde Scala!")
    println("=" * 50) // Repetir el carácter = 50 veces
    
    // Comentarios básicos sobre Scala
    mostrarCaracteristicasScala()
    
    // Demostrar sintaxis básica
    ejemploSintaxisBasica()
    
    // Trabajar con argumentos de línea de comandos
    procesarArgumentos(args)
  }
  
  /**
   * Función que explica las características principales de Scala
   */
  def mostrarCaracteristicasScala(): Unit = {
    println("CARACTERÍSTICAS DE SCALA:")
    println("-" * 30)
    
    // En Scala no necesitamos punto y coma al final de las líneas
    println("1. Scala combina programación orientada a objetos y funcional")
    println("2. Es un lenguaje compilado que se ejecuta en la JVM")
    println("3. Es interoperable con Java (puedes usar librerías Java)")
    println("4. Sintaxis más concisa que Java")
    println("5. Tipado estático con inferencia de tipos")
    println("6. Inmutabilidad por defecto")
    println()
  }
  
  /**
   * Ejemplos de sintaxis básica de Scala
   */
  def ejemploSintaxisBasica(): Unit = {
    println("SINTAXIS BÁSICA:")
    println("-" * 20)
    
    // En Scala, el tipo se puede inferir automáticamente
    val mensaje = "Scala es genial" // val = inmutable (como final en Java)
    var contador = 0              // var = mutable
    
    println(s"Mensaje: $mensaje")           // String interpolation con s
    println(s"Contador inicial: $contador")
    
    contador = contador + 1  // o simplemente: contador += 1
    println(s"Contador después: $contador")
    
    // Operaciones básicas
    val suma = 10 + 5
    val multiplicacion = 3 * 4
    val division = 15.0 / 3.0  // Usar .0 para trabajar con decimales
    
    println(s"Suma: $suma, Multiplicación: $multiplicacion, División: $division")
    
    // Boolean y operaciones lógicas
    val esVerdadero = true
    val esFalso = false
    val resultado = esVerdadero && !esFalso  // && = AND, || = OR, ! = NOT
    
    println(s"Resultado lógico: $resultado")
    println()
  }
  
  /**
   * Procesar argumentos de línea de comandos
   * 
   * @param argumentos Array de strings con los argumentos
   */
  def procesarArgumentos(argumentos: Array[String]): Unit = {
    println("ARGUMENTOS DE LÍNEA DE COMANDOS:")
    println("-" * 35)
    
    if (argumentos.length == 0) {
      println("No se proporcionaron argumentos")
      println("Ejecuta con: scala BasicoScala argumento1 argumento2 ...")
    } else {
      println(s"Se recibieron ${argumentos.length} argumentos:")
      
      // Iterar sobre los argumentos - varias formas de hacerlo
      for (i <- argumentos.indices) {
        println(s"  Argumento $i: ${argumentos(i)}")
      }
    }
    println()
    
    // Despedida
    println("¡Fin del programa básico de Scala!")
  }
}

/*
 * NOTAS IMPORTANTES:
 * 
 * 1. COMPILACIÓN Y EJECUCIÓN:
 *    - Compilar: mvn compile
 *    - Ejecutar: mvn exec:java -Dexec.mainClass="BasicoScala"
 *    - O con argumentos: mvn exec:java -Dexec.mainClass="BasicoScala" -Dexec.args="arg1 arg2"
 * 
 * 2. DIFERENCIAS CON JAVA:
 *    - No necesitas escribir 'public static' 
 *    - No necesitas punto y coma al final de las líneas
 *    - El tipo de retorno va después del nombre de la función
 *    - Unit equivale a void en Java
 *    - String interpolation con s"texto $variable"
 * 
 * 3. CONCEPTOS CLAVE:
 *    - object: Singleton, equivale a una clase con métodos estáticos
 *    - def: Define una función
 *    - val: Variable inmutable (recomendado)
 *    - var: Variable mutable (usar solo cuando sea necesario)
 *    - Los tipos se pueden inferir automáticamente
 * 
 * 4. PRÓXIMO PASO:
 *    - Ver 2-variables.scala para aprender sobre tipos de datos
 */