/**
 * 2-variables.scala
 * 
 * Variables, constantes y tipos de datos básicos en Scala
 * 
 * Este archivo demuestra:
 * - Diferencia entre val (inmutable) y var (mutable)
 * - Tipos de datos básicos en Scala
 * - Inferencia de tipos
 * - Conversiones de tipos
 * - Nulos y opciones
 */

object VariablesYTipos {
  
  def main(args: Array[String]): Unit = {
    
    println("VARIABLES Y TIPOS DE DATOS EN SCALA")
    println("=" * 40)
    
    // Demostrar val vs var
    demostrarValVsVar()
    
    // Tipos de datos básicos
    mostrarTiposBasicos()
    
    // Inferencia de tipos
    ejemplosInferenciaTipos()
    
    // Conversiones de tipos
    conversionesTipos()
    
    // Trabajar con nulos y Option
    manejarNulosYOpciones()
    
    // Constantes y literales especiales
    constantesEspeciales()
  }
  
  /**
   * Diferencia entre val (inmutable) y var (mutable)
   */
  def demostrarValVsVar(): Unit = {
    println("VAL vs VAR:")
    println("-" * 15)
    
    // val = inmutable (como final en Java)
    // Una vez asignado, no se puede cambiar
    val nombre = "Juan"
    val edad = 25
    
    println(s"Nombre (val): $nombre")
    println(s"Edad (val): $edad")
    
    // Esto daría error de compilación si lo descomentamos:
    // nombre = "Pedro"  // ERROR: reassignment to val
    
    // var = mutable (se puede cambiar)
    var contador = 0
    var temperatura = 20.5
    
    println(s"Contador inicial (var): $contador")
    println(s"Temperatura inicial (var): $temperatura")
    
    // Podemos cambiar variables var
    contador = contador + 1
    temperatura = 22.3
    
    println(s"Contador después (var): $contador")
    println(s"Temperatura después (var): $temperatura")
    
    println("\n💡 REGLA DE ORO: Usa 'val' siempre que puedas, 'var' solo cuando necesites cambiar el valor\n")
  }
  
  /**
   * Tipos de datos básicos en Scala
   */
  def mostrarTiposBasicos(): Unit = {
    println("TIPOS DE DATOS BÁSICOS:")
    println("-" * 25)
    
    // NÚMEROS ENTEROS
    val bytePequeno: Byte = 127           // -128 a 127
    val shortCorto: Short = 32767         // -32,768 a 32,767  
    val entero: Int = 2147483647          // -2^31 a 2^31-1 (más común)
    val largo: Long = 9223372036854775807L // -2^63 a 2^63-1 (nota la L al final)
    
    println(s"Byte: $bytePequeno")
    println(s"Short: $shortCorto") 
    println(s"Int: $entero")
    println(s"Long: $largo")
    
    // NÚMEROS DECIMALES
    val decimal: Float = 3.14159f         // 32 bits (nota la f al final)
    val doble: Double = 3.141592653589793 // 64 bits (más preciso, más común)
    
    println(s"Float: $decimal")
    println(s"Double: $doble")
    
    // CARÁCTER Y TEXTO
    val caracter: Char = 'A'              // Un solo carácter Unicode
    val texto: String = "Hola Scala"      // Cadena de caracteres
    
    println(s"Char: $caracter")
    println(s"String: $texto")
    
    // BOOLEANO
    val verdadero: Boolean = true
    val falso: Boolean = false
    
    println(s"Boolean true: $verdadero")
    println(s"Boolean false: $falso")
    
    // TIPO UNIT (equivale a void en Java)
    val sinValor: Unit = ()               // Representa "no hay valor"
    println(s"Unit: $sinValor")           // Imprime ()
    
    println()
  }
  
  /**
   * Ejemplos de inferencia de tipos
   */
  def ejemplosInferenciaTipos(): Unit = {
    println("INFERENCIA DE TIPOS:")
    println("-" * 20)
    
    // Scala puede inferir el tipo automáticamente
    val numeroAutomatico = 42             // Scala infiere que es Int
    val decimalAutomatico = 3.14          // Scala infiere que es Double
    val textoAutomatico = "Auto"          // Scala infiere que es String
    val booleanoAutomatico = true         // Scala infiere que es Boolean
    
    println(s"Número inferido como Int: $numeroAutomatico")
    println(s"Decimal inferido como Double: $decimalAutomatico")
    println(s"Texto inferido como String: $textoAutomatico")
    println(s"Booleano inferido: $booleanoAutomatico")
    
    // Podemos ser explícitos cuando queramos
    val numeroExplicito: Double = 42      // Forzamos que sea Double en lugar de Int
    val textoExplicito: String = "Explícito"
    
    println(s"Número explícito como Double: $numeroExplicito")
    println(s"Texto explícito: $textoExplicito")
    
    // Cuando Scala no puede inferir, debemos ser explícitos
    val lista = List(1, 2, 3)             // Scala infiere List[Int]
    val listaVacia: List[Int] = List()     // Necesitamos especificar el tipo para lista vacía
    
    println(s"Lista con inferencia: $lista")
    println(s"Lista vacía explícita: $listaVacia")
    
    println()
  }
  
