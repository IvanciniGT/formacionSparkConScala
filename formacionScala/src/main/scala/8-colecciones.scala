/**
 * 8-colecciones.scala
 * 
 * Colecciones y sus operaciones básicas en Scala
 * 
 * Este archivo demuestra:
 * - Arrays, Lists, Sets, Maps
 * - Colecciones mutables e inmutables
 * - Operaciones básicas (agregar, quitar, buscar)
 * - Métodos funcionales (map, filter, reduce, etc.)
 * - For comprehensions
 * - Conversiones entre colecciones
 */

import scala.collection.mutable

object ColeccionesBasicas {
  
  def main(args: Array[String]): Unit = {
    
    println("COLECCIONES EN SCALA")
    println("=" * 20)
    
    // Arrays
    trabajarConArrays()
    
    // Listas
    trabajarConListas()
    
    // Sets (conjuntos)
    trabajarConSets()
    
    // Maps (diccionarios)
    trabajarConMaps()
    
    // Operaciones funcionales
    operacionesFuncionales()
    
    // For comprehensions
    forComprehensions()
    
    // Colecciones mutables
    coleccionesMutables()
    
    // Conversiones y utilidades
    conversionesYUtilidades()
  }
  
  /**
   * Trabajar con Arrays (arreglos)
   */
  def trabajarConArrays(): Unit = {
    println("ARRAYS:")
    println("-" * 7)
    
    // Crear arrays
    val numeros = Array(1, 2, 3, 4, 5)
    val nombres = Array("Ana", "Juan", "María")
    val vacio = Array.empty[Int]
    val conTamaño = Array.ofDim[String](3)  // Array de 3 elementos null
    
    println(s"Números: ${numeros.mkString("[", ", ", "]")}")
    println(s"Nombres: ${nombres.mkString(", ")}")
    println(s"Vacío: ${vacio.mkString(", ")}")
    println(s"Con tamaño: ${conTamaño.mkString("[", ", ", "]")}")
    
    // Acceso a elementos
    println(s"Primer número: ${numeros(0)}")
    println(s"Último número: ${numeros(numeros.length - 1)}")
    println(s"Segundo nombre: ${nombres(1)}")
    
    // Propiedades
    println(s"Longitud números: ${numeros.length}")
    println(s"¿Está vacío? ${numeros.isEmpty}")
    println(s"¿No está vacío? ${numeros.nonEmpty}")
    
    // Modificar elementos (arrays son mutables)
    val numerosMutable = Array(10, 20, 30)
    numerosMutable(0) = 100
    println(s"Después de modificar: ${numerosMutable.mkString(", ")}")
    
    // Búsquedas
    println(s"¿Contiene 3? ${numeros.contains(3)}")
    println(s"Índice de 4: ${numeros.indexOf(4)}")
    println(s"¿Existe número > 3? ${numeros.exists(_ > 3)}")
    println(s"¿Todos > 0? ${numeros.forall(_ > 0)}")
    
    // Slice y take
    println(s"Primeros 3: ${numeros.take(3).mkString(", ")}")
    println(s"Últimos 2: ${numeros.takeRight(2).mkString(", ")}")
    println(s"Slice (1,4): ${numeros.slice(1, 4).mkString(", ")}")
    
    println()
  }
  
  /**
   * Trabajar con Listas (inmutables por defecto)
   */
  def trabajarConListas(): Unit = {
    println("LISTAS:")
    println("-" * 7)
    
    // Crear listas
    val numeros = List(1, 2, 3, 4, 5)
    val nombres = List("Ana", "Juan", "María")
    val vacia = List.empty[Int]
    val conRange = (1 to 10).toList
    
    println(s"Números: $numeros")
    println(s"Nombres: $nombres")
    println(s"Vacía: $vacia")
    println(s"Con range: $conRange")
    
    // Construcción con :: (cons)
    val lista1 = 1 :: 2 :: 3 :: Nil  // Nil es la lista vacía
    val lista2 = 0 :: numeros        // Prepender elemento
    val lista3 = numeros :+ 6        // Append elemento (menos eficiente)
    
    println(s"Con :: $lista1")
    println(s"Prepender 0: $lista2")
    println(s"Append 6: $lista3")
    
    // Acceso a elementos
    println(s"Head (primer elemento): ${numeros.head}")
    println(s"Tail (resto): ${numeros.tail}")
    println(s"Last (último): ${numeros.last}")
    println(s"Init (sin último): ${numeros.init}")
    
    // Acceso por índice (menos eficiente que head/tail)
    println(s"Elemento en índice 2: ${numeros(2)}")
    
    // Concatenación
    val lista4 = List(1, 2, 3)
    val lista5 = List(4, 5, 6)
    val concatenada = lista4 ++ lista5  // o lista4 ::: lista5
    
    println(s"Concatenación: $concatenada")
    
    // Operaciones útiles
    println(s"Longitud: ${numeros.length}")
    println(s"Suma: ${numeros.sum}")
    println(s"Máximo: ${numeros.max}")
    println(s"Mínimo: ${numeros.min}")
    println(s"Promedio: ${numeros.sum.toDouble / numeros.length}")
    
    // Búsquedas y filtros básicos
    println(s"¿Contiene 3? ${numeros.contains(3)}")
    println(s"Encontrar > 3: ${numeros.find(_ > 3)}")  // Option
    println(s"Filtrar pares: ${numeros.filter(_ % 2 == 0)}")
    println(s"Descartar primeros 2: ${numeros.drop(2)}")
    
    println()
  }
  
