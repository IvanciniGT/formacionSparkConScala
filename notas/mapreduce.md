
# Map Reduce

Es un modelo de programación (una forma de escribir programas) para el procesamiento de grandes volúmenes de datos en paralelo y de forma distribuida. Esto lo creo Google en 2004 para manejar sus grandes volúmenes de datos.

En qué consiste? Buscar una forma alternativa de resolver problemas tradicionales al procesamiento de datos, que permita aprovechar las ventajas del procesamiento en paralelo y distribuido.

> Imaginad que soy Tweeter (X).. y me llegan tweets... montones de tweets.

A esos tweets, les quiero hacer muchos procesamientos:
- Extraer los hashtags -> Montar el trending topic
- Extraer los usuarios mencionados -> Enviar notificaciones

Esas tareas se hacen en paralelo y de forma asíncrona con respecto a la publicación de los tweets en el feed del usuario.

                             Kafka
USUARIOS ----> tweets -----> CAJON <---- Programa que publica los tweets en los feeds
                                   <---- Programa que extrae hashtags -> trending topic
                                   <---- Programa que extrae usuarios mencionados -> notificaciones


Kafka es un sistema de mensajería, como activaMQ, RabbitMQ, WhatsApp, etc.

Whatsapp es un sistema de mensajería que permite enviar mensajes entre humanos. 
Kafka es un sistema de mensajería que permite enviar mensajes entre aplicaciones.

> Queremos montar el programa que extrae los hashtags de los tweets 

Origen: Lista de tweets (en una ventana de tiempo (1 minuto), en la siguiente ventana de tiempo llegará otra lista de tweets)
[
"En la playa con mis amig@s #veranito#GoodVibes#FriendForever",
"En casa con mi perrito #DogLover#PetLover#GoodVibes",
"Preparando la oposición #MierdaVibes#Estudio#QuieroSerFuncionaria"
"1,2,3 nos vamos otra vez! #MasVerano#GoodVibes #PartyAllNight"
]

El trabajo es pasar de esta lista de tweets a una lista de hashtags:
- Contando ocurrencias
- Eliminando hashtags que contengan palabras indeseables (Caca, culo, Pedo, Pis, Mierda, etc)
- Normalizar los hastags (todo en minúsculas)
[
    [ "#veranito", 1 ],
    [ "#goodvibes", 2 ],
    [ "#friendforever", 1 ],
    [ "#doglover", 1 ],
    [ "#petlover", 1 ],
    ~~[ "#mierdavibes", 1 ],~~
    [ "#estudio", 1 ],
    [ "#quieroserfuncionaria", 1 ]
]

Posteriormente habrá otro programa... que recoja estos resultados.. a lo largo de una ventana de tiempo mayor (1 hora), y los agregue para obtener el trending topic de la hora.
[
    [ "#veranito", 1500 ],
    [ "#goodvibes", 3200 ],
    [ "#friendforever", 800 ],
    [ "#doglover", 900 ],
    [ "#petlover", 700 ],
    [ "#estudio", 400 ],
    [ "#quieroserfuncionaria", 300 ]
]

Quiero pasar de A (una lista de tweets) a B (una lista de hashtags con sus ocurrencias).

Cuántas líneas de código necesito para hacer ese trabajo? < 100 líneas? Si escribo código imperativo es poco probable.
Cuántos tweets soy capaz de procesar en mi computadora? Millones de tweets? Una computadora tiene capacidad como para procesar millones de tweets cada minuto? Ni la computadora más gorda que exista en el mundo es capaz de procesar millones de tweets cada minuto.

Entonces... cómo lo hago? Aquí es donde entra el modelo de programación MapReduce, que además me permitirá resolver este problema con 1 sola línea de código.

En qué consiste MapReduce? Se basa en programación funcional. 
Y se basa en dos tipos de operaciones: Operaciones de tipo Map y operaciones de tipo Reduce.

    Colección de datos1 
            -> Operación de tipo map 
                    -> Colección de datos2 
                            -> Operación de tipo map 
                                    -> Colección de datos3 
                                            -> Operación de tipo map 
                                                    -> Colección de datos4 
                                                            -> Operación de tipo reduce 
                                                                    -> Resultado final

Dicho de otra forma, lo que voy a hacer es ir transformando poco a poco la colección de datos inicial, llegar al resultado final, mediante una serie de transformaciones intermedias.


