/**
 * 11-clases.scala
 * 
 * Programación Orientada a Objetos en Scala
 * 
 * Este archivo demuestra:
 * - Definición de clases y case classes
 * - Clase Persona con DNI, nombre, edad
 * - Validación de DNI español
 * - Stream processing con validaciones
 * - Filtros y transformaciones de datos
 * - Casos de uso complejos con POO + funcional
 */

import scala.util.{Try, Success, Failure}

object ClasesYProcesamiento {
  
  def main(args: Array[String]): Unit = {
    
    println("CLASES Y PROCESAMIENTO EN SCALA")
    println("=" * 32)
    
    // Definir clases básicas
    definicionClases()
    
    // Trabajar con personas
    trabajarConPersonas()
    
    // Validación de DNI
    validacionDNI()
    
    // Stream processing con validaciones
    streamProcessing()
    
    // Casos de uso complejos
    casosDeUsoComplejos()
  }
  
  /**
   * Definición de clases básicas
   */
  def definicionClases(): Unit = {
    println("DEFINICIÓN DE CLASES:")
    println("-" * 21)
    
    // Clase normal con constructor
    class PersonaSimple(val nombre: String, val edad: Int) {
      def saludar(): String = s"Hola, soy $nombre y tengo $edad años"
      
      def esMayorDeEdad: Boolean = edad >= 18
      
      override def toString: String = s"PersonaSimple($nombre, $edad)"
    }
    
    val persona1 = new PersonaSimple("Ana", 25)
    println(s"Persona simple: $persona1")
    println(s"Saludo: ${persona1.saludar()}")
    println(s"¿Es mayor de edad? ${persona1.esMayorDeEdad}")
    
    // Case class (más común en Scala)
    case class PersonaCase(nombre: String, edad: Int) {
      def saludar(): String = s"Hola, soy $nombre y tengo $edad años"
      def esMayorDeEdad: Boolean = edad >= 18
    }
    
    val persona2 = PersonaCase("Juan", 30)  // No necesita 'new'
    println(s"Case class: $persona2")
    println(s"Saludo: ${persona2.saludar()}")
    
    // Ventajas de case class
    val persona3 = persona2.copy(edad = 31)  // Crear copia modificada
    println(s"Copia modificada: $persona3")
    println(s"¿Son iguales? ${persona2 == persona3}")  // Comparación por valor
    
    // Pattern matching con case classes
    def analizarPersona(persona: PersonaCase): String = persona match {
      case PersonaCase("Juan", edad) => s"Es Juan de $edad años"
      case PersonaCase(nombre, edad) if edad < 18 => s"$nombre es menor de edad"
      case PersonaCase(nombre, edad) => s"$nombre es adulto de $edad años"
    }
    
    println(s"Análisis persona2: ${analizarPersona(persona2)}")
    println(s"Análisis persona3: ${analizarPersona(persona3)}")
    
    println()
  }
  
  /**
   * Clase Persona completa con DNI
   */
  case class Persona(
    dni: String,
    nombre: String,
    apellidos: String,
    edad: Int,
    email: Option[String] = None
  ) {
    
    def nombreCompleto: String = s"$nombre $apellidos"
    
    def esMayorDeEdad: Boolean = edad >= 18
    
    def esJubilado: Boolean = edad >= 65
    
    def validarDNI: Boolean = DNIValidator.validar(dni)
    
    def formatearContacto: String = {
      val emailStr = email.map(e => s" - $e").getOrElse("")
      s"$nombreCompleto (DNI: $dni)$emailStr"
    }
    
    // Validaciones
    def esValida: Either[List[String], Persona] = {
      val errores = scala.collection.mutable.ListBuffer[String]()
      
      if (nombre.trim.isEmpty) errores += "El nombre no puede estar vacío"
      if (apellidos.trim.isEmpty) errores += "Los apellidos no pueden estar vacíos"
      if (edad < 0 || edad > 150) errores += "La edad debe estar entre 0 y 150"
      if (!validarDNI) errores += "El DNI no es válido"
      
      email.foreach { e =>
        if (!e.contains("@") || !e.contains(".")) {
          errores += "El email no tiene formato válido"
        }
      }
      
      if (errores.isEmpty) Right(this)
      else Left(errores.toList)
    }
  }
  