  /**
   * Trabajar con Sets (conjuntos - elementos únicos)
   */
  def trabajarConSets(): Unit = {
    println("SETS (CONJUNTOS):")
    println("-" * 17)
    
    // Crear sets
    val numeros = Set(1, 2, 3, 4, 5, 3, 2)  // Los duplicados se eliminan
    val nombres = Set("Ana", "Juan", "María", "Ana")
    val vacio = Set.empty[String]
    
    println(s"Números (sin duplicados): $numeros")
    println(s"Nombres (sin duplicados): $nombres")
    println(s"Vacío: $vacio")
    
    // Operaciones de conjuntos
    val set1 = Set(1, 2, 3, 4)
    val set2 = Set(3, 4, 5, 6)
    
    val union = set1 ++ set2        // o set1 union set2
    val interseccion = set1 & set2   // o set1 intersect set2
    val diferencia = set1 -- set2    // o set1 diff set2
    
    println(s"Set1: $set1")
    println(s"Set2: $set2")
    println(s"Unión: $union")
    println(s"Intersección: $interseccion")
    println(s"Diferencia (set1 - set2): $diferencia")
    
    // Verificaciones
    println(s"¿Set1 contiene 3? ${set1.contains(3)}")
    println(s"¿Set1 es subconjunto de unión? ${set1.subsetOf(union)}")
    println(s"¿Set1 y Set2 son disjuntos? ${(set1 & set2).isEmpty}")
    
    // Agregar y quitar elementos (retorna nuevo Set)
    val set3 = set1 + 10        // Agregar elemento
    val set4 = set1 - 1         // Quitar elemento
    val set5 = set1 ++ List(7, 8, 9)  // Agregar múltiples
    
    println(s"Agregar 10: $set3")
    println(s"Quitar 1: $set4")
    println(s"Agregar varios: $set5")
    
    println()
  }
  
  /**
   * Trabajar con Maps (diccionarios clave-valor)
   */
  def trabajarConMaps(): Unit = {
    println("MAPS (DICCIONARIOS):")
    println("-" * 20)
    
    // Crear maps
    val edades = Map("Ana" -> 25, "Juan" -> 30, "María" -> 28)
    val codigos = Map(1 -> "Uno", 2 -> "Dos", 3 -> "Tres")
    val vacio = Map.empty[String, Int]
    
    println(s"Edades: $edades")
    println(s"Códigos: $codigos")
    println(s"Vacío: $vacio")
    
    // Acceso a valores
    println(s"Edad de Ana: ${edades("Ana")}")
    println(s"Código de 2: ${codigos(2)}")
    
    // Acceso seguro con get (retorna Option)
    println(s"Edad de Pedro: ${edades.get("Pedro")}")  // None
    println(s"Edad de Ana con get: ${edades.get("Ana")}")  // Some(25)
    
    // Acceso con valor por defecto
    println(s"Edad de Pedro (default 0): ${edades.getOrElse("Pedro", 0)}")
    
    // Verificaciones
    println(s"¿Contiene clave 'Ana'? ${edades.contains("Ana")}")
    println(s"¿Contiene valor 25? ${edades.values.toSet.contains(25)}")
    
    // Obtener claves y valores
    println(s"Claves: ${edades.keys.mkString(", ")}")
    println(s"Valores: ${edades.values.mkString(", ")}")
    println(s"Pares clave-valor: ${edades.toList}")
    
    // Agregar y modificar (retorna nuevo Map)
    val edades2 = edades + ("Pedro" -> 35)     // Agregar nuevo
    val edades3 = edades + ("Ana" -> 26)       // Modificar existente
    val edades4 = edades ++ Map("Luis" -> 40, "Carmen" -> 33)  // Agregar múltiples
    
    println(s"Agregar Pedro: $edades2")
    println(s"Modificar Ana: $edades3")
    println(s"Agregar múltiples: $edades4")
    
    // Quitar elementos
    val edades5 = edades - "Juan"              // Quitar una clave
    val edades6 = edades -- List("Ana", "María")  // Quitar múltiples
    
    println(s"Sin Juan: $edades5")
    println(s"Sin Ana y María: $edades6")
    
    // Transformaciones útiles
    val edadesEn10Anos = edades.map { case (nombre, edad) => nombre -> (edad + 10) }
    val soloMayores25 = edades.filter { case (_, edad) => edad > 25 }
    
    println(s"Edades en 10 años: $edadesEn10Anos")
    println(s"Solo mayores de 25: $soloMayores25")
    
    println()
  }
  
