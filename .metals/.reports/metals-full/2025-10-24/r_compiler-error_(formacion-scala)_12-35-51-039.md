file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
### scala.reflect.internal.FatalError: 
  ThisType(method mostrarResultadoDeLaMano) for sym which is not a class
     while compiling: file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
        during phase: globalPhase=<no phase>, enteringPhase=parser
     library version: version 2.13.12
    compiler version: version 2.13.12
  reconstructed args: -release:11 -classpath <WORKSPACE>/formacionScala/target/bloop-bsp-clients-classes/classes-Metals-OZrIsQ6qSmqYvgIZCqniFg==:<HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/sourcegraph/semanticdb-javac/0.11.1/semanticdb-javac-0.11.1.jar:<WORKSPACE>/formacionScala/target/classes:<HOME>/.m2/repository/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar:<HOME>/.m2/repository/org/scala-lang/scala-compiler/2.13.12/scala-compiler-2.13.12.jar:<HOME>/.m2/repository/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar:<HOME>/.m2/repository/io/github/java-diff-utils/java-diff-utils/4.12/java-diff-utils-4.12.jar:<HOME>/.m2/repository/org/jline/jline/3.22.0/jline-3.22.0.jar:<HOME>/.m2/repository/net/java/dev/jna/jna/5.13.0/jna-5.13.0.jar:<HOME>/.m2/repository/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar -Xplugin-require:semanticdb -Yrangepos -Ymacro-expand:discard -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: Ident(_CURSOR_eleccionJugador)
       tree position: line 59 of file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
            tree tpe: <error>
              symbol: value <error: <none>>
   symbol definition: val <error: <none>>: <error> (a TermSymbol)
      symbol package: <empty>
       symbol owners: value <error: <none>> -> method solicitarEleccionAlJugador -> object JuegoPiedraPapelTijera
           call site: <none> in <none>