  /**
   * Validador de DNI español
   */
  object DNIValidator {
    
    private val letrasControl = "TRWAGMYFPDXBNJZSQVHLCKE"
    
    def validar(dni: String): Boolean = {
      if (dni == null || dni.length != 9) return false
      
      try {
        val numero = dni.substring(0, 8).toInt
        val letraEsperada = letrasControl(numero % 23)
        val letraActual = dni.charAt(8).toUpper
        
        letraEsperada == letraActual
      } catch {
        case _: Exception => false
      }
    }
    
    def extraerNumero(dni: String): Option[Int] = {
      Try(dni.substring(0, 8).toInt).toOption
    }
    
    def extraerLetra(dni: String): Option[Char] = {
      Try(dni.charAt(8).toUpper).toOption
    }
    
    def calcularLetraControl(numero: Int): Char = {
      letrasControl(numero % 23)
    }
    
    def generarDNIAleatorio(): String = {
      val numero = scala.util.Random.nextInt(99999999)
      val numeroFormateado = f"$numero%08d"
      val letra = calcularLetraControl(numero)
      s"$numeroFormateado$letra"
    }
  }
  
  /**
   * Trabajar con personas
   */
  def trabajarConPersonas(): Unit = {
    println("TRABAJAR CON PERSONAS:")
    println("-" * 22)
    
    // Crear personas de ejemplo
    val personas = List(
      Persona("12345678Z", "Ana", "García López", 25, Some("ana@email.com")),
      Persona("23456789Y", "Juan", "Martín Pérez", 17, None),
      Persona("34567890X", "María", "López Rodríguez", 35, Some("maria@email.com")),
      Persona("45678901W", "Pedro", "Sánchez Jiménez", 28, Some("pedro@email.com")),
      Persona("56789012V", "Carmen", "Ruiz Fernández", 42, None),
      Persona("67890123U", "Luis", "González Moreno", 19, Some("luis@email.com")),
      Persona("78901234T", "Sofía", "Díaz Herrera", 31, Some("sofia@email.com"))
    )
    
    println(s"Total personas: ${personas.length}")
    
    // Información básica
    personas.take(3).foreach { persona =>
      println(s"  ${persona.formatearContacto}")
      println(s"    Mayor de edad: ${persona.esMayorDeEdad}")
      println(s"    DNI válido: ${persona.validarDNI}")
      println()
    }
    
    // Estadísticas básicas
    val mayoresDeEdad = personas.count(_.esMayorDeEdad)
    val conEmail = personas.count(_.email.isDefined)
    val edadPromedio = personas.map(_.edad).sum.toDouble / personas.length
    
    println(s"Estadísticas:")
    println(s"  Mayores de edad: $mayoresDeEdad/${personas.length}")
    println(s"  Con email: $conEmail/${personas.length}")
    println(f"  Edad promedio: $edadPromedio%.1f años")
    
    println()
  }
  