  /**
   * Operaciones funcionales avanzadas
   */
  def operacionesFuncionales(): Unit = {
    println("OPERACIONES FUNCIONALES:")
    println("-" * 24)
    
    val numeros = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val palabras = List("scala", "java", "python", "javascript", "go")
    val personas = List(
      Map("nombre" -> "Ana", "edad" -> 25),
      Map("nombre" -> "Juan", "edad" -> 30),
      Map("nombre" -> "María", "edad" -> 28)
    )
    
    // MAP - transformar cada elemento
    val cuadrados = numeros.map(x => x * x)
    val longitudesPalabras = palabras.map(_.length)
    val nombresMayusculas = palabras.map(_.toUpperCase)
    
    println(s"Cuadrados: $cuadrados")
    println(s"Longitudes: $longitudesPalabras")
    println(s"Mayúsculas: $nombresMayusculas")
    
    // FILTER - filtrar elementos
    val pares = numeros.filter(_ % 2 == 0)
    val palabrasCortas = palabras.filter(_.length <= 4)
    val mayores27 = personas.filter(p => p("edad").asInstanceOf[Int] > 27)
    
    println(s"Pares: $pares")
    println(s"Palabras cortas: $palabrasCortas")
    println(s"Mayores de 27: $mayores27")
    
    // REDUCE - combinar elementos
    val suma = numeros.reduce(_ + _)
    val producto = numeros.reduce(_ * _)
    val maximo = numeros.reduce((a, b) => if (a > b) a else b)
    
    println(s"Suma: $suma")
    println(s"Producto: $producto")
    println(s"Máximo: $maximo")
    
    // FOLD - reduce con valor inicial
    val sumaConInicial = numeros.fold(100)(_ + _)  // Empieza en 100
    val concatenacion = palabras.fold("")(_ + " " + _)
    
    println(s"Suma con inicial: $sumaConInicial")
    println(s"Concatenación: $concatenacion")
    
    // FLATMAP - map + flatten
    val rangos = List(List(1, 2), List(3, 4), List(5, 6))
    val aplanado = rangos.flatten
    val duplicados = numeros.flatMap(x => List(x, x))
    
    println(s"Rangos: $rangos")
    println(s"Aplanado: $aplanado")
    println(s"Duplicados: $duplicados")
    
    // GROUPBY - agrupar por criterio
    val paresImpares = numeros.groupBy(_ % 2 == 0)
    val palabrasPorLongitud = palabras.groupBy(_.length)
    
    println(s"Pares/Impares: $paresImpares")
    println(s"Por longitud: $palabrasPorLongitud")
    
    // PARTITION - dividir en dos grupos
    val (menores5, mayores5) = numeros.partition(_ < 5)
    println(s"Menores que 5: $menores5")
    println(s"Mayores o iguales a 5: $mayores5")
    
    // Operaciones combinadas
    val resultado = numeros
      .filter(_ % 2 == 0)    // Solo pares
      .map(_ * 3)            // Multiplicar por 3
      .filter(_ > 10)        // Solo mayores que 10
      .sum                   // Sumar todo
    
    println(s"Operación combinada: $resultado")
    
    println()
  }
  