> PASO 0: ORIGEN: Lista de tweets

    [
        "En la playa con mis amig@s #veranito#GoodVibes#FriendForever",
        "En casa con mi perrito #DogLover#PetLover#GoodVibes",
        "Preparando la oposición #MierdaVibes#Estudio#QuieroSerFuncionaria",
        "1,2,3 nos vamos otra vez! #MasVerano #GoodVibes #PartyAllNight"
    ]

    coleccionOriginal.map( funcionAñadirEspacioEnBlancoAntesDeAlmohadilla )

    def funcionAñadirEspacioEnBlancoAntesDeAlmohadilla( tweet ):
        return tweet.replace( "#", " #" )

> PASO 1: Sustituir el caracter "#" por un espacio en blanco + " #"

    [
        "En la playa con mis amig@s #veranito #GoodVibes #FriendForever",
        "En casa con mi perrito #DogLover #PetLover #GoodVibes",
        "Preparando la oposición #MierdaVibes #Estudio #QuieroSerFuncionaria",
        "1,2,3 nos vamos otra vez! #MasVerano #GoodVibes #PartyAllNight"
    ]

> PASO 2: Partimos cada tweet en sus "palabras": ESPACIO EN BLANCO, COMA, PUNTO, PUNTO Y COMA, ADMIRACION, PARENTESIS, CORCHETES...
Usamos también el carácter # para partir? NO

    ~~coleccionTweetsConEspaciosAntesDeAlmohadilla.map( funcionPartirTweetEnPalabras )~~
    coleccionTweetsConEspaciosAntesDeAlmohadilla.flatmap( funcionPartirTweetEnPalabras ) // Ya me pasa directamente a PASO 4

    def funcionPartirTweetEnPalabras( tweet ):
        return tweet.split( " .,-()[]!¡¿?=...." )

            vvvvvvvv
    [
        ["En", "la", "playa", "con", "mis", "amig@s", "#veranito", "#GoodVibes", "#FriendForever"],
        ["En", "casa", "con", "mi", "perrito", "#DogLover", "#PetLover", "#GoodVibes"],
        ["Preparando", "la", "oposición", "#MierdaVibes", "#Estudio", "#QuieroSerFuncionaria"],
        ["1", "2", "3", "nos", "vamos", "otra", "vez", "#MasVerano", "#GoodVibes", "#PartyAllNight"]
    ]

> PASO 3: Separa cada "palabra" en un elemento independiente de la lista

    coleccionTweetsPartidosEnPalabras.flatten()


    [ 
        "En",
        "la",
        "playa",
        "con",
        "mis",
        "amig@s",
        "#veranito",
        "#GoodVibes",
        "#FriendForever"
        "En",
        "casa",
        "con",
        "mi",
        "perrito",
        "#DogLover",
        "#PetLover",
        "#GoodVibes",
        "Preparando",
        "la",
        "oposición",
        "#MierdaVibes",
        "#Estudio",
        "#QuieroSerFuncionaria",
        "1",
        "2",
        "3",
        "nos",
        "vamos",
        "otra",
        "vez",
        "#MasVerano",
        "#GoodVibes",
        "#PartyAllNight"
    ]

> PASO 4: Eliminar de cada lista Todo lo que no empiece por #

    coleccionDePalabras.filter( funcionFiltrarSoloHashtags )

    def funcionFiltrarSoloHashtags( palabra ):
        return palabra.startsWith( "#" )

    [
        "#veranito",
        "#GoodVibes",
        "#FriendForever",
        "#DogLover",
        "#PetLover",
        "#GoodVibes",
        "#MierdaVibes",
        "#Estudio",
        "#QuieroSerFuncionaria",
        "#MasVerano",
        "#GoodVibes",
        "#PartyAllNight"
    ]

> PASO 5: Quitar los #

    coleccionDeHashtagsConCuadraditos.map( funcionQuitarCuadradito )

    def funcionQuitarCuadradito( hashtag ):
        return hashtag[1:] // Desde la posición 1 hasta el final

    [
        "veranito",
        "GoodVibes",
        "FriendForever",
        "DogLover",
        "PetLover",
        "GoodVibes",
        "MierdaVibes",
        "Estudio",
        "QuieroSerFuncionaria",
        "MasVerano",
        "GoodVibes",
        "PartyAllNight"
    ]

> PASO 6: Pasar todo a minúsculas

    coleccionDeHashtagsSinCuadraditos.map( funcionPasarAMinusculas )

    def funcionPasarAMinusculas( hashtag ):
        return hashtag.lower()

    [
        "veranito",
        "goodvibes",
        "friendforever",
        "doglover",
        "petlover",
        "goodvibes",
        "mierdavibes",
        "estudio",
        "quieroserfuncionaria",
        "masverano",
        "goodvibes",
        "partyallnight"
    ]