  /**
   * Validación de DNI
   */
  def validacionDNI(): Unit = {
    println("VALIDACIÓN DE DNI:")
    println("-" * 18)
    
    val dnisParaProbar = List(
      "12345678Z",  // Válido
      "12345678A",  // Inválido (letra incorrecta)
      "123456789",  // Inválido (sin letra)
      "1234567Z",   // Inválido (muy corto)
      "ABCDEFGHI",  // Inválido (no numérico)
      "87654321X",  // Válido
      "00000000T"   // Válido
    )
    
    println("Validando DNIs:")
    dnisParaProbar.foreach { dni =>
      val esValido = DNIValidator.validar(dni)
      val numero = DNIValidator.extraerNumero(dni)
      val letra = DNIValidator.extraerLetra(dni)
      val letraEsperada = numero.map(DNIValidator.calcularLetraControl)
      
      println(f"$dni%12s: ${if (esValido) "✅ VÁLIDO" else "❌ INVÁLIDO"}")
      numero.foreach(n => println(s"            Número: $n"))
      letra.foreach(l => println(s"            Letra: $l"))
      letraEsperada.foreach(le => println(s"            Esperada: $le"))
      println()
    }
    
    // Generar DNIs aleatorios válidos
    println("DNIs aleatorios válidos:")
    (1 to 5).foreach { _ =>
      val dniAleatorio = DNIValidator.generarDNIAleatorio()
      println(s"  $dniAleatorio (válido: ${DNIValidator.validar(dniAleatorio)})")
    }
    
    println()
  }
  
  /**
   * Stream processing con validaciones
   */
  def streamProcessing(): Unit = {
    println("STREAM PROCESSING CON VALIDACIONES:")
    println("-" * 35)
    
    // Datos de entrada con algunos errores
    val datosEntrada = List(
      ("12345678Z", "  Ana  ", "García López", 25, Some("ana@email.com")),
      ("23456789A", "Juan", "Martín Pérez", 17, None),  // DNI inválido
      ("34567890X", "", "López Rodríguez", 35, Some("maria@email.com")),  // Nombre vacío
      ("45678901W", "Pedro", "Sánchez Jiménez", -5, Some("pedro@email.com")),  // Edad inválida
      ("56789012V", "Carmen", "Ruiz Fernández", 42, None),
      ("67890123U", "Luis  ", "  González Moreno  ", 19, Some("email-inválido")),  // Email inválido
      ("78901234T", "Sofía", "Díaz Herrera", 31, Some("sofia@email.com")),
      ("invalidDNI", "Rosa", "Martín Torres", 28, Some("rosa@email.com"))  // DNI inválido
    )
    
    println(s"Datos de entrada: ${datosEntrada.length} registros")
    
    // PASO 1: Convertir tuplas a personas (con limpieza de datos)
    val personasCreadas = datosEntrada.map { case (dni, nombre, apellidos, edad, email) =>
      val nombreLimpio = nombre.trim
      val apellidosLimpios = apellidos.trim  
      val emailLimpio = email.map(_.trim).filter(_.nonEmpty)
      
      Persona(dni, nombreLimpio, apellidosLimpios, edad, emailLimpio)
    }
    
    println(s"Después de limpieza: ${personasCreadas.length} personas")
    
    // PASO 2: Validar personas y separar válidas de inválidas
    val (personasValidas, personasInvalidas) = personasCreadas
      .map(persona => (persona, persona.esValida))
      .partition(_._2.isRight)
    
    val validasExtraidas = personasValidas.map(_._1)
    val invalidasConErrores = personasInvalidas.map { case (persona, Left(errores)) => (persona, errores) }
    
    println(s"Personas válidas: ${validasExtraidas.length}")
    println(s"Personas inválidas: ${invalidasConErrores.length}")
    
    // Mostrar errores
    if (invalidasConErrores.nonEmpty) {
      println("\nErrores encontrados:")
      invalidasConErrores.foreach { case (persona, errores) =>
        println(s"  ${persona.nombreCompleto} (${persona.dni}):")
        errores.foreach(error => println(s"    - $error"))
      }
    }
    
    // PASO 3: Procesar solo personas válidas
    println(s"\n=== PROCESAMIENTO DE PERSONAS VÁLIDAS ===")
    
    val pipeline = validasExtraidas
      .filter(_.esMayorDeEdad)           // Solo mayores de edad
      .filter(_.validarDNI)              // Solo DNIs válidos
      .map { persona =>                  // Limpiar nombres (quitar espacios extra)
        val nombreLimpio = persona.nombre.trim.split("\\s+").map(_.capitalize).mkString(" ")
        val apellidosLimpios = persona.apellidos.trim.split("\\s+").map(_.capitalize).mkString(" ")
        persona.copy(nombre = nombreLimpio, apellidos = apellidosLimpios)
      }
      .filter(_.edad >= 18)              // Filtro adicional de edad
      .sortBy(_.edad)                    // Ordenar por edad
    
    println(s"Después del pipeline: ${pipeline.length} personas")
    
    // PASO 4: Generar reporte
    println("\n=== REPORTE FINAL ===")
    pipeline.foreach { persona =>
      val estado = if (persona.esJubilado) "Jubilado" 
                  else if (persona.esMayorDeEdad) "Adulto" 
                  else "Menor"
      
      val contacto = persona.email.map(e => s" [$e]").getOrElse(" [Sin email]")
      
      println(f"${persona.nombreCompleto}%-25s (${persona.edad}%2d años) - $estado%8s - DNI: ${persona.dni}$contacto")
    }
    
    // PASO 5: Estadísticas finales
    println(s"\n=== ESTADÍSTICAS FINALES ===")
    val conEmail = pipeline.count(_.email.isDefined)
    val sinEmail = pipeline.length - conEmail
    val edadPromedio = if (pipeline.nonEmpty) pipeline.map(_.edad).sum.toDouble / pipeline.length else 0
    val jubilados = pipeline.count(_.esJubilado)
    
    println(f"Total procesadas: ${pipeline.length}")
    println(f"Con email: $conEmail (${"%.1f".format(conEmail * 100.0 / pipeline.length)}%%)")
    println(f"Sin email: $sinEmail (${"%.1f".format(sinEmail * 100.0 / pipeline.length)}%%)")
    println(f"Edad promedio: $edadPromedio%.1f años")
    println(f"Jubilados: $jubilados")
    
    // PASO 6: Agrupar por rango de edad
    val rangosPorEdad = pipeline.groupBy { persona =>
      persona.edad match {
        case edad if edad < 25 => "18-24"
        case edad if edad < 35 => "25-34"
        case edad if edad < 50 => "35-49"
        case edad if edad < 65 => "50-64"
        case _ => "65+"
      }
    }
    
    println(s"\n=== DISTRIBUCIÓN POR EDADES ===")
    rangosPorEdad.toList.sortBy(_._1).foreach { case (rango, personas) =>
      println(f"$rango%6s: ${personas.length}%2d personas")
    }
    
    println()
  }
  
