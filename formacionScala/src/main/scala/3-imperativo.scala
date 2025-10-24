/**
 * 3-imperativo.scala
 * 
 * Programación imperativa en Scala
 * 
 * Este archivo demuestra:
 * - Estructuras condicionales (if, else if, else)
 * - Bucles (for, while, do-while)
 * - Pattern matching (equivalente mejorado de switch)
 * - Control de flujo (break/continue equivalentes)
 * - Expresiones vs declaraciones
 */

import scala.util.control.Breaks._

object ProgramacionImperativa {
  
  def main(args: Array[String]): Unit = {
    
    println("PROGRAMACIÓN IMPERATIVA EN SCALA")
    println("=" * 35)
    
    // Estructuras condicionales
    estructurasCondicionales()
    
    // Bucles básicos
    buclesBasicos()
    
    // Bucles for avanzados
    buclesForAvanzados()
    
    // Pattern matching
    patternMatching()
    
    // Control de flujo
    controlDeFlujo()
    
    // Expresiones vs declaraciones
    expresionesVsDeclaraciones()
  }
  
  /**
   * Estructuras condicionales: if, else if, else
   */
  def estructurasCondicionales(): Unit = {
    println("ESTRUCTURAS CONDICIONALES:")
    println("-" * 25)
    
    val edad = 20
    val tieneLicencia = true
    val dinero = 150.0
    
    // IF básico - En Scala, if es una expresión (devuelve un valor)
    val mensaje = if (edad >= 18) "Es mayor de edad" else "Es menor de edad"
    println(s"Edad $edad: $mensaje")
    
    // IF múltiple (else if)
    val categoria = if (edad < 13) {
      "Niño"
    } else if (edad < 18) {
      "Adolescente"  
    } else if (edad < 65) {
      "Adulto"
    } else {
      "Senior"
    }
    println(s"Categoría por edad: $categoria")
    
    // Condiciones compuestas
    if (edad >= 18 && tieneLicencia) {
      println("✅ Puede conducir")
    } else {
      println("❌ No puede conducir")
    }
    
    // Operadores lógicos
    val puedeComprar = edad >= 18 || dinero > 100
    val descuentoEspecial = edad > 65 && dinero > 200
    val requiereSupervision = !(edad >= 18) // equivale a edad < 18
    
    println(s"Puede comprar: $puedeComprar")
    println(s"Descuento especial: $descuentoEspecial")  
    println(s"Requiere supervisión: $requiereSupervision")
    
    println()
  }
  
  /**
   * Bucles básicos: while, do-while
   */
  def buclesBasicos(): Unit = {
    println("BUCLES BÁSICOS:")
    println("-" * 15)
    
    // WHILE - Evalúa la condición antes de ejecutar
    println("Bucle while (0 a 4):")
    var contador = 0
    while (contador < 5) {
      print(s"$contador ")
      contador += 1  // equivale a contador = contador + 1
    }
    println()
    
    // DO-WHILE - Ejecuta al menos una vez, luego evalúa la condición
    println("Bucle do-while (10 a 7):")
    var numero = 10
    do {
      print(s"$numero ")
      numero -= 1
    } while (numero >= 7)
    println()
    
    // Ejemplo práctico: buscar un elemento
    val numeros = Array(1, 3, 5, 7, 9, 11)
    val buscar = 7
    var encontrado = false
    var indice = 0
    
    while (indice < numeros.length && !encontrado) {
      if (numeros(indice) == buscar) {
        encontrado = true
        println(s"Número $buscar encontrado en posición $indice")
      }
      indice += 1
    }
    
    if (!encontrado) {
      println(s"Número $buscar no encontrado")
    }
    
    println()
  }
  
