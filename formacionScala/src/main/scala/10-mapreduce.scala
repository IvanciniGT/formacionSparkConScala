/**
 * 10-mapreduce.scala
 * 
 * Conceptos MapReduce sin Spark - Fundamentos
 * 
 * Este archivo demuestra:
 * - ¿Qué es MapReduce?
 * - Implementación básica de Map y Reduce
 * - Patrones comunes de MapReduce
 * - Agregaciones y transformaciones distribuidas
 * - Simulación de procesamiento distribuido
 * - Preparación para entender Spark
 */

object ConceptosMapReduce {
  
  def main(args: Array[String]): Unit = {
    
    println("CONCEPTOS MAPREDUCE SIN SPARK")
    println("=" * 29)
    
    // Fundamentos MapReduce
    fundamentosMapReduce()
    
    // Patrones básicos
    patronesBasicos()
    
    // Conteo de palabras (ejemplo clásico)
    contarPalabras()
    
    // Agregaciones complejas
    agregacionesComplejas()
    
    // Simulación distribuida
    simulacionDistribuida()
    
    // Patrones avanzados
    patronesAvanzados()
  }
  
  /**
   * Fundamentos del paradigma MapReduce
   */
  def fundamentosMapReduce(): Unit = {
    println("FUNDAMENTOS DE MAPREDUCE:")
    println("-" * 25)
    
    println("MapReduce es un paradigma de programación para procesar grandes cantidades de datos")
    println("Se basa en dos operaciones principales:")
    println("  1. MAP: Transformar cada elemento del dataset")
    println("  2. REDUCE: Agregar/combinar los resultados")
    println()
    
    // Ejemplo simple: calcular suma de cuadrados
    val numeros = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    
    println("=== EJEMPLO: Suma de cuadrados ===")
    println(s"Datos originales: $numeros")
    
    // FASE MAP: transformar cada número a su cuadrado
    val cuadrados = numeros.map { numero =>
      val cuadrado = numero * numero
      println(s"  MAP: $numero -> $cuadrado")
      cuadrado
    }
    
    println(s"Después de MAP: $cuadrados")
    
    // FASE REDUCE: sumar todos los cuadrados
    val suma = cuadrados.reduce { (acc, valor) =>
      val nuevoAcc = acc + valor
      println(s"  REDUCE: $acc + $valor = $nuevoAcc")
      nuevoAcc
    }
    
    println(s"Resultado final: $suma")
    
    // Todo en una línea (como se haría en Spark)
    val resultadoDirecto = numeros.map(x => x * x).reduce(_ + _)
    println(s"En una línea: $resultadoDirecto")
    
    println()
  }
  
  /**
   * Patrones básicos de MapReduce
   */
  def patronesBasicos(): Unit = {
    println("PATRONES BÁSICOS:")
    println("-" * 17)
    
    val ventas = List(
      ("Producto A", 100.0),
      ("Producto B", 250.0),
      ("Producto A", 150.0),
      ("Producto C", 300.0),
      ("Producto B", 200.0),
      ("Producto A", 80.0)
    )
    
    println(s"Datos de ventas: $ventas")
    
    // PATRÓN 1: Filtrar y sumar
    println("\n=== PATRÓN 1: Filtrar y sumar ===")
    val ventasProductoA = ventas
      .filter(_._1 == "Producto A")    // Filtrar
      .map(_._2)                       // MAP: extraer montos
      .reduce(_ + _)                   // REDUCE: sumar
    
    println(s"Ventas totales Producto A: $ventasProductoA")
    
    // PATRÓN 2: Transformar y agregar
    println("\n=== PATRÓN 2: Transformar y agregar ===")
    val ventasConImpuestos = ventas
      .map { case (producto, monto) => (producto, monto * 1.21) }  // MAP: agregar IVA
      .groupBy(_._1)                   // Agrupar por producto
      .map { case (producto, lista) => (producto, lista.map(_._2).sum) }  // REDUCE por grupo
    
    println("Ventas con IVA por producto:")
    ventasConImpuestos.foreach { case (producto, total) =>
      println(s"  $producto: €${"%.2f".format(total)}")
    }
    
    // PATRÓN 3: Contar elementos
    println("\n=== PATRÓN 3: Contar elementos ===")
    val conteoVentas = ventas
      .map(venta => (venta._1, 1))     // MAP: (producto, 1)
      .groupBy(_._1)                   // Agrupar por producto
      .map { case (producto, lista) => (producto, lista.map(_._2).sum) }  // REDUCE: sumar contadores
    
    println("Número de ventas por producto:")
    conteoVentas.foreach { case (producto, conteo) =>
      println(s"  $producto: $conteo ventas")
    }
    
    println()
  }
  