== Source file context for tree position ==

    56             println("\nElige tu opción: (0) Piedra, (1) Papel, (2) Tijera")
    57             val entrada = scala.io.StdIn.readLine()
    58             try {
    59                 _CURSOR_eleccionJugador = entrada.toInt
    60                 if (OPCIONES.contains(eleccionJugador)) {
    61                     eleccionValida = true
    62                 } else {

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.13.12
Classpath:
<WORKSPACE>/formacionScala/target/bloop-bsp-clients-classes/classes-Metals-OZrIsQ6qSmqYvgIZCqniFg== [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/sourcegraph/semanticdb-javac/0.11.1/semanticdb-javac-0.11.1.jar [exists ], <WORKSPACE>/formacionScala/target/classes [exists ], <HOME>/.m2/repository/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.m2/repository/org/scala-lang/scala-compiler/2.13.12/scala-compiler-2.13.12.jar [exists ], <HOME>/.m2/repository/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.m2/repository/io/github/java-diff-utils/java-diff-utils/4.12/java-diff-utils-4.12.jar [exists ], <HOME>/.m2/repository/org/jline/jline/3.22.0/jline-3.22.0.jar [exists ], <HOME>/.m2/repository/net/java/dev/jna/jna/5.13.0/jna-5.13.0.jar [exists ], <HOME>/.m2/repository/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ]
Options:
-release 11 -Yrangepos -Xplugin-require:semanticdb


action parameters:
offset: 2092
uri: file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
text:
```scala

object JuegoPiedraPapelTijera {
    // Con qué datos voy a trabajar?
    val PIEDRA = 0
    val PAPEL = 1
    val TIJERA = 2

    val OPCIONES = Array(PIEDRA, PAPEL, TIJERA)
    val NOMBRES_OPCIONES = Array("Piedra", "Papel", "Tijera")

    val GANADOR_HUMANO = 0
    val GANADOR_COMPUTADORA = 1
    val EMPATE = 2

    val MARCADOR_GLOBAL = Array(0, 0, 0) // [humano, computadora, empates]

    def bienvenida(): Unit = {
        println("¡Bienvenido al juego de Piedra, Papel o Tijera!")
    }

    def despedida(): Unit = {
        println("¡Gracias por jugar! ¡Hasta la próxima!")
    }

    def hayGanador(): Boolean = MARCADOR_GLOBAL(0) == 3 || MARCADOR_GLOBAL(1) == 3
  
    def actualizarMarcadorGlobal(ganadorMano: Int): Unit = MARCADOR_GLOBAL(ganadorMano) += 1

    def publicarLosResultadosFinales(): Unit = {
        println("\nRESULTADOS FINALES:")
        println(s"Jugador: ${MARCADOR_GLOBAL(0)}")
        println(s"Computadora: ${MARCADOR_GLOBAL(1)}")
        println(s"Empates: ${MARCADOR_GLOBAL(2)}")
        println(    
                    if (MARCADOR_GLOBAL(0) > MARCADOR_GLOBAL(1)) {
                        "¡Felicidades! ¡Has ganado el juego!"
                    } else {
                        "¡Eres un pringao! Te he ganado el juego!"
                    }
        )
    }

    def mostrarResultadoDeLaMano(eleccionJugador: Int, eleccionComputadora: Int, ganadorMano: Int): Unit = {
        println(s"\nHas elegido: ${NOMBRES_OPCIONES(eleccionJugador)}")
        println(s"Yo he elegido: ${NOMBRES_OPCIONES(eleccionComputadora)}")
        ganadorMano match {
            case GANADOR_HUMANO => println("¡Has ganado esta mano!")
            case GANADOR_COMPUTADORA => println("¡Eres mu malo! He ganado yo!!!")
            case EMPATE => println("¡Esta mano ha sido un empate!")
        }
    }

    def solicitarEleccionAlJugador(): Int = {
        var eleccionValida = false
        while (true) {
            println("\nElige tu opción: (0) Piedra, (1) Papel, (2) Tijera")
            val entrada = scala.io.StdIn.readLine()
            try {
                @@eleccionJugador = entrada.toInt
                if (OPCIONES.contains(eleccionJugador)) {
                    eleccionValida = true
                } else {
                    println("Opción no válida. Inténtalo de nuevo.")
                }
            } catch {
                case _: NumberFormatException => println("Entrada no válida. Por favor ingresa 0, 1 o 2.")
            }
        }
    }

    def main(args: Array[String]): Unit = {
        bienvenida()
        while (! hayGanador() ) {
            val eleccionJugador = solicitarEleccionAlJugador()
            val eleccionComputadora = solicitarEleccionAlComputador()
            val ganadorMano = determinarGanadorDeLaMano(eleccionJugador, eleccionComputadora)
            mostrarResultadoDeLaMano(eleccionJugador, eleccionComputadora, ganadorMano)
            actualizarMarcadorGlobal(ganadorMano)
        }
        publicarLosResultadosFinales()
        despedida()
    }

}

```



#### Error stacktrace:

```
scala.reflect.internal.Reporting.abort(Reporting.scala:70)
	scala.reflect.internal.Reporting.abort$(Reporting.scala:66)
	scala.reflect.internal.SymbolTable.abort(SymbolTable.scala:28)
	scala.reflect.internal.Types$ThisType.<init>(Types.scala:1394)
	scala.reflect.internal.Types$UniqueThisType.<init>(Types.scala:1414)
	scala.reflect.internal.Types$ThisType$.apply(Types.scala:1418)
	scala.meta.internal.pc.AutoImportsProvider$$anonfun$autoImports$3.applyOrElse(AutoImportsProvider.scala:75)
	scala.meta.internal.pc.AutoImportsProvider$$anonfun$autoImports$3.applyOrElse(AutoImportsProvider.scala:60)
	scala.collection.immutable.List.collect(List.scala:267)
	scala.meta.internal.pc.AutoImportsProvider.autoImports(AutoImportsProvider.scala:60)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$autoImports$1(ScalaPresentationCompiler.scala:384)
```
#### Short summary: 

scala.reflect.internal.FatalError: 
  ThisType(method mostrarResultadoDeLaMano) for sym which is not a class
     while compiling: file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
        during phase: globalPhase=<no phase>, enteringPhase=parser
     library version: version 2.13.12
    compiler version: version 2.13.12
  reconstructed args: -release:11 -classpath <WORKSPACE>/formacionScala/target/bloop-bsp-clients-classes/classes-Metals-OZrIsQ6qSmqYvgIZCqniFg==:<HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/sourcegraph/semanticdb-javac/0.11.1/semanticdb-javac-0.11.1.jar:<WORKSPACE>/formacionScala/target/classes:<HOME>/.m2/repository/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar:<HOME>/.m2/repository/org/scala-lang/scala-compiler/2.13.12/scala-compiler-2.13.12.jar:<HOME>/.m2/repository/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar:<HOME>/.m2/repository/io/github/java-diff-utils/java-diff-utils/4.12/java-diff-utils-4.12.jar:<HOME>/.m2/repository/org/jline/jline/3.22.0/jline-3.22.0.jar:<HOME>/.m2/repository/net/java/dev/jna/jna/5.13.0/jna-5.13.0.jar:<HOME>/.m2/repository/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar -Xplugin-require:semanticdb -Yrangepos -Ymacro-expand:discard -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: Ident(_CURSOR_eleccionJugador)
       tree position: line 59 of file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
            tree tpe: <error>
              symbol: value <error: <none>>
   symbol definition: val <error: <none>>: <error> (a TermSymbol)
      symbol package: <empty>
       symbol owners: value <error: <none>> -> method solicitarEleccionAlJugador -> object JuegoPiedraPapelTijera
           call site: <none> in <none>

== Source file context for tree position ==

    56             println("\nElige tu opción: (0) Piedra, (1) Papel, (2) Tijera")
    57             val entrada = scala.io.StdIn.readLine()
    58             try {
    59                 _CURSOR_eleccionJugador = entrada.toInt
    60                 if (OPCIONES.contains(eleccionJugador)) {
    61                     eleccionValida = true
    62                 } else {