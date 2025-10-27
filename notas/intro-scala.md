

# object - POO

# class = Tipo de datos

```python

class Persona:

    def __init__(self, nombre, edad):
        self.nombre = nombre
        self.edad = edad

    def saludar(self):
        return f"Hola, mi nombre es {self.nombre} y tengo {self.edad} años."
```

Eso es lo que llamamos un modelo / entidad que es un tipo de clase.

Pero hay otros tipos de clases:
- Servicios, Controladores, Repositorios, Managers, Helpers, Utils, etc.

Ahí es don de python empieza a quedarse corto.. No tiene tanta potencia en POO como otros lenguajes.

En otros lenguajes existe enl concepto de clase estática, abstracta, interfaz, etc.

Hay una forma de crear clases, para asegurarnos que de esas clase SOLO PUEDE HABER UNA INSTANCIA: Patrón Singleton:
- Gestor de Conexiones a BBDD

En JAVA:

```java
public class GestorConexionesDB {
    private static volatile GestorConexionesDB instancia;
    private GestorConexionesDB() {
        // Inicialización de la conexión
    }
    public ConexionDB getConexion() {
        ConexionDB conexion = // Lógica para obtener la conexión
        return conexion;
    }
    public static GestorConexionesDB getInstancia() {
        if (instancia == null) {
            synchronized (ConexionDB.class) {
                if (instancia == null) {
                    instancia = new GestorConexionesDB();
                }
            }
        }
        return instancia;
    }
}

// En otros sitios:
ConexionDB conexion = GestorConexionesDB.getInstancia().getConexion();
```

Ese código en Scala:

```scala
object GestorConexionesDB {
  def getConexion(): ConexionDB = {
    // Lógica para obtener la conexión
  }
}

// En otros sitios:
val conexion = GestorConexionesDB.getConexion()
```
Y Aquí empiezan las dificultades de aprender scala.
Java, aunque es verbose, es explicito.
Scala es muy conciso, pero a veces es demasiado conciso y cuesta entender lo que está implica poner una línea de código.
Un Object en Scala es una clase singleton. No puedes crear muchas instancias de de ella... de hecho, scala al arrancar el programa, ya me crea el solito esa instancia... y me permite acceder a ella directamente por el nombre de la clase, en lugar de generar un método estático que me devuelva la instancia, del tipo getInstancia().


# Un object:

Es una clase de la que solo hay una instancia, que se genera en automático, a la que se accede directamente por el nombre de la clase.

Más adelante, cuando nos metamos en POO, veremos que en muchos casos, y un patrón muy habitual en scala es juntar una clase con un object del mismo nombre.
El object se llama companion object (objeto compañero) y eso tendrá su gracia.

Por ahora solo estamos usando eso, porque que SCALA obliga a ello. Me obliga a tener un Object, que dentro defina una función llamada main, que es el punto de entrada del programa.

Esto no es como python.. que pongo código a pelo y se ejecuta.
Aquí defino ese código que quiero que se ejecute en una función main, que debe ir dentro de un object.


En python, existen las expresiones lambda? SI.. os las enseñé ayer.
Pero la sintaxis es cutre y está ultra limitada. Una lambda solo puede ser una expresión simple, no puede tener varias líneas de código.
Si lo que quiero es poner un texto en mayúsculas, me vale.. Si quiero procesar la validez de un dni, ya no me vale.

# Operadores lógicos con o sin corto circuito

condicion1 && condicion2  // AND con corto circuito
condicion1 & condicion2   // AND sin corto circuito
condicion1 || condicion2  // OR con corto circuito
condicion1 | condicion2   // OR sin corto circuito
condicion1 ^ condicion2   // XOR sin corto circuito
!condicion1               // NOT


La diferencia entre && y & es que el primero es de corto circuito. Es decir, si la primera condicion es falsa, no se evalua la segunda condición, porque ya sabemos que el resultado final va a ser falso.
Con & se evalúan ambas condiciones siempre, independientemente del resultado de la primera.

El or en corto circuito (||) si la primera condición es verdadera, no evalúa la segunda condición, porque ya sabemos que el resultado final va a ser verdadero.

El XOR es un o exclusivo. Es verdadero si una de las dos condiciones es verdadera, pero no ambas.
Elk OR es verdadero si al menos una de las dos condiciones es verdadera, pudiendo serlo ambas.

> Ejemplo

if (mandarAKafka(datos) & cargarEnOracle(datos))
   println("Todo OK")
else
   println("Error en el proceso")
   // Rollback si es necesario



# Option.