> PASO 7: Eliminar los hashtags indeseables que contengan palabras malsonantes

    coleccionDeHashtagsMinusculas.filter( funcionFiltrarHashtagsIndeseables )

    def funcionFiltrarHashtagsIndeseables( hashtag ):
        listaPalabrasIndeseables = [ "caca", "culo", "mierda" ]
        for palabra in listaPalabrasIndeseables:
            if palabra in hashtag:
                return False
        return True

    [
        "veranito",
        "goodvibes",
        "friendforever",
        "doglover",
        "petlover",
        "goodvibes",
        "estudio",
        "quieroserfuncionaria",
        "masverano",
        "goodvibes",
        "partyallnight"
    ]

> PASO 8: Colocar un 1 al lado de cada hashtag

    coleccionDeHashtagsFiltrados.map( funcionAñadirOcurrencia )

    def funcionAñadirOcurrencia( hashtag ):
        return [ hashtag, 1 ]

    [
        [ "veranito", 1 ],
        [ "goodvibes", 1 ],
        [ "friendforever", 1 ],
        [ "doglover", 1 ],
        [ "petlover", 1 ],
        [ "goodvibes", 1 ],
        [ "estudio", 1 ],
        [ "quieroserfuncionaria", 1 ],
        [ "masverano", 1 ],
        [ "goodvibes", 1 ],
        [ "partyallnight", 1 ]
    ]

> PASO 9: Agrupar por hashtag y sumar las ocurrencias

    coleccionDeHashtagsConOcurrencias.groupBy( funcionQueDeterminaLaClave, funcionQueDeterminaElValorQueQuedaAsociadoALaClave )

    def funcionQueDeterminaLaClave( dosPartes ):
        return elemento[0] 


        [ "goodvibes", 1 ], \
        [ "goodvibes", 1 ], / [ "goodvibes", 2 ] \
        [ "goodvibes", 1 ], ----------------------  [ "goodvibes", 3 ]

    def funcionQueDeterminaElValorQueQuedaAsociadoALaClave( valor1, valor2 ):
        return valor1 + valor2


    [
        [ "veranito", 1 ],
        [ "goodvibes", 3 ],
        [ "friendforever", 1 ],
        [ "doglover", 1 ],
        [ "petlover", 1 ],
        [ "estudio", 1 ],
        [ "quieroserfuncionaria", 1 ],
        [ "masverano", 1 ],
        [ "partyallnight", 1 ]
    ]

            vvvvvvvv

> PASO 10: DESTINO: Lista de hashtags filtrados y normalizados con sus ocurrencias

    recopilarTodoEnUnaLista( coleccionFinalDeHashtagsConOcurrencias )

[
    [ "veranito", 1 ],
    [ "goodvibes", 2 ],
    [ "friendforever", 1 ],
    [ "doglover", 1 ],
    [ "petlover", 1 ],
    ~~[ "mierdavibes", 1 ],~~
    [ "estudio", 1 ],
    [ "quieroserfuncionaria", 1 ],
    [ "masverano", 1 ],
    [ "partyallnight", 1 ]
]

Se os habría ocurrido alguna vez, plantear así el problema? NO
Lo primero va a ser cambiar la forma de pensar para resolver el problema. 
MAP REDUCE Me da una serie de operaciones.... y tengo que ajustarme a ellas para resolver el problema.
Y eso implica empezar a pensar de esta forma que os acabo de contar... Lo cual ya es un problema en sí mismo.

# Que ofrece Map Reduce:

Un montón de operaciones de tipo map y reduce, que me permiten transformar colecciones de datos.
Un pipeline de operaciones MapReduce, siempre tiene de 1 a N operaciones de tipo map, y 1 operación de tipo reduce al final.

No todas las colecciones de datos soportan todas las operaciones de tipo map y reduce.
Solo algunas.

## Operaciones de tipo MAP

Una operación que me permite transformar una colección de datos que soporte operaciones de tipo map/reduce, en otra colección de datos que también soporte operaciones de tipo map/reduce.

Hay muchas operaciones de tipo map:
- map(COLECCION, FUNCION DE MAPEO) -> COLECCION (es la que creamos nosotros en python)
  Nos permite transformar los datos, mediante una función de transformación que nosotros creamos. 
- flatten(COLECCION DE COLECCIONES) -> COLECCION UNIFICADA
  Nos permite transformar una colección de colecciones en una única colección.
- flatmap(COLECCION, FUNCION DE MAPEO) -> COLECCION UNIFICADA
  Permite hacer primero un map y luego un flatten en una sola operación.
