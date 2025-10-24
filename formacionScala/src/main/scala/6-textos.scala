/**
 * 6-textos.scala
 * 
 * Manipulación de textos y strings en Scala
 * 
 * Este archivo demuestra:
 * - Operaciones básicas con strings
 * - String interpolation avanzada
 * - Expresiones regulares
 * - Formateo de strings
 * - Métodos funcionales para strings
 * - Parsing y validación de texto
 */

import scala.util.matching.Regex

object ManipulacionTextos {
  
  def main(args: Array[String]): Unit = {
    
    println("MANIPULACIÓN DE TEXTOS EN SCALA")
    println("=" * 33)
    
    // Operaciones básicas
    operacionesBasicas()
    
    // String interpolation
    stringInterpolation()
    
    // Métodos útiles de String
    metodosUtiles()
    
    // Expresiones regulares
    expresionesRegulares()
    
    // Formateo avanzado
    formateoAvanzado()
    
    // Procesamiento funcional de texto
    procesamientoFuncional()
    
    // Validación y parsing
    validacionYParsing()
  }
  
  /**
   * Operaciones básicas con strings
   */
  def operacionesBasicas(): Unit = {
    println("OPERACIONES BÁSICAS:")
    println("-" * 20)
    
    val texto = "Scala es genial"
    val otroTexto = "  para Big Data  "
    
    // Propiedades básicas
    println(s"Texto original: '$texto'")
    println(s"Longitud: ${texto.length}")
    println(s"Vacío: ${texto.isEmpty}")
    println(s"No vacío: ${texto.nonEmpty}")
    
    // Acceso a caracteres
    println(s"Primer carácter: ${texto.head}")
    println(s"Último carácter: ${texto.last}")
    println(s"Carácter en posición 2: ${texto(2)}")  // 'a'
    println(s"Substring (0,5): ${texto.substring(0, 5)}")
    
    // Concatenación
    val textoCompleto = texto + " " + otroTexto.trim
    println(s"Concatenación: '$textoCompleto'")
    
    // Comparaciones
    println(s"¿Son iguales? ${texto == "Scala es genial"}")
    println(s"¿Comienza con 'Scala'? ${texto.startsWith("Scala")}")
    println(s"¿Termina con 'genial'? ${texto.endsWith("genial")}")
    println(s"¿Contiene 'es'? ${texto.contains("es")}")
    
    // Transformaciones básicas
    println(s"Mayúsculas: ${texto.toUpperCase}")
    println(s"Minúsculas: ${texto.toLowerCase}")
    println(s"Capitalizar: ${texto.toLowerCase.capitalize}")
    
    // Limpiar espacios
    println(s"Con espacios: '$otroTexto'")
    println(s"Sin espacios: '${otroTexto.trim}'")
    
    println()
  }
  
  /**
   * String interpolation - diferentes tipos
   */
  def stringInterpolation(): Unit = {
    println("STRING INTERPOLATION:")
    println("-" * 21)
    
    val nombre = "Ana"
    val edad = 25
    val salario = 45000.75
    
    // Interpolación básica con 's'
    val mensaje = s"Hola $nombre, tienes $edad años"
    println(s"Interpolación 's': $mensaje")
    
    // Interpolación con expresiones
    val calculado = s"El año que viene tendrás ${edad + 1} años"
    println(s"Con expresiones: $calculado")
    
    // Interpolación formateada con 'f'
    val formateado = f"$nombre%s gana $salario%.2f euros al año"
    println(s"Interpolación 'f': $formateado")
    
    // Más ejemplos de formato
    val numero = 42
    val porcentaje = 0.1567
    println(f"Número: $numero%04d")           // Relleno con ceros: 0042
    println(f"Porcentaje: ${porcentaje * 100}%.1f%%")  // 15.7%
    
    // Interpolación raw con 'raw' (no escapa caracteres)
    val path = raw"C:\\users\\$nombre\\documents"
    println(s"Path raw: $path")
    
    // String multilínea con stripMargin
    val textoMultilinea = s"""
      |Nombre: $nombre
      |Edad: $edad años
      |Salario: $salario euros
      |Estado: ${if (edad >= 18) "Adulto" else "Menor"}
      |""".stripMargin
    
    println("Texto multilínea:")
    println(textoMultilinea)
    
    println()
  }
  