````scala 
// Long y Latitud de una dirección
def obtenerDatosGeoposicionamiento(direccion: String): = Array[Double] = // Que tal esta?
// Es un poco ambiguo.. Cuantos datos tendré dentro del array? Y eso donde lo pone?
def obtenerDatosGeoposicionamiento(direccion: String): (Double, Double) = 
// Mejor, pero sigue siendo ambiguo. Te devuelvo una tupla con dos doubles.. 
// Sigue habiendo un problema .. y si no encuentro la dirección? Qué devuelvo? 
// - OPCION 1: null
// - OPCION 2: Una tupla con dos valores especiales, tipo -9999.99, -9999.99

// Y para dejarlo claro, en Scala, igual que en Java,m existe un tipo de dato especial: Option[T]
def obtenerDatosGeoposicionamiento(direccion: String): Option[(Double, Double)] = 
    // La función devuelve una tupla de 2 doubles, o nada.
    // Siempre me viene una CAJA (=OPTION) que puede venir con regalo dentro (TUPLA) o vacía (NONE)
    // A la caja luego le pregunto si viene con relleno de chocolate o está vacía:

// En otro sitio:
val resultado: Option[(Double, Double)] = obtenerDatosGeoposicionamiento("Calle Falsa 123")
resultado match {
   case Some((latitud, longitud)) => println(s"Coordenadas: $latitud, $longitud") // Si hay valor
   case None => println("Dirección no encontrada") // Si no hay valor
} // Esta es una sintaxis.. parecida a un switch case de otros lenguajes.

// Otra forma de hacerlo con programación imperativa:
if (resultado.isDefined) {
   val (latitud, longitud) = resultado.get
    println(s"Coordenadas: $latitud, $longitud")    
} else {
    println("Dirección no encontrada")
}

```

Geoposicionar, podría ser dar la latitud y longitud de un teléfono / IP.
Pero podría provechar a devolver más cosas?
- lat/long
- Ciudad
   - Como texto / Nombre
   - Código INE
- País
    - Nombre
    - Código ISO
- Código postal
- Zona horaria


def obtenerDatosGeoposicionamiento(telefono: String): Option[DatosGeoposicionamiento] =


Qué devuelvo ahi?
(Double, Double, String, String, String, String, String, String)... podría... pero que cojones es cada cosa?

Aquí es donde nos echa una mano la POO.
Quiero mi propio tipo de dato:

```scala

case class DatosGeoposicionamiento( // Un tipo de clase muy especial.. que solo contiene datos: Case Class
    latitud: Double, 
    longitud: Double, 
    ciudad: String, 
    codigoINE: String, 
    pais: String, 
    codigoISO: String, 
    codigoPostal: String, 
    zonaHoraria: String
)
```

En python:
```python

class DatosGeoposicionamiento:
    def __init__(self, latitud, longitud, ciudad, codigoINE, pais, codigoISO, codigoPostal, zonaHoraria):
        self.latitud = latitud
        self.longitud = longitud
        self.ciudad = ciudad
        self.codigoINE = codigoINE
        self.pais = pais
        self.codigoISO = codigoISO
        self.codigoPostal = codigoPostal
        self.zonaHoraria = zonaHoraria
    
```

En java:

```java
public class DatosGeoposicionamiento {
    private double latitud;
    private double longitud;
    private String ciudad;
    private String codigoINE;
    private String pais;
    private String codigoISO;
    private String codigoPostal;
    private String zonaHoraria;

    // Constructor
    public DatosGeoposicionamiento(double latitud, double longitud, String ciudad, String codigoINE,
                                  String pais, String codigoISO, String codigoPostal, String zonaHoraria) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.ciudad = ciudad;
        this.codigoINE = codigoINE;
        this.pais = pais;
        this.codigoISO = codigoISO;
        this.codigoPostal = codigoPostal;
        this.zonaHoraria = zonaHoraria;
    }
    // Getters y Setters
    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }
    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getCodigoINE() { return codigoINE; }
    public void setCodigoINE(String codigoINE) { this.codigoINE = codigoINE; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getCodigoISO() { return codigoISO; }
    public void setCodigoISO(String codigoISO) { this.codigoISO = codigoISO; }
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
    public String getZonaHoraria() { return zonaHoraria; }
    public void setZonaHoraria(String zonaHoraria) { this.zonaHoraria =
    zonaHoraria; 
}


# Condicionales 

Habitualmente en los lenguajes de programación tenemos los if como statements (instrucciones):

    val edad = 17
    if( edad >= 18 ){
        // ORDEN / Statement
        println("Eres mayor de edad")
    } else {
        // Otra ORDEN / Statement
        println("Eres menor de edad")
    }

En Scala, tenemos los if como expresión... también hay cositas asi en otros lenguajes... pero muy limitadas.

    // Expresión??? Un trozo de código que devuelve un valor

    val edad = 17;
    val resultado = if(edad >= 18){
        // Expresión 1
        "Eres mayor de edad"
    } else {
        // Expresión 2
        "Eres menor de edad"
    }
    println(s"Resultado: $resultado")