- filter(COLECCION, FUNCION DE FILTRADO) -> COLECCION FILTRADA
  Permite filtrar los elementos de una colección, manteniendo solo aquellos para los cuales la función de filtrado devuelve true.
- groupBy(COLECCION, FUNCION DE AGRUPACION) -> COLECCION DE COLECCIONES
  Permite agrupar los elementos de una colección en función del resultado de la función de agrupación.

^^^ Como estas hay unas 50 operaciones de tipo map. Que necesitamos aprender! Van a ser las piezas que tengo para resolver los problemas.

## Operaciones de tipo REDUCE
- toList(COLECCION) -> LISTA
  Una operación que me permite transformar una colección de datos que soporte operaciones de tipo map/reduce, en una lista.


Una operación que me permite transformar una colección de datos que soporte operaciones de tipo map/reduce, en un resultado que no soporte operaciones de tipo map/reduce.

VAYA LOCURA !

Spark lo que me da son esas 50 operaciones de tipo map, y otras 50 operaciones de tipo reduce.
Y yo tengo que aprender a combinarlas, para resolver los problemas que me vayan surgiendo.

Y además, spark lo que me permite es (sin que yo me entere) ejecutar esas operaciones en paralelo y de forma distribuida, aprovechando los recursos de un cluster de computadoras, es decir, que ese código no se ejecute solo en mi computadora, sino en un conjunto de computadoras trabajando juntas. De esto ni me entero... a priori! ... porque necesitaré tenerlo en cuenta por las implicaciones que tiene.... pero ese será un tercer problema!

```scala

val coleccionOriginal = sc.parallelize( Array(
    "En la playa con mis amig@s #veranito#GoodVibes#FriendForever",
    "En casa con mi perrito #DogLover#PetLover#GoodVibes",
    "Preparando la oposición #MierdaVibes#Estudio#QuieroSerFuncionaria",
    "1,2,3 nos vamos otra vez! #MasVerano #GoodVibes #PartyAllNight"
) )

val resultado = collectionOriginal
    .map( tweet => tweet.replace( "#", " #" ) )
    .flatMap( tweet => tweet.split( " .,-()[]!¡¿?=...." ) )
    .filter( palabra => palabra.startsWith( "#" ) )
    .map( hashtag => hashtag.substring( 1 ) )
    .map( hashtag => hashtag.toLowerCase() )
    .filter( hashtag => {
        val palabrasIndeseables = Array( "caca", "culo", "mierda" )
        !palabrasIndeseables.exists( palabra => hashtag.contains( palabra ) )
    } )
    .map( hashtag => ( hashtag, 1 ) )
    .reduceByKey( ( valor1, valor2 ) => valor1 + valor2 )
    .collect()
```

Viernes: Repaso rápido de Scala (lo justo para entender Spark : ~20% de lo que es Scala)
Esto es lo que estaremos trabajando: Lunes/Martes Spark MAP-REDUCE... y en paralelo irán saliendo algunas cositas de Scala que iremos viendo sobre la marcha.

Miércoles.
- SparkSQL?
- Intentando entender cómo se lleva todo esto a un entorno de producción real.

# Con Spark CORE, que es lo que trabajaremos Lunes y Martes, y que es la base de Spark Vamos a flipar!

Es complicao... muy complicao!
Tan complicao, que los de Spark tuvieron que ahcer una versión / reescritura completa de Spark, de forma que usando una sintaxis SQL podamos hacer estas operaciones... O al menos algunas de ellas.
Y cuando trabaje con datos MUY MUY MUY CUADRICULADOS... y quiera hacer transformaciones MUY MUY MUY SENCILAS... usaré SparkSQL para hacerlas, aprovechando esa capacidad de Spark de ejecutar en paralelo y de forma distribuida, pero sin necesidad de escribir código MapReduce.
Ese código lo escribirá por mi SparkSQL... yo solo escribiré SQL.
Pero claro... SQL es muy limitado... y no podré hacer todo lo que quiero.
En SQL puedo coger un texto, partirlo en troz, quitar los trozos que no me interesan, normalizar los trozos que me interesan, OLVIDADO.
Pero a lo mejor, una vez limpito todo eso.. el agrupar los datos y contar cuantos hay... SI... y es más fácil.

En general el 70%-80% de las veces que use Spark, usaré SparkSQL.
Pero de eso... tengo muy poco que enseñaros... porque es SQL... y SQL ya lo sabéis.
Para esto, lo único que tengo que enseñaros son 4 funciones de SparkSQL que me permitan ejecutar SQL sobre colecciones de datos distribuidas en Spark.