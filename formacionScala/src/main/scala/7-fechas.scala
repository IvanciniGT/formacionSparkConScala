/**
 * 7-fechas.scala
 * 
 * Manejo de fechas y tiempo en Scala
 * 
 * Este archivo demuestra:
 * - API de fechas de Java 8+ (java.time)
 * - Creación y manipulación de fechas
 * - Formateo y parsing de fechas
 * - Cálculos con fechas y duraciones
 * - Zonas horarias
 * - Casos de uso comunes en aplicaciones
 */

import java.time._
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import scala.util.{Try, Success, Failure}

object ManejoFechas {
  
  def main(args: Array[String]): Unit = {
    
    println("MANEJO DE FECHAS Y TIEMPO EN SCALA")
    println("=" * 35)
    
    // Creación de fechas
    creacionFechas()
    
    // Formateo de fechas
    formateoFechas()
    
    // Parsing de fechas
    parsingFechas()
    
    // Operaciones con fechas
    operacionesFechas()
    
    // Duraciones y períodos
    duracionesYPeriodos()
    
    // Zonas horarias
    zonasHorarias()
    
    // Casos de uso prácticos
    casosDeUsoPracticos()
  }
  
  /**
   * Creación de fechas y tiempo
   */
  def creacionFechas(): Unit = {
    println("CREACIÓN DE FECHAS:")
    println("-" * 19)
    
    // Fecha y hora actual
    val ahora = LocalDateTime.now()
    val fechaActual = LocalDate.now()
    val horaActual = LocalTime.now()
    val instanteActual = Instant.now()
    
    println(s"Fecha y hora actual: $ahora")
    println(s"Solo fecha: $fechaActual") 
    println(s"Solo hora: $horaActual")
    println(s"Instante actual (UTC): $instanteActual")
    
    // Crear fechas específicas
    val fechaNacimiento = LocalDate.of(1990, Month.MARCH, 15)
    val horaReunion = LocalTime.of(14, 30, 0)
    val fechaHoraEvento = LocalDateTime.of(2024, 12, 25, 18, 30)
    
    println(s"Fecha nacimiento: $fechaNacimiento")
    println(s"Hora reunión: $horaReunion")
    println(s"Evento navideño: $fechaHoraEvento")
    
    // Crear desde strings (ISO format)
    val fechaDesdeString = LocalDate.parse("2024-01-15")
    val fechaHoraDesdeString = LocalDateTime.parse("2024-01-15T10:30:00")
    
    println(s"Fecha desde string: $fechaDesdeString")
    println(s"Fecha-hora desde string: $fechaHoraDesdeString")
    
    // Fechas relativas
    val primerDiaAno = LocalDate.now().withDayOfYear(1)
    val primerDiaMes = LocalDate.now().withDayOfMonth(1)
    val proximoLunes = LocalDate.now().`with`(java.time.temporal.TemporalAdjusters.next(java.time.DayOfWeek.MONDAY))
    
    println(s"Primer día del año: $primerDiaAno")
    println(s"Primer día del mes: $primerDiaMes")
    println(s"Próximo lunes: $proximoLunes")
    
    println()
  }
  
