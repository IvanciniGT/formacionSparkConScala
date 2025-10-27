file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
### java.lang.NullPointerException: Cannot invoke "scala.reflect.internal.Types$Type.typeSymbol()" because "tp" is null

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.13.12
Classpath:
<WORKSPACE>/formacionScala/target/bloop-bsp-clients-classes/classes-Metals-OZrIsQ6qSmqYvgIZCqniFg== [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/sourcegraph/semanticdb-javac/0.11.1/semanticdb-javac-0.11.1.jar [exists ], <WORKSPACE>/formacionScala/target/classes [exists ], <HOME>/.m2/repository/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.m2/repository/org/scala-lang/scala-compiler/2.13.12/scala-compiler-2.13.12.jar [exists ], <HOME>/.m2/repository/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.m2/repository/io/github/java-diff-utils/java-diff-utils/4.12/java-diff-utils-4.12.jar [exists ], <HOME>/.m2/repository/org/jline/jline/3.22.0/jline-3.22.0.jar [exists ], <HOME>/.m2/repository/net/java/dev/jna/jna/5.13.0/jna-5.13.0.jar [exists ], <HOME>/.m2/repository/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ]
Options:
-release 11 -Yrangepos -Xplugin-require:semanticdb


action parameters:
offset: 811
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

    def publicarLosResultadosFinales()@@

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
scala.reflect.internal.Definitions$DefinitionsClass.isByNameParamType(Definitions.scala:428)
	scala.reflect.internal.TreeInfo.isStableIdent(TreeInfo.scala:140)
	scala.reflect.internal.TreeInfo.isStableIdentifier(TreeInfo.scala:113)
	scala.reflect.internal.TreeInfo.isPath(TreeInfo.scala:102)
	scala.tools.nsc.interactive.Global.stabilizedType(Global.scala:974)
	scala.tools.nsc.interactive.Global.typedTreeAt(Global.scala:822)
	scala.meta.internal.pc.SignatureHelpProvider.signatureHelp(SignatureHelpProvider.scala:23)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$signatureHelp$1(ScalaPresentationCompiler.scala:417)
```
#### Short summary: 

java.lang.NullPointerException: Cannot invoke "scala.reflect.internal.Types$Type.typeSymbol()" because "tp" is null