  /**
   * For comprehensions - sintaxis alternativa para operaciones
   */
  def forComprehensions(): Unit = {
    println("FOR COMPREHENSIONS:")
    println("-" * 19)
    
    val numeros = List(1, 2, 3, 4, 5)
    val letras = List("a", "b", "c")
    
    // For con yield - equivale a map
    val cuadrados = for (n <- numeros) yield n * n
    println(s"Cuadrados con for: $cuadrados")
    
    // For con filtro - equivale a filter + map
    val paresCuadrados = for {
      n <- numeros
      if n % 2 == 0
    } yield n * n
    println(s"Cuadrados de pares: $paresCuadrados")
    
    // For anidado - equivale a flatMap
    val combinaciones = for {
      numero <- numeros
      letra <- letras
    } yield s"$numero$letra"
    println(s"Combinaciones: ${combinaciones.take(6)}")
    
    // For con múltiples filtros
    val filtradoComplejo = for {
      n <- numeros
      if n > 2
      if n < 5
    } yield n * 10
    println(s"Filtrado complejo: $filtradoComplejo")
    
    // For con asignaciones intermedias
    val conAsignaciones = for {
      palabra <- List("scala", "java", "python")
      longitud = palabra.length
      if longitud > 4
      mayuscula = palabra.toUpperCase
    } yield s"$mayuscula ($longitud)"
    
    println(s"Con asignaciones: $conAsignaciones")
    
    // For con Maps
    val personas = Map("Ana" -> 25, "Juan" -> 30, "María" -> 28)
    val descripciones = for {
      (nombre, edad) <- personas
      if edad >= 28
    } yield s"$nombre tiene $edad años"
    
    println(s"Descripciones: $descripciones")
    
    // Equivalencia con métodos funcionales
    println("\nEquivalencias:")
    val metodoFuncional = numeros.filter(_ % 2 == 0).map(_ * 2)
    val forComprehension = for (n <- numeros if n % 2 == 0) yield n * 2
    
    println(s"Método funcional: $metodoFuncional")
    println(s"For comprehension: $forComprehension")
    println(s"¿Son iguales? ${metodoFuncional == forComprehension}")
    
    println()
  }
  
  /**
   * Colecciones mutables
   */
  def coleccionesMutables(): Unit = {
    println("COLECCIONES MUTABLES:")
    println("-" * 21)
    
    // ArrayBuffer - lista mutable
    val buffer = mutable.ArrayBuffer(1, 2, 3)
    println(s"Buffer inicial: $buffer")
    
    buffer += 4              // Agregar elemento
    buffer ++= List(5, 6)    // Agregar múltiples
    buffer.insert(0, 0)      // Insertar en posición
    buffer.remove(1)         // Quitar por índice
    
    println(s"Buffer modificado: $buffer")
    
    // ListBuffer - otra lista mutable
    val listBuffer = mutable.ListBuffer("a", "b", "c")
    listBuffer.prepend("inicio")
    listBuffer.append("fin")
    
    println(s"ListBuffer: $listBuffer")
    
    // Set mutable
    val setMutable = mutable.Set(1, 2, 3)
    setMutable += 4
    setMutable ++= Set(5, 6)
    setMutable -= 1
    
    println(s"Set mutable: $setMutable")
    
    // Map mutable
    val mapMutable = mutable.Map("a" -> 1, "b" -> 2)
    mapMutable("c") = 3              // Agregar/modificar
    mapMutable += ("d" -> 4)         // Agregar
    mapMutable ++= Map("e" -> 5, "f" -> 6)  // Agregar múltiples
    mapMutable.remove("a")           // Quitar
    
    println(s"Map mutable: $mapMutable")
    
    // Conversión mutable <-> inmutable
    val listaInmutable = List(1, 2, 3)
    val bufferDesdeLista = listaInmutable.to(mutable.ArrayBuffer)
    val listaDesdeBuffer = buffer.toList
    
    println(s"Lista inmutable: $listaInmutable")
    println(s"Buffer desde lista: $bufferDesdeLista")
    println(s"Lista desde buffer: $listaDesdeBuffer")
    
    println("\n💡 REGLA: Prefiere colecciones inmutables por defecto")
    println("💡 USA mutables solo cuando la performance lo requiera")
    
    println()
  }
  