  /**
   * Formateo de fechas a strings
   */
  def formateoFechas(): Unit = {
    println("FORMATEO DE FECHAS:")
    println("-" * 19)
    
    val fecha = LocalDateTime.of(2024, 10, 24, 15, 30, 45)
    
    // Formatos predefinidos
    println("Formatos predefinidos:")
    println(s"ISO Local Date: ${fecha.toLocalDate}")
    println(s"ISO Local Time: ${fecha.toLocalTime}")
    println(s"ISO Local DateTime: $fecha")
    
    // Formatos personalizados
    println("\nFormatos personalizados:")
    
    val formatoEspanol = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    val formatoLargo = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy")
    val formatoCorto = DateTimeFormatter.ofPattern("dd-MM-yy")
    val formatoHora12 = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")
    
    println(s"Español: ${fecha.format(formatoEspanol)}")
    println(s"Formato largo: ${fecha.format(formatoLargo)}")
    println(s"Formato corto: ${fecha.toLocalDate.format(formatoCorto)}")
    println(s"12 horas: ${fecha.format(formatoHora12)}")
    
    // Formatos para diferentes componentes
    val soloAno = DateTimeFormatter.ofPattern("yyyy")
    val soloMes = DateTimeFormatter.ofPattern("MMMM")
    val soloDia = DateTimeFormatter.ofPattern("EEEE")
    
    println(s"Solo año: ${fecha.format(soloAno)}")
    println(s"Solo mes: ${fecha.format(soloMes)}")
    println(s"Solo día: ${fecha.format(soloDia)}")
    
    // Formateo en diferentes idiomas
    import java.util.Locale
    val formatoIngles = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH)
    val formatoFrances = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH)
    
    println(s"Inglés: ${fecha.format(formatoIngles)}")
    println(s"Francés: ${fecha.format(formatoFrances)}")
    
    println()
  }
  
  /**
   * Parsing (análisis) de strings a fechas
   */
  def parsingFechas(): Unit = {
    println("PARSING DE FECHAS:")
    println("-" * 18)
    
    // Parsing con formatos estándar
    val fechasString = List(
      "2024-10-24",
      "2024-10-24T15:30:00",
      "15:30:45"
    )
    
    println("Parsing estándar (ISO):")
    fechasString.foreach { fechaStr =>
      try {
        if (fechaStr.contains("T")) {
          val fechaHora = LocalDateTime.parse(fechaStr)
          println(s"'$fechaStr' -> $fechaHora")
        } else if (fechaStr.contains(":")) {
          val hora = LocalTime.parse(fechaStr)
          println(s"'$fechaStr' -> $hora")
        } else {
          val fecha = LocalDate.parse(fechaStr)
          println(s"'$fechaStr' -> $fecha")
        }
      } catch {
        case e: Exception => println(s"Error parsing '$fechaStr': ${e.getMessage}")
      }
    }
    
    // Parsing con formatos personalizados
    println("\nParsing con formatos personalizados:")
    
    val formatoEspanol = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formatoAmericano = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val formatoHora12 = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")
    
    val fechasPersonalizadas = List(
      ("24/10/2024", formatoEspanol),
      ("10/24/2024", formatoAmericano),
      ("24/10/2024 03:30 PM", formatoHora12)
    )
    
    fechasPersonalizadas.foreach { case (fechaStr, formato) =>
      try {
        if (fechaStr.contains(":")) {
          val fechaHora = LocalDateTime.parse(fechaStr, formato)
          println(s"'$fechaStr' -> $fechaHora")
        } else {
          val fecha = LocalDate.parse(fechaStr, formato)
          println(s"'$fechaStr' -> $fecha")
        }
      } catch {
        case e: Exception => println(s"Error parsing '$fechaStr': ${e.getMessage}")
      }
    }
    
    // Parsing seguro con Try
    def parseFechaSegura(fechaStr: String, formato: DateTimeFormatter): Try[LocalDate] = {
      Try(LocalDate.parse(fechaStr, formato))
    }
    
    println("\nParsing seguro:")
    val fechasPrueba = List("24/10/2024", "fecha-inválida", "31/02/2024")
    
    fechasPrueba.foreach { fechaStr =>
      parseFechaSegura(fechaStr, formatoEspanol) match {
        case Success(fecha) => println(s"✅ '$fechaStr' -> $fecha")
        case Failure(error) => println(s"❌ '$fechaStr' -> Error: ${error.getMessage}")
      }
    }
    
    println()
  }
  
  /**
   * Operaciones y cálculos con fechas
   */
  def operacionesFechas(): Unit = {
    println("OPERACIONES CON FECHAS:")
    println("-" * 23)
    
    val fechaBase = LocalDate.of(2024, 10, 24)
    val fechaHoraBase = LocalDateTime.of(2024, 10, 24, 15, 30, 0)
    
    println(s"Fecha base: $fechaBase")
    println(s"Fecha-hora base: $fechaHoraBase")
    
    // Suma y resta de tiempo
    val mañana = fechaBase.plusDays(1)
    val semanaProxima = fechaBase.plusWeeks(1)
    val mesProximo = fechaBase.plusMonths(1)
    val anoProximo = fechaBase.plusYears(1)
    
    println(s"Mañana: $mañana")
    println(s"Semana próxima: $semanaProxima")
    println(s"Mes próximo: $mesProximo")
    println(s"Año próximo: $anoProximo")
    
    // Resta de tiempo
    val ayer = fechaBase.minusDays(1)
    val semnaPasada = fechaBase.minusWeeks(1)
    val mesPasado = fechaBase.minusMonths(1)
    val anoPasado = fechaBase.minusYears(1)
    
    println(s"Ayer: $ayer")
    println(s"Semana pasada: $semnaPasada")
    println(s"Mes pasado: $mesPasado")
    println(s"Año pasado: $anoPasado")
    
    // Operaciones con horas
    val enUnaHora = fechaHoraBase.plusHours(1)
    val en30Minutos = fechaHoraBase.plusMinutes(30)
    val en45Segundos = fechaHoraBase.plusSeconds(45)
    
    println(s"En una hora: $enUnaHora")
    println(s"En 30 minutos: $en30Minutos")
    println(s"En 45 segundos: $en45Segundos")
    
    // Comparaciones
    val fecha1 = LocalDate.of(2024, 10, 24)
    val fecha2 = LocalDate.of(2024, 10, 25)
    
    println(s"\nComparaciones:")
    println(s"$fecha1 es antes que $fecha2: ${fecha1.isBefore(fecha2)}")
    println(s"$fecha1 es después que $fecha2: ${fecha1.isAfter(fecha2)}")
    println(s"$fecha1 es igual a $fecha2: ${fecha1.isEqual(fecha2)}")
    
    // Calcular diferencias
    val diferenciaDias = ChronoUnit.DAYS.between(fecha1, fecha2)
    val nacimiento = LocalDate.of(1990, 3, 15)
    val edadAnos = ChronoUnit.YEARS.between(nacimiento, LocalDate.now())
    val edadDias = ChronoUnit.DAYS.between(nacimiento, LocalDate.now())
    
    println(s"Diferencia en días: $diferenciaDias")
    println(s"Edad en años: $edadAnos")
    println(s"Edad en días: $edadDias")
    
    println()
  }
  
  /**
   * Duraciones y períodos
   */
  def duracionesYPeriodos(): Unit = {
    println("DURACIONES Y PERÍODOS:")
    println("-" * 22)
    
    // Period - para fechas (años, meses, días)
    val periodo = Period.of(2, 3, 10)  // 2 años, 3 meses, 10 días
    println(s"Período: $periodo")
    
    val fechaInicio = LocalDate.of(2024, 1, 1)
    val fechaConPeriodo = fechaInicio.plus(periodo)
    println(s"$fechaInicio + $periodo = $fechaConPeriodo")
    
    // Duration - para tiempo (horas, minutos, segundos)
    val duracion = Duration.ofHours(2).plusMinutes(30).plusSeconds(45)
    println(s"Duración: $duracion")
    
    val horaInicio = LocalTime.of(10, 0, 0)
    val horaConDuracion = horaInicio.plus(duracion)
    println(s"$horaInicio + $duracion = $horaConDuracion")
    
    // Calcular duraciones entre momentos
    val inicio = LocalDateTime.of(2024, 10, 24, 9, 0, 0)
    val fin = LocalDateTime.of(2024, 10, 24, 17, 30, 0)
    val jornada = Duration.between(inicio, fin)
    
    println(s"Jornada laboral: $inicio a $fin")
    println(s"Duración: $jornada")
    println(s"Horas trabajadas: ${jornada.toHours()}")
    println(s"Minutos trabajados: ${jornada.toMinutes()}")
    
    // Operaciones con duraciones
    val descanso = Duration.ofMinutes(30)
    val jornadaReal = jornada.minus(descanso)
    
    println(s"Descanso: $descanso")
    println(s"Jornada real: $jornadaReal")
    println(s"Horas reales: ${jornadaReal.toHours()} horas y ${jornadaReal.toMinutesPart()} minutos")
    
    // Formatear duraciones
    def formatearDuracion(duration: Duration): String = {
      val horas = duration.toHours()
      val minutos = duration.toMinutesPart()
      val segundos = duration.toSecondsPart()
      f"${horas}h ${minutos}m ${segundos}s"
    }
    
    println(s"Duración formateada: ${formatearDuracion(jornada)}")
    
    println()
  }
  
  /**
   * Trabajar con zonas horarias
   */
  def zonasHorarias(): Unit = {
    println("ZONAS HORARIAS:")
    println("-" * 15)
    
    // Obtener zona horaria del sistema
    val zonaLocal = ZoneId.systemDefault()
    println(s"Zona horaria local: $zonaLocal")
    
    // Crear fechas con zona horaria
    val fechaHoraMadrid = ZonedDateTime.of(2024, 10, 24, 15, 30, 0, 0, ZoneId.of("Europe/Madrid"))
    val fechaHoraNY = ZonedDateTime.of(2024, 10, 24, 15, 30, 0, 0, ZoneId.of("America/New_York"))
    val fechaHoraTokio = ZonedDateTime.of(2024, 10, 24, 15, 30, 0, 0, ZoneId.of("Asia/Tokyo"))
    
    println(s"Madrid: $fechaHoraMadrid")
    println(s"Nueva York: $fechaHoraNY")
    println(s"Tokio: $fechaHoraTokio")
    
    // Convertir entre zonas horarias
    val mismoMomentoEnNY = fechaHoraMadrid.withZoneSameInstant(ZoneId.of("America/New_York"))
    val mismoMomentoEnTokio = fechaHoraMadrid.withZoneSameInstant(ZoneId.of("Asia/Tokyo"))
    
    println(s"\nConversiones desde Madrid 15:30:")
    println(s"En Nueva York: $mismoMomentoEnNY")
    println(s"En Tokio: $mismoMomentoEnTokio")
    
    // Trabajar con UTC
    val instanteUTC = Instant.now()
    val fechaUTC = instanteUTC.atZone(ZoneOffset.UTC)
    val fechaLocal = instanteUTC.atZone(zonaLocal)
    
    println(s"\nMomento actual:")
    println(s"UTC: $fechaUTC")
    println(s"Local: $fechaLocal")
    
    // Listar zonas horarias populares
    val zonasPopulares = List(
      "Europe/Madrid", "Europe/London", "America/New_York",
      "America/Los_Angeles", "Asia/Tokyo", "Asia/Shanghai"
    )
    
    println(s"\nHora actual en diferentes zonas:")
    val ahoraUTC = Instant.now()
    
    zonasPopulares.foreach { zona =>
      val fechaZona = ahoraUTC.atZone(ZoneId.of(zona))
      val formatoHora = DateTimeFormatter.ofPattern("HH:mm")
      println(s"$zona: ${fechaZona.format(formatoHora)}")
    }
    
    println()
  }
  
  /**
   * Casos de uso prácticos
   */
  def casosDeUsoPracticos(): Unit = {
    println("CASOS DE USO PRÁCTICOS:")
    println("-" * 24)
    
    // 1. Calcular edad exacta
    def calcularEdad(fechaNacimiento: LocalDate): String = {
      val hoy = LocalDate.now()
      val periodo = Period.between(fechaNacimiento, hoy)
      s"${periodo.getYears()} años, ${periodo.getMonths()} meses, ${periodo.getDays()} días"
    }
    
    val nacimiento = LocalDate.of(1990, 3, 15)
    println(s"Edad de alguien nacido el $nacimiento: ${calcularEdad(nacimiento)}")
    
    // 2. Verificar si es fin de semana
    def esFinDeSemana(fecha: LocalDate): Boolean = {
      val diaSemana = fecha.getDayOfWeek
      diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY
    }
    
    val hoy = LocalDate.now()
    println(s"¿Hoy ($hoy) es fin de semana? ${esFinDeSemana(hoy)}")
    
    // 3. Calcular días laborables entre dos fechas
    def diasLaborables(inicio: LocalDate, fin: LocalDate): Long = {
      inicio.datesUntil(fin.plusDays(1))
        .filter(fecha => !esFinDeSemana(fecha))
        .count()
    }
    
    val inicioMes = LocalDate.now().withDayOfMonth(1)
    val finMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
    val laborables = diasLaborables(inicioMes, finMes)
    
    println(s"Días laborables este mes: $laborables")
    
    // 4. Formatear fecha para diferentes audiencias
    def formatearFechaParaUsuario(fecha: LocalDateTime, formato: String): String = {
      formato match {
        case "formal" => fecha.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy 'a las' HH:mm"))
        case "corto" => fecha.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"))
        case "iso" => fecha.toString
        case "relativo" => {
          val ahora = LocalDateTime.now()
          val diferencia = ChronoUnit.HOURS.between(fecha, ahora)
          if (diferencia == 0) "Ahora mismo"
          else if (diferencia < 24) s"Hace $diferencia horas"
          else s"Hace ${diferencia / 24} días"
        }
        case _ => fecha.toString
      }
    }
    
    val fecha = LocalDateTime.now().minusHours(3)
    println(s"\nFormatos de fecha:")
    println(s"Formal: ${formatearFechaParaUsuario(fecha, "formal")}")
    println(s"Corto: ${formatearFechaParaUsuario(fecha, "corto")}")
    println(s"ISO: ${formatearFechaParaUsuario(fecha, "iso")}")
    println(s"Relativo: ${formatearFechaParaUsuario(fecha, "relativo")}")
    
    // 5. Calcular tiempo transcurrido en reunión
    case class Reunion(inicio: LocalDateTime, fin: Option[LocalDateTime] = None) {
      def duracion: Duration = {
        val finReal = fin.getOrElse(LocalDateTime.now())
        Duration.between(inicio, finReal)
      }
      
      def estaActiva: Boolean = fin.isEmpty
      
      def resumen: String = {
        val dur = duracion
        val estado = if (estaActiva) "En curso" else "Finalizada"
        val tiempo = f"${dur.toHours()}h ${dur.toMinutesPart()}m"
        s"$estado - Duración: $tiempo"
      }
    }
    
    val reunion1 = Reunion(LocalDateTime.now().minusHours(2))  // Empezó hace 2 horas
    val reunion2 = Reunion(
      LocalDateTime.now().minusHours(3),
      Some(LocalDateTime.now().minusHours(1))  // Terminó hace 1 hora
    )
    
    println(s"\nReuniones:")
    println(s"Reunión 1: ${reunion1.resumen}")
    println(s"Reunión 2: ${reunion2.resumen}")
    
    // 6. Validar rango de fechas para reservas
    def validarReserva(inicio: LocalDateTime, fin: LocalDateTime): Either[String, String] = {
      val ahora = LocalDateTime.now()
      
      if (inicio.isBefore(ahora)) {
        Left("La fecha de inicio no puede ser en el pasado")
      } else if (fin.isBefore(inicio)) {
        Left("La fecha de fin no puede ser anterior al inicio")
      } else if (Duration.between(inicio, fin).toMinutes < 30) {
        Left("La reserva debe ser de al menos 30 minutos")
      } else if (Duration.between(inicio, fin).toHours > 8) {
        Left("La reserva no puede ser mayor a 8 horas")
      } else {
        val duracion = Duration.between(inicio, fin)
        Right(s"Reserva válida de ${duracion.toHours()}h ${duracion.toMinutesPart()}m")
      }
    }
    
    val reserva1 = validarReserva(
      LocalDateTime.now().plusHours(2),
      LocalDateTime.now().plusHours(4)
    )
    
    val reserva2 = validarReserva(
      LocalDateTime.now().minusHours(1),
      LocalDateTime.now().plusHours(1)
    )
    
    println(s"\nValidación reservas:")
    println(s"Reserva 1: $reserva1")
    println(s"Reserva 2: $reserva2")
    
    println("\n🎯 RESUMEN: java.time es la API moderna para fechas")
    println("🎯 RESUMEN: Usa LocalDate/LocalDateTime para fechas locales")
    println("🎯 RESUMEN: Usa ZonedDateTime para fechas con zona horaria")
    println("🎯 RESUMEN: Period para diferencias de fechas, Duration para tiempo")
  }
}