  /**
   * Métodos útiles para trabajar con strings
   */
  def metodosUtiles(): Unit = {
    println("MÉTODOS ÚTILES:")
    println("-" * 15)
    
    val frase = "Scala es un lenguaje de programación"
    val numeros = "1,2,3,4,5"
    val emails = "ana@test.com, juan@test.com, maria@test.com"
    
    // División de strings
    val palabras = frase.split(" ")
    println(s"Palabras: ${palabras.mkString("[", ", ", "]")}")
    
    val listaNumeros = numeros.split(",").map(_.toInt)
    println(s"Números: ${listaNumeros.mkString(", ")}")
    
    // Unir arrays en string
    val nuevaFrase = palabras.mkString(" | ")
    println(s"Unir con |: $nuevaFrase")
    
    // Reemplazos
    val fraseModificada = frase.replace("Scala", "Python")
    println(s"Reemplazo: $fraseModificada")
    
    val fraseReemplazos = frase.replaceAll("\\s+", "_")  // Regex: espacios por _
    println(s"Espacios por _: $fraseReemplazos")
    
    // Búsquedas
    val indiceScala = frase.indexOf("Scala")
    val ultimaA = frase.lastIndexOf("a")
    println(s"Índice de 'Scala': $indiceScala")
    println(s"Último índice de 'a': $ultimaA")
    
    // Repetición
    val guiones = "-" * 20
    println(s"Repetir: $guiones")
    
    // Reversión
    val fraseInvertida = frase.reverse
    println(s"Invertida: $fraseInvertida")
    
    // Trabajo con líneas
    val textoVarias = """Primera línea
                        |Segunda línea
                        |Tercera línea""".stripMargin
    
    val lineas = textoVarias.linesIterator.toList
    println(s"Número de líneas: ${lineas.length}")
    lineas.zipWithIndex.foreach { case (linea, i) => 
      println(s"  Línea ${i + 1}: $linea")
    }
    
    println()
  }
  
  /**
   * Expresiones regulares (regex)
   */
  def expresionesRegulares(): Unit = {
    println("EXPRESIONES REGULARES:")
    println("-" * 22)
    
    val texto = "Mi email es juan@ejemplo.com y mi teléfono es 123-456-789"
    val documento = """
      |Juan Pérez - 28 años - juan@test.com - 666-777-888
      |Ana García - 35 años - ana@test.com - 555-666-777
      |Luis Martín - 42 años - luis@test.com - 444-555-666
      |""".stripMargin
    
    // Crear regex
    val emailRegex: Regex = """[\w\.]+@[\w\.]+\.\w+""".r
    val telefonoRegex: Regex = """\d{3}-\d{3}-\d{3}""".r
    val edadRegex: Regex = """(\d+) años""".r
    
    // Encontrar primera coincidencia
    val primerEmail = emailRegex.findFirstIn(texto)
    println(s"Primer email: $primerEmail")
    
    val primerTelefono = telefonoRegex.findFirstIn(texto)
    println(s"Primer teléfono: $primerTelefono")
    
    // Encontrar todas las coincidencias
    val todosEmails = emailRegex.findAllIn(documento).toList
    println(s"Todos los emails: $todosEmails")
    
    val todosTelefonos = telefonoRegex.findAllIn(documento).toList
    println(s"Todos los teléfonos: $todosTelefonos")
    
    // Extraer con grupos de captura
    val edades = edadRegex.findAllMatchIn(documento).map(_.group(1)).toList
    println(s"Edades extraídas: $edades")
    
    // Validar formato
    def esEmailValido(email: String): Boolean = {
      emailRegex.pattern.matcher(email).matches()
    }
    
    println(s"¿Es válido 'test@test.com'? ${esEmailValido("test@test.com")}")
    println(s"¿Es válido 'email-inválido'? ${esEmailValido("email-inválido")}")
    
    // Reemplazar con regex
    val textoLimpio = documento.replaceAll("""\d{3}-\d{3}-\d{3}""", "[TELÉFONO]")
    println("Texto con teléfonos ocultados:")
    println(textoLimpio)
    
    // Pattern matching con regex
    def analizarContacto(linea: String): String = {
      linea match {
        case emailRegex() => "Contiene email"
        case telefonoRegex() => "Contiene teléfono"
        case _ => "No contiene contacto"
      }
    }
    
    println(s"Análisis 'juan@test.com': ${analizarContacto("juan@test.com")}")
    println(s"Análisis '666-777-888': ${analizarContacto("666-777-888")}")
    
    println()
  }
  
