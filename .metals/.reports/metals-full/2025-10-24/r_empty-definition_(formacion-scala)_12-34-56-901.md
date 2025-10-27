error id: file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala:scala/Unit#
file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol scala/Unit#
empty definition using fallback
non-local guesses:

offset: 1865
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

    def main(args: Array[String]): Unit@@ = {
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


#### Short summary: 

empty definition using pc, found symbol in pc: 