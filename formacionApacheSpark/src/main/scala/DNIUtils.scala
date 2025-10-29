import ValidezDNI.ValidezDNI


object ValidezDNI extends Enumeration { // Nuevo tipo de datos... más que un booleano. El booleano solo admite dos valores (true/false).
    // Aquí quiero más de dos valores: correcto, incorrecto por letra que no coincide, incorrecto por formato no reconocido
    type ValidezDNI = Value
    val CORRECTO, INCORRECTO_LETRA_NO_COINCIDE, INCORRECTO_FORMATO_NO_RECONOCIDO = Value
}
trait DNI { // (1) Define un ESQUEMA de un tipo de dato nuevo... lo que tendrán los datos de ese tipo DNI
    val textoOriginal: String;
    val validez: ValidezDNI;
    def esValido(): Boolean = validez == ValidezDNI.CORRECTO
}

case class OpcionesDeFormateoDNI( // Clase para definir opciones de formateo. Es un Case Class.
                                  // Los case class son clases especiales que me permiten definir clases de datos simples
                                  // Tipos de datos que solo tienen atributos y no tienen lógica compleja
    puntos: Boolean = false,
    letraEnMayuscula: Boolean = true,
    separador: String = "",
    cerosDelante: Boolean = true
)

class DNIValido(override val textoOriginal:String, val letra:Char, val numero:Int) extends DNI { // Estoy definiendo el tipo de datos DNI BUENO, que tiene lo que tiene que tener un DNI
                                                                 // Y más cosas específicas de un DNI válido
    override val validez: ValidezDNI = ValidezDNI.CORRECTO

    def formatear(opciones:OpcionesDeFormateoDNI = OpcionesDeFormateoDNI()):String = {
        // La parte de la letra
        val letraFormateada = if (opciones.letraEnMayuscula) letra.toUpper else letra.toLower
        val parteLetra = opciones.separador + letraFormateada
        // La parte del número
        var numeroComoTexto = numero.toString
        if (opciones.cerosDelante) {
            numeroComoTexto = ("00000000" + numeroComoTexto).takeRight(8)
        }
        if (opciones.puntos) {
            // Poner puntos cada tres cifras empezando por la derecha        123     23456     1234567
            //                                                                       23.456    1.234.567
            val longitud = numeroComoTexto.length
            if (longitud > 6) {
                numeroComoTexto = numeroComoTexto.substring(0, longitud - 6) + "." +
                    numeroComoTexto.substring(longitud - 6, longitud - 3) + "." +
                    numeroComoTexto.substring(longitud - 3, longitud)
            } else if (longitud > 3) {
                numeroComoTexto=numeroComoTexto.substring(0, longitud - 3) + "." +
                    numeroComoTexto.substring(longitud - 3, longitud)
            }
        }

        // Devuelvo resultado
        return numeroComoTexto+parteLetra;
    }
}

class DNIInvalido(override val textoOriginal:String, override val validez: ValidezDNI) extends DNI { // Estoy definiendo el tipo de datos DNI MALO
                                                                                                     // Que no tiene cosas específicas con respecto a lo que ofrece un DNI
}

object DNI {            // Aquí definimos un Singleton... Una clase de la que solo hay una instancia a la que puedo referirme directamente por su nombre
    // Y esta es la que me permite crear objetos de tipo DNI (1)

    val LETRAS_VALIDAS: String = "TRWAGMYFPDXBNJZSQVHLCKE"

    def crearDNI(texto:String): DNI ={
        // Lo primero voy a ver si huele a DNI... si respeta el formato general que debe tener un DNI:
        // Usamos regex
        val regexDNI = """^(([0-9]{1,2}([.][0-9]{3}){2})|([0-9]{1,3}[.][0-9]{3})|([0-9]{1,8}))[ -]?[A-Za-z]$""".r // Eso es un patrón de regex : '.r'
        if (regexDNI.matches(texto) ) {
            // Si cumple, voy a quitar todos los caracteres extra especiales que podría haber:
            val textoLimpio = texto.replace(".","").replace(" ","").replace("-","").toUpperCase()
            val letra = textoLimpio.charAt(textoLimpio.length - 1).toUpper
            val numeroTexto = textoLimpio.substring(0, textoLimpio.length - 1)
            val numero = numeroTexto.toInt
            // Ahora calculo la letra que debería corresponder a ese número:
            val letraQueCorrespondeAlNumero = LETRAS_VALIDAS.charAt( numero % 23 )
            if (letra == letraQueCorrespondeAlNumero) {
                new DNIValido(texto, letra, numero)
            } else {
                new DNIInvalido(texto, ValidezDNI.INCORRECTO_LETRA_NO_COINCIDE)
            }
        } else {
            new DNIInvalido(texto, ValidezDNI.INCORRECTO_FORMATO_NO_RECONOCIDO)
        }
    }

    def esValido(dni:String): Boolean = crearDNI(dni).esValido()
    def esInvalido(dni:String): Boolean = !esValido(dni)

}