  /**
   * Ejemplo clásico: Contar palabras (Word Count)
   */
  def contarPalabras(): Unit = {
    println("EJEMPLO CLÁSICO: CONTAR PALABRAS")
    println("-" * 33)
    
    val documento = """
      Scala es un lenguaje de programación.
      Scala combina programación funcional y orientada a objetos.
      Apache Spark está escrito en Scala.
      Scala es ideal para Big Data.
    """.trim
    
    println("Documento original:")
    println(documento)
    println()
    
    // Implementación paso a paso
    println("=== PASO A PASO ===")
    
    // 1. Dividir en líneas
    val lineas = documento.split("\n").toList
    println(s"1. Líneas: ${lineas.length}")
    
    // 2. MAP: convertir cada línea en palabras
    val palabrasPorLinea = lineas.map { linea =>
      val palabras = linea.toLowerCase
        .replaceAll("[^a-záéíóúüñ ]", "")  // Solo letras y espacios
        .split("\\s+")
        .filter(_.nonEmpty)
        .toList
      println(s"   Línea: '${linea.take(30)}...' -> ${palabras.length} palabras")
      palabras
    }
    
    // 3. Aplanar (flatten) - convertir List[List[String]] en List[String]
    val todasLasPalabras = palabrasPorLinea.flatten
    println(s"2. Total palabras: ${todasLasPalabras.length}")
    
    // 4. MAP: convertir cada palabra en (palabra, 1)
    val palabrasConContador = todasLasPalabras.map(palabra => (palabra, 1))
    println(s"3. Palabras con contador: ${palabrasConContador.take(5)}")
    
    // 5. Agrupar por palabra
    val palabrasAgrupadas = palabrasConContador.groupBy(_._1)
    println(s"4. Grupos de palabras: ${palabrasAgrupadas.keys.toList.sorted}")
    
    // 6. REDUCE: sumar contadores por palabra
    val conteoPalabras = palabrasAgrupadas.map { 
      case (palabra, lista) => (palabra, lista.map(_._2).sum) 
    }
    
    println("\n=== RESULTADO FINAL ===")
    val palabrasOrdenadas = conteoPalabras.toList.sortBy(-_._2)  // Ordenar por frecuencia
    
    palabrasOrdenadas.foreach { case (palabra, conteo) =>
      println(f"$palabra%15s: $conteo%2d veces")
    }
    
    // Versión concisa (como se haría en Spark)
    println("\n=== VERSIÓN CONCISA ===")
    val conteoDirecto = documento
      .toLowerCase
      .replaceAll("[^a-záéíóúüñ ]", "")
      .split("\\s+")
      .filter(_.nonEmpty)
      .groupBy(identity)
      .view.mapValues(_.length)
      .toMap
      .toList
      .sortBy(-_._2)
    
    println("Top 5 palabras más frecuentes:")
    conteoDirecto.take(5).foreach { case (palabra, conteo) =>
      println(s"  $palabra: $conteo")
    }
    
    println()
  }
  
