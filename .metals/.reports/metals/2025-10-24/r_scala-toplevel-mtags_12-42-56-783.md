error id: file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala:[3103..3106) in Input.VirtualFile("file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala", "
import scala.io.StdIn.readLine
import scala.util.Random

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
        var valorElegido: Int = -1
        do{
            println("\nElige tu opción: (0) Piedra, (1) Papel, (2) Tijera")
            val entrada = readLine().toLowerCase.trim // El equivalente en python a un input()
            if (entrada == "0"  || entrada.startsWith("pi") || entrada == "piedra") {
                valorElegido = PIEDRA
            } else if (entrada == "1"  || entrada.startsWith("pa") || entrada == "papel") {
                valorElegido = PAPEL
            } else if (entrada == "2"  || entrada.startsWith("ti") || entrada == "tijera") {
                valorElegido = TIJERA
            } else {
                println("Entrada no válida. Por favor, intenta de nuevo.")
            }
        } while (valorElegido == -1) 
        return valorElegido
    }

    //def solicitarEleccionAlComputador(): Int = scala.util.Random.nextInt(OPCIONES.length)
    // Igual que en python, muchas funciones de scala vienen dentro de Librerías: scala.util.Random
    // Podemos importar esas librerías si queremos usar sus funciones sin el prefijo scala.util.Random
    def solicitarEleccionAlComputador(): Int = Random.nextInt(OPCIONES.length)

    def 

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
")
file://<WORKSPACE>/file:<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala:81: error: expected identifier; obtained def
    def main(args: Array[String]): Unit = {
    ^
#### Short summary: 

expected identifier; obtained def