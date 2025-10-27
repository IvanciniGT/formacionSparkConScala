error id: file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala:[50..53) in Input.VirtualFile("file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala", "
object JuegoPiedraPapelTijera {

    def 
  
    def main(args: Array[String]): Unit = {
        bienvenida()
        while (! hayGanador() ) {
            val eleccionJugador = solicitarEleccionAlJugador()
            val eleccionComputadora = solicitarEleccionAlComputador()
            val ganador = determinarGanadorDeLaMano(eleccionJugador, eleccionComputadora)
            mostrarResultadoDeLaMano(eleccionJugador, eleccionComputadora, ganador)
            actualizarMarcadorGlobal(ganador)
        }
        publicarLosResultadosFinales()
        despedida()
    }

}
")
file://<WORKSPACE>/file:<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala:6: error: expected identifier; obtained def
    def main(args: Array[String]): Unit = {
    ^
#### Short summary: 

expected identifier; obtained def