  /**
   * Agregaciones complejas con MapReduce
   */
  def agregacionesComplejas(): Unit = {
    println("AGREGACIONES COMPLEJAS:")
    println("-" * 23)
    
    // Datos de ejemplo: transacciones de e-commerce
    case class Transaccion(
      id: String,
      cliente: String,
      producto: String,
      categoria: String,
      cantidad: Int,
      precio: Double,
      fecha: String
    )
    
    val transacciones = List(
      Transaccion("T001", "Cliente A", "Laptop", "Electrónicos", 1, 1200.0, "2024-01-15"),
      Transaccion("T002", "Cliente B", "Ratón", "Electrónicos", 2, 25.0, "2024-01-15"),
      Transaccion("T003", "Cliente A", "Teclado", "Electrónicos", 1, 80.0, "2024-01-16"),
      Transaccion("T004", "Cliente C", "Silla", "Mobiliario", 1, 150.0, "2024-01-16"),
      Transaccion("T005", "Cliente B", "Mesa", "Mobiliario", 1, 300.0, "2024-01-17"),
      Transaccion("T006", "Cliente A", "Monitor", "Electrónicos", 2, 250.0, "2024-01-17"),
      Transaccion("T007", "Cliente D", "Lámpara", "Mobiliario", 3, 45.0, "2024-01-18")
    )
    
    println(s"Total transacciones: ${transacciones.length}")
    
    // AGREGACIÓN 1: Ventas por categoría
    println("\n=== VENTAS POR CATEGORÍA ===")
    val ventasPorCategoria = transacciones
      .map(t => (t.categoria, t.cantidad * t.precio))  // MAP: (categoría, total)
      .groupBy(_._1)                                   // Agrupar por categoría
      .map { case (categoria, ventas) =>               // REDUCE: sumar por categoría
        (categoria, ventas.map(_._2).sum)
      }
    
    ventasPorCategoria.toList.sortBy(-_._2).foreach { case (categoria, total) =>
      println(f"  $categoria%15s: €$total%8.2f")
    }
    
    // AGREGACIÓN 2: Estadísticas por cliente
    println("\n=== ESTADÍSTICAS POR CLIENTE ===")
    val estadisticasClientes = transacciones
      .groupBy(_.cliente)
      .map { case (cliente, transaccionesCliente) =>
        val totalGastado = transaccionesCliente.map(t => t.cantidad * t.precio).sum
        val numTransacciones = transaccionesCliente.length
        val ticketPromedio = totalGastado / numTransacciones
        
        (cliente, totalGastado, numTransacciones, ticketPromedio)
      }
    
    estadisticasClientes.toList.sortBy(-_._2).foreach { 
      case (cliente, total, numTrans, promedio) =>
        println(f"$cliente%10s: €$total%8.2f ($numTrans%d trans, promedio €$promedio%6.2f)")
    }
    
    // AGREGACIÓN 3: Tendencia por día
    println("\n=== VENTAS POR DÍA ===")
    val ventasPorDia = transacciones
      .map(t => (t.fecha, t.cantidad * t.precio))
      .groupBy(_._1)
      .map { case (fecha, ventas) => (fecha, ventas.map(_._2).sum) }
      .toList
      .sortBy(_._1)
    
    ventasPorDia.foreach { case (fecha, total) =>
      println(f"$fecha: €$total%8.2f")
    }
    
    // AGREGACIÓN 4: Productos más vendidos
    println("\n=== TOP PRODUCTOS (por cantidad) ===")
    val productosTopCantidad = transacciones
      .map(t => (t.producto, t.cantidad))
      .groupBy(_._1)
      .map { case (producto, cantidades) => 
        (producto, cantidades.map(_._2).sum) 
      }
      .toList
      .sortBy(-_._2)
    
    productosTopCantidad.take(5).foreach { case (producto, cantidad) =>
      println(f"  $producto%10s: $cantidad%3d unidades")
    }
    
    println()
  }
  
  /**
   * Simulación de procesamiento distribuido
   */
  def simulacionDistribuida(): Unit = {
    println("SIMULACIÓN PROCESAMIENTO DISTRIBUIDO:")
    println("-" * 37)
    
    // Simular un dataset grande distribuido en "particiones"
    val dataset = (1 to 1000).toList
    val numParticiones = 4
    val tamanioParticion = dataset.length / numParticiones
    
    // Dividir datos en particiones (como haría Spark)
    val particiones = dataset.grouped(tamanioParticion).toList
    
    println(s"Dataset original: ${dataset.length} elementos")
    println(s"Dividido en $numParticiones particiones de ~$tamanioParticion elementos")
    
    // Función de mapeo costosa (simula procesamiento complejo)
    def procesamientoCostoso(numero: Int): (String, Int) = {
      Thread.sleep(1)  // Simular operación lenta
      val categoria = if (numero % 2 == 0) "par" else "impar"
      (categoria, numero * numero)
    }
    
    println("\n=== FASE MAP (distribuida) ===")
    val resultadosMap = particiones.zipWithIndex.map { case (particion, indice) =>
      println(s"Procesando partición $indice (${particion.length} elementos)...")
      val inicio = System.currentTimeMillis()
      
      val resultado = particion.map(procesamientoCostoso)
      
      val tiempo = System.currentTimeMillis() - inicio
      println(s"  Partición $indice completada en ${tiempo}ms")
      
      resultado
    }
    
    // Combinar resultados de todas las particiones
    val todosLosResultados = resultadosMap.flatten
    
    println(s"\nResultados MAP combinados: ${todosLosResultados.length} elementos")
    
    // FASE SHUFFLE: Agrupar por clave (como hace Spark internamente)
    println("\n=== FASE SHUFFLE ===")
    val datosPorClave = todosLosResultados.groupBy(_._1)
    
    datosPorClave.foreach { case (clave, valores) =>
      println(s"Clave '$clave': ${valores.length} valores")
    }
    
    // FASE REDUCE: Agregar por clave
    println("\n=== FASE REDUCE ===")
    val resultadosFinales = datosPorClave.map { case (clave, valores) =>
      val suma = valores.map(_._2).sum
      val promedio = suma.toDouble / valores.length
      val maximo = valores.map(_._2).max
      val minimo = valores.map(_._2).min
      
      (clave, Map(
        "suma" -> suma,
        "promedio" -> promedio,
        "maximo" -> maximo,
        "minimo" -> minimo,
        "conteo" -> valores.length
      ))
    }
    
    println("Estadísticas finales:")
    resultadosFinales.foreach { case (categoria, stats) =>
      println(s"\nCategoría: $categoria")
      stats.foreach { case (metrica, valor) =>
        println(s"  $metrica: $valor")
      }
    }
    
    println("\n💡 ESTO ES LO QUE HACE SPARK AUTOMÁTICAMENTE:")
    println("  1. Divide los datos en particiones")
    println("  2. Ejecuta MAP en paralelo en cada partición")
    println("  3. Hace SHUFFLE para reagrupar por clave")
    println("  4. Ejecuta REDUCE en paralelo por clave")
    println("  5. Combina los resultados finales")
    
    println()
  }
  