  /**
   * Formateo avanzado de strings
   */
  def formateoAvanzado(): Unit = {
    println("FORMATEO AVANZADO:")
    println("-" * 18)
    
    val productos = List(
      ("Laptop", 1299.99, 2),
      ("Mouse", 25.50, 5),
      ("Teclado", 89.99, 3),
      ("Monitor", 299.95, 1)
    )
    
    // Crear tabla formateada
    println("FACTURA")
    println("=" * 50)
    println(f"${"Producto"}%-15s ${"Precio"}%8s ${"Cant"}%4s ${"Total"}%8s")
    println("-" * 50)
    
    var total = 0.0
    for ((producto, precio, cantidad) <- productos) {
      val subtotal = precio * cantidad
      total += subtotal
      println(f"$producto%-15s $precio%8.2f $cantidad%4d $subtotal%8.2f")
    }
    
    println("-" * 50)
    println(f"${"TOTAL"}%-28s $total%8.2f")
    
    // Formateo de números
    val numero = 1234567.89
    println(s"\nFormatos de número $numero:")
    println(f"Con decimales: $numero%.2f")
    println(f"Científico: $numero%.2e")
    val porcentaje = (numero / 10000000) * 100
    println(f"Porcentaje: $porcentaje%.1f%%")
    
    // Formateo de fechas (usando java.time)
    import java.time.LocalDateTime
    import java.time.format.DateTimeFormatter
    
    val ahora = LocalDateTime.now()
    val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    println(s"Fecha actual: ${ahora.format(formatoFecha)}")
    
    // Alineación de texto
    val textos = List("Corto", "Texto medio", "Texto mucho más largo")
    println("\nAlineación de texto:")
    for (texto <- textos) {
      println(f"|$texto%-20s|")  // Alineado a la izquierda
      println(f"|$texto%20s|")   // Alineado a la derecha
    }
    
    println()
  }
  
  /**
   * Procesamiento funcional de texto
   */
  def procesamientoFuncional(): Unit = {
    println("PROCESAMIENTO FUNCIONAL:")
    println("-" * 24)
    
    val texto = "Scala es un lenguaje de programación funcional y orientado a objetos"
    val parrafo = """
      |Scala es un lenguaje moderno.
      |Combina programación funcional y orientada a objetos.
      |Es ideal para Big Data y aplicaciones distribuidas.
      |""".stripMargin
    
    // Trabajar con palabras usando métodos funcionales
    val palabras = texto.split(" ").toList
    
    // Filtrar palabras largas
    val palabrasLargas = palabras.filter(_.length > 5)
    println(s"Palabras largas (>5 chars): $palabrasLargas")
    
    // Transformar palabras
    val palabrasMayusculas = palabras.map(_.toUpperCase)
    println(s"En mayúsculas: ${palabrasMayusculas.take(5)}")
    
    // Contar caracteres por palabra
    val longitudesPalabras = palabras.map(palabra => (palabra, palabra.length))
    println("Longitudes:")
    longitudesPalabras.take(5).foreach { case (palabra, longitud) =>
      println(s"  '$palabra' -> $longitud chars")
    }
    
    // Estadísticas del texto
    val totalCaracteres = palabras.map(_.length).sum
    val promedioLongitud = totalCaracteres.toDouble / palabras.length
    val palabraMasLarga = palabras.maxBy(_.length)
    val palabraMasCorta = palabras.minBy(_.length)
    
    println(s"Total caracteres: $totalCaracteres")
    println(f"Promedio longitud: $promedioLongitud%.1f")
    println(s"Palabra más larga: '$palabraMasLarga'")
    println(s"Palabra más corta: '$palabraMasCorta'")
    
    // Procesar líneas del párrafo
    val lineas = parrafo.trim.split("\n").toList
    val lineasLimpias = lineas.map(_.trim).filter(_.nonEmpty)
    
    println(s"\nProcesamiento de párrafo:")
    println(s"Número de líneas: ${lineasLimpias.length}")
    
    // Contar palabras por línea
    val palabrasPorLinea = lineasLimpias.map(linea => 
      (linea, linea.split("\\s+").length)
    )
    
    palabrasPorLinea.foreach { case (linea, count) =>
      println(s"$count palabras: '${linea.take(30)}...'")
    }
    
    // Buscar patrones
    val lineasConScala = lineasLimpias.filter(_.toLowerCase.contains("scala"))
    println(s"Líneas que mencionan Scala: ${lineasConScala.length}")
    
    println()
  }
  