  /**
   * Bucles for con diferentes sintaxis
   */
  def buclesForAvanzados(): Unit = {
    println("BUCLES FOR AVANZADOS:")
    println("-" * 20)
    
    // FOR básico con rango
    println("For con rango (1 a 5):")
    for (i <- 1 to 5) {
      print(s"$i ")
    }
    println()
    
    // FOR con until (excluye el último)
    println("For con until (1 hasta 5, sin incluir 5):")
    for (i <- 1 until 5) {
      print(s"$i ")
    }
    println()
    
    // FOR con step (saltos)
    println("For con step de 2 (0 a 10):")
    for (i <- 0 to 10 by 2) {
      print(s"$i ")
    }
    println()
    
    // FOR con colecciones
    val frutas = Array("manzana", "banana", "naranja", "pera")
    println("For con array de frutas:")
    for (fruta <- frutas) {
      println(s"  - $fruta")
    }
    
    // FOR con índices
    println("For con índices:")
    for (i <- frutas.indices) {
      println(s"  Posición $i: ${frutas(i)}")
    }
    
    // FOR con filtros (guardas)
    println("For con filtro (números pares):")
    for (i <- 1 to 10 if i % 2 == 0) {
      print(s"$i ")
    }
    println()
    
    // FOR anidado
    println("For anidado (tabla de multiplicar 3x3):")
    for (i <- 1 to 3; j <- 1 to 3) {
      print(s"${i*j} ")
      if (j == 3) println() // Nueva línea después de cada fila
    }
    
    // FOR con yield (genera una nueva colección)
    println("For con yield (cuadrados de 1 a 5):")
    val cuadrados = for (i <- 1 to 5) yield i * i
    println(s"Cuadrados: ${cuadrados.mkString(", ")}")
    
    // FOR complejo con múltiples generadores y filtros
    println("For complejo - coordenadas pares:")
    val coordenadas = for {
      x <- 1 to 3
      y <- 1 to 3
      if (x + y) % 2 == 0  // Solo coordenadas donde la suma sea par
    } yield (x, y)
    
    println(s"Coordenadas: ${coordenadas.mkString(", ")}")
    
    println()
  }
  
  /**
   * Pattern matching - equivalente mejorado de switch
   */
  def patternMatching(): Unit = {
    println("PATTERN MATCHING:")
    println("-" * 17)
    
    val dia = 3
    
    // Pattern matching básico
    val nombreDia = dia match {
      case 1 => "Lunes"
      case 2 => "Martes"  
      case 3 => "Miércoles"
      case 4 => "Jueves"
      case 5 => "Viernes"
      case 6 | 7 => "Fin de semana"  // Múltiples valores
      case _ => "Día inválido"       // Caso por defecto
    }
    
    println(s"Día $dia: $nombreDia")
    
    // Pattern matching con guardas
    val nota = 85
    val calificacion = nota match {
      case n if n >= 90 => "Excelente"
      case n if n >= 80 => "Muy bueno"
      case n if n >= 70 => "Bueno"
      case n if n >= 60 => "Suficiente"
      case _ => "Insuficiente"
    }
    
    println(s"Nota $nota: $calificacion")
    
    // Pattern matching con tipos
    def describir(x: Any): String = x match {
      case i: Int => s"Es un entero: $i"
      case s: String => s"Es un texto: '$s'"
      case b: Boolean => s"Es un booleano: $b"
      case d: Double => s"Es un decimal: $d"
      case _ => "Tipo desconocido"
    }
    
    println(describir(42))
    println(describir("Hola"))
    println(describir(true))
    println(describir(3.14))
    
    // Pattern matching con rangos
    val temperatura = 22
    val sensacion = temperatura match {
      case t if t < 0 => "Muy frío"
      case t if t < 10 => "Frío"
      case t if t < 20 => "Fresco"
      case t if t < 30 => "Agradable"
      case _ => "Calor"
    }
    
    println(s"Temperatura $temperatura°C: $sensacion")
    
    println()
  }
  