  /**
   * Patrones avanzados de MapReduce
   */
  def patronesAvanzados(): Unit = {
    println("PATRONES AVANZADOS:")
    println("-" * 19)
    
    // Datos de ejemplo: logs de servidor web
    case class LogEntry(
      timestamp: String,
      ip: String,
      method: String,
      url: String,
      status: Int,
      size: Int
    )
    
    val logs = List(
      LogEntry("2024-01-15 10:15:23", "192.168.1.1", "GET", "/home", 200, 1024),
      LogEntry("2024-01-15 10:15:45", "192.168.1.2", "GET", "/products", 200, 2048),
      LogEntry("2024-01-15 10:16:12", "192.168.1.1", "POST", "/login", 401, 512),
      LogEntry("2024-01-15 10:16:34", "192.168.1.3", "GET", "/home", 200, 1024),
      LogEntry("2024-01-15 10:17:01", "192.168.1.2", "GET", "/products", 404, 256),
      LogEntry("2024-01-15 10:17:23", "192.168.1.1", "GET", "/home", 200, 1024),
      LogEntry("2024-01-15 10:17:45", "192.168.1.4", "GET", "/api/data", 500, 128)
    )
    
    // PATRÓN 1: Top-K (elementos más frecuentes)
    println("=== PATRÓN TOP-K: URLs más visitadas ===")
    val topUrls = logs
      .map(log => (log.url, 1))          // MAP: (url, 1)
      .groupBy(_._1)                     // Agrupar por URL
      .map { case (url, visits) =>       // REDUCE: contar visitas
        (url, visits.map(_._2).sum)
      }
      .toList
      .sortBy(-_._2)                     // Ordenar por frecuencia
      .take(3)                           // Top 3
    
    topUrls.foreach { case (url, conteo) =>
      println(s"  $url: $conteo visitas")
    }
    
    // PATRÓN 2: Análisis de errores
    println("\n=== PATRÓN ANÁLISIS: Errores por IP ===")
    val erroresPorIp = logs
      .filter(_.status >= 400)           // Solo errores
      .map(log => (log.ip, (log.status, log.url)))  // MAP: (ip, (status, url))
      .groupBy(_._1)                     // Agrupar por IP
      .map { case (ip, errores) =>       // REDUCE: analizar errores
        val statusCodes = errores.map(_._2._1).distinct
        val urls = errores.map(_._2._2).distinct
        (ip, errores.length, statusCodes, urls)
      }
    
    erroresPorIp.foreach { case (ip, numErrores, status, urls) =>
      println(s"  $ip: $numErrores errores (status: $status, urls: $urls)")
    }
    
    // PATRÓN 3: Ventana deslizante temporal
    println("\n=== PATRÓN VENTANA: Tráfico por minuto ===")
    val trafficoPorMinuto = logs
      .map { log =>
        // Extraer minuto del timestamp
        val minuto = log.timestamp.substring(0, 16)  // "2024-01-15 10:15"
        (minuto, log.size)
      }
      .groupBy(_._1)
      .map { case (minuto, registros) =>
        val totalBytes = registros.map(_._2).sum
        val numRequests = registros.length
        (minuto, numRequests, totalBytes)
      }
      .toList
      .sortBy(_._1)
    
    trafficoPorMinuto.foreach { case (minuto, requests, bytes) =>
      println(f"  $minuto: $requests%2d requests, $bytes%4d bytes")
    }
    
    // PATRÓN 4: Join de datos (combinación)
    println("\n=== PATRÓN JOIN: Usuarios con actividad ===")
    
    // Datos de usuarios
    val usuarios = Map(
      "192.168.1.1" -> "Alice",
      "192.168.1.2" -> "Bob", 
      "192.168.1.3" -> "Charlie"
    )
    
    val actividadUsuarios = logs
      .map(log => (log.ip, 1))           // MAP: (ip, 1)
      .groupBy(_._1)                     // Agrupar por IP
      .map { case (ip, actividad) =>     // REDUCE: contar actividad
        val numActividad = actividad.map(_._2).sum
        val nombreUsuario = usuarios.getOrElse(ip, "Desconocido")
        (ip, nombreUsuario, numActividad)
      }
      .toList
      .sortBy(-_._3)
    
    actividadUsuarios.foreach { case (ip, nombre, actividad) =>
      println(f"  $ip ($nombre%8s): $actividad%2d actividades")
    }
    
    // PATRÓN 5: Co-occurrence (co-ocurrencia)
    println("\n=== PATRÓN CO-OCCURRENCE: URLs visitadas juntas ===")
    val sessionesPorIp = logs.groupBy(_.ip)
    
    val coOcurrencias = sessionesPorIp.flatMap { case (ip, logsUsuario) =>
      val urls = logsUsuario.map(_.url).distinct
      // Generar pares de URLs visitadas por el mismo usuario
      for {
        url1 <- urls
        url2 <- urls
        if url1 < url2  // Evitar duplicados (a,b) y (b,a)
      } yield ((url1, url2), 1)
    }
    .groupBy(_._1)
    .map { case (par, ocurrencias) => (par, ocurrencias.map(_._2).sum) }
    .toList
    .sortBy(-_._2)
    
    println("Pares de URLs frecuentemente visitadas juntas:")
    coOcurrencias.take(3).foreach { case ((url1, url2), conteo) =>
      println(s"  ($url1, $url2): $conteo veces")
    }
    
    println("\n🎯 PREPARACIÓN PARA SPARK:")
    println("  🔗 Todos estos patrones se implementan igual en Spark")
    println("  🔗 Spark añade distribución automática y tolerancia a fallos")
    println("  🔗 RDD/DataFrame/Dataset usan estos mismos conceptos")
    println("  🔗 La lógica de negocio es idéntica")
    println("  🔗 Spark optimiza automáticamente la ejecución")
  }
}