  /**
   * Conversiones y utilidades
   */
  def conversionesYUtilidades(): Unit = {
    println("CONVERSIONES Y UTILIDADES:")
    println("-" * 26)
    
    // Conversiones entre tipos de colecciones
    val lista = List(1, 2, 3, 4, 5)
    val array = lista.toArray
    val set = lista.toSet
    val map = lista.zipWithIndex.toMap  // (valor, índice) como (clave, valor)
    
    println(s"Lista: $lista")
    println(s"Array: ${array.mkString("[", ", ", "]")}")
    println(s"Set: $set")
    println(s"Map (valor->índice): $map")
    
    // Zip - combinar colecciones
    val nombres = List("Ana", "Juan", "María")
    val edades = List(25, 30, 28)
    val ciudades = List("Madrid", "Barcelona", "Valencia")
    
    val nombreEdad = nombres.zip(edades)
    val completo = nombres.zip(edades).zip(ciudades).map {
      case ((nombre, edad), ciudad) => (nombre, edad, ciudad)
    }
    
    println(s"Nombres con edades: $nombreEdad")
    println(s"Información completa: $completo")
    
    // Unzip - separar pares
    val (nombresExtraidos, edadesExtraidas) = nombreEdad.unzip
    println(s"Nombres extraídos: $nombresExtraidos")
    println(s"Edades extraídas: $edadesExtraidas")
    
    // Utilidades estadísticas
    val numeros = List(1, 5, 3, 9, 2, 8, 4, 7, 6)
    
    println(s"Números desordenados: $numeros")
    println(s"Ordenados: ${numeros.sorted}")
    println(s"Ordenados desc: ${numeros.sorted.reverse}")
    println(s"Mezclados: ${scala.util.Random.shuffle(numeros)}")
    
    // Estadísticas
    println(s"Suma: ${numeros.sum}")
    println(s"Promedio: ${numeros.sum.toDouble / numeros.length}")
    println(s"Máximo: ${numeros.max}")
    println(s"Mínimo: ${numeros.min}")
    println(s"Tamaño: ${numeros.size}")
    
    // Agrupar y contar
    val palabras = List("scala", "java", "scala", "python", "java", "go", "scala")
    val conteos = palabras.groupBy(identity).view.mapValues(_.size).toMap
    
    println(s"Palabras: $palabras")
    println(s"Conteos: $conteos")
    
    // Operaciones con índices
    val conIndices = palabras.zipWithIndex
    val enumerated = palabras.zipWithIndex.map { case (palabra, i) => s"$i: $palabra" }
    
    println(s"Con índices: $conIndices")
    println(s"Enumerado: $enumerated")
    
    // Sliding window
    val ventanas = numeros.sliding(3).toList
    println(s"Ventanas de 3: $ventanas")
    
    // Distinct y unique
    val numerosConDuplicados = List(1, 2, 2, 3, 3, 3, 4, 4, 5)
    val sinDuplicados = numerosConDuplicados.distinct
    
    println(s"Con duplicados: $numerosConDuplicados")
    println(s"Sin duplicados: $sinDuplicados")
    
    println("\n🎯 RESUMEN: Scala tiene colecciones muy potentes")
    println("🎯 RESUMEN: Inmutables por defecto, mutables cuando sea necesario")
    println("🎯 RESUMEN: Métodos funcionales facilitan transformaciones")
    println("🎯 RESUMEN: For comprehensions ofrecen sintaxis legible")
  }
}

/*
 * NOTAS IMPORTANTES:
 * 
 * 1. EJECUTAR ESTE ARCHIVO:
 *    mvn exec:java -Dexec.mainClass="ColeccionesBasicas"
 * 
 * 2. JERARQUÍA DE COLECCIONES:
 *    - Iterable (raíz)
 *      ├── Seq (secuencias ordenadas)
 *      │   ├── List (inmutable, enlazada)
 *      │   ├── Array (mutable, indexada)
 *      │   └── ArrayBuffer (mutable, dinámica)
 *      ├── Set (elementos únicos)
 *      └── Map (clave-valor)
 * 
 * 3. INMUTABLES VS MUTABLES:
 *    - Por defecto: inmutables (scala.collection.immutable)
 *    - Mutables: scala.collection.mutable
 *    - Inmutables son thread-safe y más seguras
 * 
 * 4. OPERACIONES CLAVE:
 *    - map: transformar elementos
 *    - filter: filtrar elementos
 *    - reduce/fold: combinar elementos
 *    - flatMap: map + flatten
 *    - groupBy: agrupar por criterio
 * 
 * 5. FOR COMPREHENSIONS:
 *    - Sintaxis más legible para operaciones complejas
 *    - for (x <- collection if condition) yield transformation
 *    - Equivale a filter + map
 * 
 * 6. PRÓXIMO PASO:
 *    - Ver 9-streams.scala para evaluación perezosa
 */