  /**
   * Control de flujo - equivalentes a break y continue
   */
  def controlDeFlujo(): Unit = {
    println("CONTROL DE FLUJO:")
    println("-" * 17)
    
    // Scala no tiene break/continue nativos, pero podemos simularlos
    
    // Simulando "continue" con filtros en for
    println("Simular continue - solo números impares:")
    for (i <- 1 to 10 if i % 2 != 0) {
      print(s"$i ")
    }
    println()
    
    // Simulando "break" con Breaks
    println("Simular break - parar en el primer número mayor que 6:")
    breakable {
      for (i <- 1 to 10) {
        if (i > 6) break()
        print(s"$i ")
      }
    }
    println()
    
    // Alternativa funcional - usando takeWhile
    println("Alternativa funcional al break:")
    val numeros = (1 to 10).takeWhile(_ <= 6)
    print(numeros.mkString(" "))
    println()
    
    // Return temprano en función
    def buscarPrimerPar(numeros: Array[Int]): Option[Int] = {
      for (numero <- numeros) {
        if (numero % 2 == 0) {
          return Some(numero)  // Return temprano
        }
      }
      None
    }
    
    val lista = Array(1, 3, 5, 8, 9, 10)
    val primerPar = buscarPrimerPar(lista)
    println(s"Primer número par en [${lista.mkString(", ")}]: $primerPar")
    
    println()
  }
  
  /**
   * Diferencia entre expresiones y declaraciones
   */
  def expresionesVsDeclaraciones(): Unit = {
    println("EXPRESIONES VS DECLARACIONES:")
    println("-" * 30)
    
    // En Scala, casi todo es una expresión (devuelve un valor)
    
    // IF como expresión
    val edad = 25
    val mensaje = if (edad >= 18) "Adulto" else "Menor"
    println(s"IF como expresión: $mensaje")
    
    // FOR como expresión (con yield)
    val cuadrados = for (i <- 1 to 5) yield i * i
    println(s"FOR como expresión: ${cuadrados.mkString(", ")}")
    
    // TRY como expresión
    val resultado = try {
      val numero = "123".toInt
      numero * 2
    } catch {
      case _: NumberFormatException => 0
    }
    println(s"TRY como expresión: $resultado")
    
    // MATCH como expresión
    val valor: Any = "texto"  // Declarar como Any para que el match tenga sentido
    val tipo = valor match {
      case s: String => "Es texto"
      case i: Int => "Es número"
      case _ => "Otro tipo"
    }
    println(s"MATCH como expresión: $tipo")
    
    // Bloques como expresiones
    val calculoComplejo = {
      val a = 10
      val b = 20
      val suma = a + b
      val multiplicacion = suma * 2
      multiplicacion  // Último valor se devuelve
    }
    println(s"Bloque como expresión: $calculoComplejo")
    
    println("\n🎯 RESUMEN: En Scala casi todo devuelve un valor")
    println("🎯 RESUMEN: Usa pattern matching en lugar de múltiples if-else")
    println("🎯 RESUMEN: Prefiere enfoques funcionales sobre break/continue")
  }
}

/*
 * NOTAS IMPORTANTES :
 * 
 * 1. EJECUTAR ESTE ARCHIVO:
 *    mvn exec:java -Dexec.mainClass="ProgramacionImperativa"
 * 
 * 2. DIFERENCIAS CON JAVA:
 *    - if, for, try, match son expresiones (devuelven valores)
 *    - No hay break/continue nativos en for
 *    - Pattern matching es mucho más potente que switch
 *    - Los rangos se crean con 'to' y 'until'
 * 
 * 3. CONCEPTOS CLAVE:
 *    - Expresión: devuelve un valor
 *    - Declaración: ejecuta una acción
 *    - Pattern matching: matching potente con guardas
 *    - Generadores en for: <- para iterar
 *    - Guardas: if dentro de for para filtrar
 * 
 * 4. BUENAS PRÁCTICAS:
 *    - Usa pattern matching en lugar de múltiples if-else
 *    - Prefiere for con yield sobre bucles mutables
 *    - Evita var cuando puedas
 *    - Usa filtros en for en lugar de continue
 * 
 * 5. PRÓXIMO PASO:
 *    - Ver 4-procedural.scala para funciones simples
 */