/*
 * NOTAS IMPORTANTES :
 * 
 * 1. EJECUTAR ESTE ARCHIVO:
 *    mvn exec:java -Dexec.mainClass="ManejoFechas"
 * 
 * 2. API DE FECHAS JAVA 8+:
 *    - LocalDate: solo fecha (sin hora)
 *    - LocalTime: solo hora (sin fecha)
 *    - LocalDateTime: fecha y hora (sin zona horaria)
 *    - ZonedDateTime: fecha, hora y zona horaria
 *    - Instant: momento específico en UTC
 * 
 * 3. IMMUTABILIDAD:
 *    - Todas las clases de fecha son inmutables
 *    - Los métodos plus/minus devuelven nuevas instancias
 *    - Thread-safe por defecto
 * 
 * 4. FORMATEO Y PARSING:
 *    - DateTimeFormatter para formatear y parsear
 *    - Patrones: yyyy=año, MM=mes, dd=día, HH=hora24, hh=hora12
 *    - EEEE=día semana, MMMM=mes nombre
 * 
 * 5. BUENAS PRÁCTICAS:
 *    - Usa LocalDateTime para fechas sin zona horaria
 *    - Usa ZonedDateTime cuando las zonas horarias importan
 *    - Valida fechas de entrada con Try o Either
 *    - Almacena en UTC, muestra en zona local
 * 
 * 6. PRÓXIMO PASO:
 *    - Ver 8-colecciones.scala para estructuras de datos
 */