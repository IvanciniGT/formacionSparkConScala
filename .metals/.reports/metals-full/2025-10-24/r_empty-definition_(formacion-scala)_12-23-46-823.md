error id: file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala:
file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -determinarGanadorDeLaMano.
	 -determinarGanadorDeLaMano#
	 -determinarGanadorDeLaMano().
	 -scala/Predef.determinarGanadorDeLaMano.
	 -scala/Predef.determinarGanadorDeLaMano#
	 -scala/Predef.determinarGanadorDeLaMano().
offset: 546
uri: file://<WORKSPACE>/formacionScala/src/main/scala/JuegoPiedraPapelTijera.scala
text:
```scala

object JuegoPiedraPapelTijera {
  
    def main(args: Array[String]): Unit = {
        bienvenida()
        while (! hayGanador() ) {
        // mientras no haya un ganador (manos ganadas =3)
            // Preguntar al jugador su elección
            val eleccionJugador = solicitarEleccionAlJugador()
            // La computadora que piense el suyo al azar
            val eleccionComputadora = SolicitarEleccionAlComputador()
            // Determinar el ganador de la mano en base a las elecciones
            val ganador = determinarGanado@@rDeLaMano(eleccionJugador, )
            // Y mostrarlo en pantalla
            // Anotar el resultado de la mano en algún sitio
        }
        // Publicar los resultados
        // despedida
    }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 