  /**
   * Casos de uso complejos
   */
  def casosDeUsoComplejos(): Unit = {
    println("CASOS DE USO COMPLEJOS:")
    println("-" * 24)
    
    // Simular base de datos de empleados
    case class Empleado(
      persona: Persona,
      departamento: String,
      cargo: String,
      salario: Double,
      fechaIngreso: String
    ) {
      def antiguedad: Int = {
        val anoIngreso = fechaIngreso.substring(0, 4).toInt
        2024 - anoIngreso
      }
      
      def salarioAnual: Double = salario * 12
      
      def esDirectivo: Boolean = cargo.toLowerCase.contains("director") || cargo.toLowerCase.contains("gerente")
    }
    
    val empleados = List(
      Empleado(Persona("12345678Z", "Ana", "García López", 35, Some("ana@empresa.com")), "IT", "Desarrolladora Senior", 3500, "2019-03-15"),
      Empleado(Persona("23456789Y", "Juan", "Martín Pérez", 42, Some("juan@empresa.com")), "IT", "Director Técnico", 5500, "2015-01-10"),
      Empleado(Persona("34567890X", "María", "López Rodríguez", 28, Some("maria@empresa.com")), "RRHH", "Especialista RRHH", 2800, "2021-06-01"),
      Empleado(Persona("45678901W", "Pedro", "Sánchez Jiménez", 39, Some("pedro@empresa.com")), "Ventas", "Gerente Ventas", 4200, "2017-09-20"),
      Empleado(Persona("56789012V", "Carmen", "Ruiz Fernández", 31, Some("carmen@empresa.com")), "Marketing", "Marketing Manager", 3200, "2020-02-14"),
      Empleado(Persona("67890123U", "Luis", "González Moreno", 26, Some("luis@empresa.com")), "IT", "Desarrollador Junior", 2200, "2022-11-05"),
      Empleado(Persona("78901234T", "Sofía", "Díaz Herrera", 44, Some("sofia@empresa.com")), "Finanzas", "Directora Financiera", 6000, "2016-04-12")
    )
    
    println(s"Total empleados: ${empleados.length}")
    
    // ANÁLISIS 1: Salario por departamento
    println("\n=== ANÁLISIS SALARIAL POR DEPARTAMENTO ===")
    val salariosPorDpto = empleados
      .groupBy(_.departamento)
      .map { case (dpto, empleadosDpto) =>
        val salarios = empleadosDpto.map(_.salario)
        val promedio = salarios.sum / salarios.length
        val maximo = salarios.max
        val minimo = salarios.min
        val total = salarios.sum
        
        (dpto, empleadosDpto.length, promedio, maximo, minimo, total)
      }
      .toList
      .sortBy(-_._6)  // Ordenar por total
    
    salariosPorDpto.foreach { case (dpto, num, promedio, max, min, total) =>
      println(f"$dpto%10s: $num%2d empleados | Promedio: €$promedio%7.0f | Rango: €$min%7.0f - €$max%7.0f | Total: €$total%8.0f")
    }
    
    // ANÁLISIS 2: Top empleados
    println("\n=== TOP EMPLEADOS ===")
    val topEmpleados = empleados
      .sortBy(-_.salario)
      .take(3)
    
    topEmpleados.zipWithIndex.foreach { case (empleado, index) =>
      println(f"${index + 1}. ${empleado.persona.nombreCompleto}%-20s | ${empleado.cargo}%-20s | €${empleado.salario}%7.0f | ${empleado.departamento}")
    }
    
    // ANÁLISIS 3: Empleados con validaciones complejas
    println("\n=== VALIDACIONES COMPLEJAS ===")
    
    case class ProblemaEmpleado(empleado: Empleado, problemas: List[String])
    
    val empleadosConProblemas = empleados.map { empleado =>
      val problemas = scala.collection.mutable.ListBuffer[String]()
      
      // Validar persona
      empleado.persona.esValida match {
        case Left(errores) => problemas ++= errores
        case Right(_) => // OK
      }
      
      // Validaciones de negocio
      if (empleado.salario < 1000) problemas += "Salario por debajo del mínimo legal"
      if (empleado.salario > 10000) problemas += "Salario excesivamente alto"
      if (empleado.persona.edad < 18) problemas += "Empleado menor de edad"
      if (empleado.persona.edad > 67) problemas += "Empleado por encima de edad de jubilación"
      if (empleado.antiguedad < 0) problemas += "Fecha de ingreso futura"
      if (empleado.cargo.trim.isEmpty) problemas += "Cargo no especificado"
      if (empleado.departamento.trim.isEmpty) problemas += "Departamento no especificado"
      
      ProblemaEmpleado(empleado, problemas.toList)
    }
    
    val conProblemas = empleadosConProblemas.filter(_.problemas.nonEmpty)
    val sinProblemas = empleadosConProblemas.filter(_.problemas.isEmpty)
    
    println(s"Empleados sin problemas: ${sinProblemas.length}")
    println(s"Empleados con problemas: ${conProblemas.length}")
    
    if (conProblemas.nonEmpty) {
      println("\nProblemas detectados:")
      conProblemas.foreach { caso =>
        println(s"  ${caso.empleado.persona.nombreCompleto}:")
        caso.problemas.foreach(p => println(s"    - $p"))
      }
    }
    
    // ANÁLISIS 4: Recomendaciones automáticas
    println("\n=== RECOMENDACIONES AUTOMÁTICAS ===")
    
    val recomendaciones = empleados.flatMap { empleado =>
      val recos = scala.collection.mutable.ListBuffer[String]()
      
      // Recomendaciones salariales
      val promedioEquipo = empleados.filter(_.departamento == empleado.departamento).map(_.salario).sum / 
                          empleados.count(_.departamento == empleado.departamento)
      
      if (empleado.salario < promedioEquipo * 0.8) {
        recos += s"${empleado.persona.nombreCompleto}: Considerar aumento salarial (${empleado.salario.toInt} vs promedio ${promedioEquipo.toInt})"
      }
      
      // Recomendaciones de promoción
      if (empleado.antiguedad >= 5 && !empleado.esDirectivo && empleado.salario > 3000) {
        recos += s"${empleado.persona.nombreCompleto}: Candidato para promoción (${empleado.antiguedad} años de antigüedad)"
      }
      
      // Recomendaciones de formación
      if (empleado.persona.edad < 30 && empleado.departamento == "IT") {
        recos += s"${empleado.persona.nombreCompleto}: Candidato para formación avanzada en tecnología"
      }
      
      recos.toList
    }
    
    if (recomendaciones.nonEmpty) {
      recomendaciones.foreach(println)
    } else {
      println("No se generaron recomendaciones automáticas")
    }
    
    println("\n🎯 RESUMEN DEL PROCESAMIENTO:")
    println("  ✅ Validación completa de datos de entrada")
    println("  ✅ Limpieza y normalización automática")
    println("  ✅ Filtros en pipeline para datos de calidad")
    println("  ✅ Análisis estadístico automatizado")
    println("  ✅ Detección de problemas y anomalías")
    println("  ✅ Generación de recomendaciones inteligentes")
    
    println("\n🎯 APLICACIÓN EN SPARK:")
    println("  🔗 Estos mismos patrones se usan en Spark")
    println("  🔗 DataFrame operations para filtrar y transformar")
    println("  🔗 SQL queries para análisis complejos") 
    println("  🔗 Machine Learning para recomendaciones")
    println("  🔗 Streaming para procesamiento en tiempo real")
  }
}