/*
 * NOTAS IMPORTANTES :
 * 
 * 1. EJECUTAR ESTE ARCHIVO:
 *    mvn exec:java -Dexec.mainClass="ConceptosMapReduce"
 * 
 * 2. CONCEPTOS FUNDAMENTALES:
 *    - MAP: transformar cada elemento individualmente
 *    - REDUCE: combinar/agregar elementos
 *    - SHUFFLE: reagrupar datos por clave (automático en Spark)
 *    - PARTITION: dividir datos para procesamiento paralelo
 * 
 * 3. PATRONES MAPREDUCE CLÁSICOS:
 *    - Word Count: contar elementos
 *    - Aggregation: sumar, promediar, max, min
 *    - Top-K: elementos más frecuentes
 *    - Join: combinar datasets
 *    - Co-occurrence: elementos que aparecen juntos
 * 
 * 4. VENTAJAS DE MAPREDUCE:
 *    - Paralelización automática
 *    - Escalabilidad horizontal
 *    - Tolerancia a fallos
 *    - Manejo de grandes volúmenes de datos
 * 
 * 5. CONEXIÓN CON SPARK:
 *    - Spark implementa MapReduce en memoria
 *    - RDD.map() y RDD.reduce() son exactamente esto
 *    - DataFrame operations son MapReduce de alto nivel
 *    - Mismo paradigma, mejor rendimiento
 * 
 * 6. PRÓXIMO PASO:
 *    - Ver 11-clases.scala para POO y ejemplo completo
 *    - Después estarás listo para Apache Spark!
 */