  /**
   * Validación y parsing de strings
   */
  def validacionYParsing(): Unit = {
    println("VALIDACIÓN Y PARSING:")
    println("-" * 21)
    
    // Validadores usando funciones
    def esNumeroEntero(str: String): Boolean = {
      try {
        str.toInt
        true
      } catch {
        case _: NumberFormatException => false
      }
    }
    
    def esNumeroDecimal(str: String): Boolean = {
      try {
        str.toDouble
        true
      } catch {
        case _: NumberFormatException => false
      }
    }
    
    def esEmailValido(email: String): Boolean = {
      val emailRegex = """^[\w\.-]+@[\w\.-]+\.\w+$""".r
      emailRegex.pattern.matcher(email).matches()
    }
    
    def esTelefonoValido(telefono: String): Boolean = {
      val telefonoRegex = """^\d{3}-\d{3}-\d{3}$""".r
      telefonoRegex.pattern.matcher(telefono).matches()
    }
    
    // Probar validadores
    val casos = List(
      "123", "12.34", "texto", "user@domain.com", "666-777-888", "invalid-phone"
    )
    
    println("Validaciones:")
    for (caso <- casos) {
      println(s"'$caso':")
      println(s"  ¿Es entero? ${esNumeroEntero(caso)}")
      println(s"  ¿Es decimal? ${esNumeroDecimal(caso)}")
      println(s"  ¿Es email? ${esEmailValido(caso)}")
      println(s"  ¿Es teléfono? ${esTelefonoValido(caso)}")
    }
    
    // Parsing seguro con Option
    def parseEnteroSeguro(str: String): Option[Int] = {
      try {
        Some(str.toInt)
      } catch {
        case _: NumberFormatException => None
      }
    }
    
    def parseDecimalSeguro(str: String): Option[Double] = {
      try {
        Some(str.toDouble)
      } catch {
        case _: NumberFormatException => None
      }
    }
    
    // Usar parsing seguro
    val textosNumericos = List("123", "45.67", "no-numero", "89")
    
    println("\nParsing seguro:")
    for (texto <- textosNumericos) {
      val entero = parseEnteroSeguro(texto)
      val decimal = parseDecimalSeguro(texto)
      
      println(s"'$texto' -> Int: $entero, Double: $decimal")
    }
    
    // Procesar CSV simple
    val csvLinea = "Juan,25,juan@test.com,666-777-888"
    val campos = csvLinea.split(",").map(_.trim)
    
    if (campos.length == 4) {
      val nombre = campos(0)
      val edad = parseEnteroSeguro(campos(1))
      val email = campos(2)
      val telefono = campos(3)
      
      println(s"\nProcessing CSV:")
      println(s"Nombre: $nombre")
      println(s"Edad: ${edad.getOrElse("Inválida")}")
      println(s"Email válido: ${esEmailValido(email)}")
      println(s"Teléfono válido: ${esTelefonoValido(telefono)}")
    }
    
    println("\n🎯 RESUMEN: Scala ofrece potentes herramientas para texto")
    println("🎯 RESUMEN: String interpolation es muy útil para formateo")
    println("🎯 RESUMEN: Regex integradas facilitan validación y extracción")
    println("🎯 RESUMEN: Métodos funcionales simplifican procesamiento")
  }
}

/*
 * NOTAS IMPORTANTES :
 * 
 * 1. EJECUTAR ESTE ARCHIVO:
 *    mvn exec:java -Dexec.mainClass="ManipulacionTextos"
 * 
 * 2. STRING INTERPOLATION:
 *    - s"texto $variable"              // Básica
 *    - f"texto $variable%.2f"          // Con formato
 *    - raw"texto sin\nescapes"         // Sin escapar caracteres
 * 
 * 3. REGEX EN SCALA:
 *    - val regex = "patrón".r          // Crear regex
 *    - regex.findFirstIn(texto)        // Primera coincidencia
 *    - regex.findAllIn(texto)          // Todas las coincidencias
 *    - texto.replaceAll("patrón", "reemplazo")
 * 
 * 4. MÉTODOS FUNCIONALES ÚTILES:
 *    - texto.split(" ").map(_.toUpperCase)
 *    - palabras.filter(_.length > 5)
 *    - palabras.groupBy(_.head)        // Agrupar por primer carácter
 * 
 * 5. PARSING SEGURO:
 *    - Usar try/catch para conversiones
 *    - Devolver Option[T] para manejar errores
 *    - Validar antes de procesar
 * 
 * 6. PRÓXIMO PASO:
 *    - Ver 7-fechas.scala para manejo de fechas y tiempo
 */