  /**
   * Conversiones de tipos (casting)
   */
  def conversionesTipos(): Unit = {
    println("CONVERSIONES DE TIPOS:")
    println("-" * 22)
    
    // Conversiones automáticas (widening)
    val entero: Int = 100
    val largo: Long = entero              // Int se convierte automáticamente a Long
    val doble: Double = entero            // Int se convierte automáticamente a Double
    
    println(s"Int original: $entero")
    println(s"Convertido a Long: $largo")
    println(s"Convertido a Double: $doble")
    
    // Conversiones explícitas (narrowing) - pueden perder información
    val numeroGrande: Double = 123.789
    val enteroReducido: Int = numeroGrande.toInt      // Pierde los decimales
    val byteReducido: Byte = entero.toByte            // Puede perder información si es muy grande
    // En python sería int()    float(). bool() str()
    
    println(s"Double original: $numeroGrande")
    println(s"Convertido a Int (sin decimales): $enteroReducido")
    println(s"Int convertido a Byte: $byteReducido")
    
    // Conversiones de String
    val textoNumero = "123"
    val textoDecimal = "45.67"
    val textoBooleano = "true"
    
    val numeroDesdeTexto = textoNumero.toInt
    val decimalDesdeTexto = textoDecimal.toDouble
    val booleanoDesdeTexto = textoBooleano.toBoolean
    
    println(s"String '$textoNumero' a Int: $numeroDesdeTexto")
    println(s"String '$textoDecimal' a Double: $decimalDesdeTexto")
    println(s"String '$textoBooleano' a Boolean: $booleanoDesdeTexto")
    
    // Conversión a String
    val numero = 42
    val textoDesdeNumero = numero.toString
    println(s"Int $numero a String: '$textoDesdeNumero'")
    
    println()
  }
  
  /**
   * Manejo de nulos y Option
   */
  def manejarNulosYOpciones(): Unit = {
    println("NULOS Y OPTION:")
    println("-" * 15)
    
    // En Scala se evita usar null, en su lugar se usa Option
    val nombreCompleto: Option[String] = Some("Juan Pérez")
    val nombreVacio: Option[String] = None
    
    println(s"Nombre completo: $nombreCompleto")
    println(s"Nombre vacío: $nombreVacio")
    
    // Trabajar con Option de forma segura
    nombreCompleto match {
      case Some(nombre) => println(s"El nombre es: $nombre")
      case None => println("No hay nombre")
    }
    
    nombreVacio match {
      case Some(nombre) => println(s"El nombre es: $nombre")
      case None => println("No hay nombre disponible")
    }
    
    // Métodos útiles de Option
    println(s"¿Hay nombre completo? ${nombreCompleto.isDefined}")
    println(s"¿Está vacío el nombre? ${nombreVacio.isEmpty}")
    println(s"Nombre con valor por defecto: ${nombreVacio.getOrElse("Sin nombre")}")
    
    println()
  }
  
  /**
   * Constantes y literales especiales
   */
  def constantesEspeciales(): Unit = {
    println("CONSTANTES Y LITERALES ESPECIALES:")
    println("-" * 35)
    
    // Literales numéricos especiales
    val binario = Integer.parseInt("1010", 2)    // Número binario (10 en decimal)
    val octal = Integer.parseInt("12", 8)        // Número octal (10 en decimal)  
    val hexadecimal = 0xFF                       // Número hexadecimal (255 en decimal)
    
    println(s"Binario 1010 = $binario")
    println(s"Octal 12 = $octal")
    println(s"Hexadecimal 0xFF = $hexadecimal")
    
    // Separadores en números grandes
    val millones = 1_000_000          // Más legible que 1000000
    val tarjetaCredito = 1234_5678_9012_3456L
    
    println(s"Un millón: $millones")
    println(s"Número de tarjeta: $tarjetaCredito")
    
    // Strings especiales
    val textoMultilinea = """
      |Este es un texto
      |que ocupa múltiples
      |líneas y preserva el formato
      |""".stripMargin
    
    println("Texto multilínea:")
    println(textoMultilinea)
    
    // String interpolation avanzada
    val nombre = "Ana"
    val puntuacion = 95.7
    val formatoComplejo = f"$nombre%s tiene una puntuación de $puntuacion%.2f%%"
    
    println(s"Formato complejo: $formatoComplejo")
    
    println("\n🎯 RESUMEN: Usa val por defecto, var solo cuando necesites cambiar el valor")
    println("🎯 RESUMEN: Scala infiere tipos automáticamente, pero puedes ser explícito")
    println("🎯 RESUMEN: Usa Option en lugar de null para valores que pueden no existir")
  }
}

/*
 * NOTAS IMPORTANTES:
 * 
 * 1. EJECUTAR ESTE ARCHIVO:
 *    mvn exec:java -Dexec.mainClass="VariablesYTipos"
 * 
 * 2. JERARQUÍA DE TIPOS EN SCALA:
 *    - Any (raíz de todo)
 *      ├── AnyVal (tipos primitivos)
 *      │   ├── Boolean, Char
 *      │   ├── Byte, Short, Int, Long
 *      │   ├── Float, Double
 *      │   └── Unit
 *      └── AnyRef (tipos de referencia)
 *          ├── String
 *          ├── Option
 *          └── todas las clases
 * 
 * 3. BUENAS PRÁCTICAS:
 *    - Prefiere val sobre var (inmutabilidad)
 *    - Deja que Scala infiera los tipos cuando sea obvio
 *    - Usa Option en lugar de null
 *    - Usa tipos explícitos cuando mejore la legibilidad
 * 
 * 4. PRÓXIMO PASO:
 *    - Ver 3-imperativo.scala para estructuras de control
 */