/*
 * NOTAS IMPORTANTES :
 * 
 * 1. EJECUTAR ESTE ARCHIVO:
 *    mvn exec:java -Dexec.mainClass="ClasesYProcesamiento"
 * 
 * 2. CONCEPTOS POO EN SCALA:
 *    - class: definición de clase tradicional
 *    - case class: clase inmutable con funcionalidades extra
 *    - object: singleton, equivale a métodos estáticos
 *    - trait: similar a interface pero con implementación
 * 
 * 3. CASE CLASS VENTAJAS:
 *    - Constructor automático
 *    - Comparación por valor (equals)
 *    - Método copy() para modificar
 *    - toString() automático
 *    - Pattern matching support
 * 
 * 4. VALIDACIÓN DE DATOS:
 *    - Either[Error, Success] para manejo de errores
 *    - Option para valores opcionales
 *    - Try para operaciones que pueden fallar
 *    - Validaciones de negocio específicas
 * 
 * 5. STREAM PROCESSING PATTERNS:
 *    - map: transformar datos
 *    - filter: filtrar datos válidos
 *    - partition: separar válidos de inválidos
 *    - groupBy: agrupar para análisis
 *    - sortBy: ordenar resultados
 * 
 * 6. PREPARACIÓN PARA SPARK:
 *    - Estos patrones son idénticos en Spark
 *    - DataFrame/Dataset operations
 *    - Validación de esquemas
 *    - Transformaciones complejas
 *    - Análisis de calidad de datos
 * 
 * 7. SIGUIENTE PASO:
 *    - ¡Ya estás listo para Apache Spark!
 *    - Todos estos conceptos se aplican directamente
 *    - Scala + Spark = Big Data processing power
 */