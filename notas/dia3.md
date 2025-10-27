
# Volvemos a map/reduce

Habíamos dicho que un algoritmo planteado con un modelo map/reduce se compone de :
- Una colección de datos que admita operaciones map/reduce (map, filter, reduce, etc)
- Al que le vamos aplicando n operaciones de tipo map (map, filter, etc)
- Y al final una operación reduce (reduce, sum, count, etc)

Por qué eso:
Vamos a volver a la definición de funciones de tipo map y reduce:

# Funciones de tipo map:

Una función que puedo aplicar sobre una colección de datos que soporte operaciones map/reduce, y que me devuelve otra colección de datos que también soporta operaciones map/reduce:
- map
- filter
- flatMap
- etc

# Funciones de tipo reduce:

Una función que puedo aplicar sobre una colección de datos que soporte operaciones map/reduce, y que me devuelve un valor que no soporta operaciones map/reduce:
- reduce
- sum
- count
- etc

La gracia de este modelo de programación es que es fácilmente paralelizable y distribuible. Y además, que voy a poder obtener un buen rendimiento cuando trabajo con grandes volúmenes de datos.... pero vamos a pensar un poquito en ello más despacio:


(1,2,3) -> map(x2) -> (2,4,6) -> map(+2) -> (4,6,8) -> map(/3) -> (1.33,2,2.66) -> sum -> 6 
  ^^^                   ^^^                   ^^^                   ^^^^                 ^^^
Lista                  Lista                 Lista                  Lista               Número (evidentemente no soporta más map/reduce)
que soporta            que soporta           que soporta            que soporta
map/reduce             map/reduce            map/reduce             map/reduce 


Vamos a hacer esta transformación con un algoritmo IMPERATIVO (con bucles for):

```scala
val datos = Array(1,2,3)
var resultadoFinal = 0.0
for (x <- datos) {
    resultadoFinal += (x*2 + 2) / 3.0
}

println(s"El resultado final es: $resultadoFinal")
// 1 bucle
```

Y ahora vamos a hacer la misma transformación con un algoritmo MAP/REDUCE:

```scala
val datos = Array(1,2,3)
val resultadoFinal = datos
                        .map(x => x * 2)        // Multiplico cada elemento por 2
                        .map(x => x + 2)        // A cada elemento le sumo 2
                        .map(x => x / 3.0)      // A cada elemento lo divido entre 3.0
                        .sum                    // Sumo todos los elementos

// Cuantos bucles hay aqui? Ninguno explicito... pero coño.. bucles se aplicarán internamente!
// 4 bucles (uno por cada operación map y uno para la suma)
// Y Esto va a ofrecer mejor rendimiento que el imperativo, donde solo había un bucle? NI CERCA !
println(s"El resultado final es: $resultadoFinal")
```

Esto no funciona asi.. ni parecido. Así es como los humanos pensamos que esto se ejecuta... nos es fácil pensar de esta forma.
Que cuando aplico sobre (1.2.3).map(x=> x*2) se crea una nueva colección (2,4,6),
 y luego sobre (2,4,6).map(x=> x+2) se crea otra nueva colección (4,6,8),
 y luego sobre (4,6,8).map(x=> x/3.0) se crea otra nueva colección (1.33,2,2.66)
 y luego sobre esa colección se aplica sum() y se obtiene 6.

Así explicado, mi cerebro es capaz de entenderlo bien. Pero no es así como funciona internamente.
Eso implicaría hacer 4 bucles = RUINA ENORME EN RENDIMIENTO, comparado con la solución imperativa, DONDE SOLO HAY UN BUCLE.

Y Aquí entra un concepto nuevo: LA EVALUACIÓN DIFERIDA (LAZY EVALUATION)

Las funciones map, son funciones que se evalúan en modo LAZY (perezoso=diferido).
Por contra las funciones reduce son funciones que se evalúan en modo EAGER (ansioso=inmediato).... y no solo eso...
En el modelo map reduce, provocan un efecto domino!

Una explicación más acertada de lo que hace nuestro programa mapReduce es la siguiente:

```scala
val datos = Array(1,2,3)
val datosMapeados = datos
                        .map(x => x * 2)   // Anota que hay que multiplicar por 2 cada elemento
                        .map(x => x + 2)   // Anota que hay que sumar 2 a cada elemento
                        .map(x => x / 3.0) // Anota que hay que dividir entre 3.0 cada elemento         (3)
                        .sum               // Suma. Y claro.. para sumar, necesito los datos que hay que sumar...
                                            // Y por ende, solicito esos datos (sum solicita los datos)
                                            // Y entonces se ejecuta el map (3), pero para poder aplicar ese map (3)
                                            // necesito los datos del map (2), así que se ejecuta el map (2), pero para poder aplicar ese map (2)
                                            // necesito los datos del map (1), así que se ejecuta el map (1), que ya tiene los datos originales... y puede ejecutarse
```
El resultado de la función map no es una Colección con el resultado de map... sino la colección original, con un postit pegao encima que dice: Multiplicar por 2.

Al final, esto da lugar al siguiente código:

```scala
val datos = Array(1,2,3)
val resultadoFinal = 0.0

for (x <- datos) {
    resultadoFinal += (((x * 2)) + 2) / 3.0
}
// Y este es el código al que da lugar lo de arriba realmente
// Como no es sino hasta el final que se calcula todo, al final, puedo meter todo eso (yo no... el motor interno de map-reduce) en un solo bucle... y quien dispara la creación de ese bucle es la función reduce (sum en este caso)
// Pero hasta que no se aplica la función reduce... no se ha hecho ningún cálculo.. solo pegar postits encima de la colección original
```

Lo que pasa es que a nivel conceptual, nos resulta más sencillo pensar en las transformaciones como si se fueran ejecutando una a una sobre toda la colección... pero no es así como funciona internamente.

Esto es la clave para entender el modelo map/reduce y por qué es tan eficiente cuando se trabaja con grandes volúmenes de datos.
Porque lo que se hace realmente es preparar un plan de ejecución que luego se aplica sobre los datos originales.
Pero... puedo coger los datos originales y partirlos en 10 trozos. Y mandar a 10 máquinas a que apliquen ese plan de ejecución sobre cada uno de esos trozos de datos.

Esto es lo que hace Spark.

En Scala, cuando hemos escrito:

```scala
datos: Array[Int] = Array(1, 2, 3)
val resultadoFinal = datos
                        .map(x => x * 2)        // Multiplico cada elemento por 2: (2,4,6)
```

Qué tipo de dato es resultadoFinal?  Un array de Int?
Enm ese código, las funciones map no se ejecutan en modo diferido (lazy) porque Array o List no soporta evaluación diferida (modo perezoso). Esos maps se van ejecutando según se van encontrando.No es necesario un reduce al final para que se ejecuten.

Y esto es algo de Scala.
Entonces, scala no soporta realmente un modo de trabajo map/reduce con evaluación diferida (lazy evaluation) de verdad?
SI LO SOPORTA... quien no lo soporta son los Arrays y las Lists de Scala.

Pero en scala encontramos otro tipo de colecciones que SI soportan evaluación diferida (lazy evaluation) y por ende, un verdadero modelo map/reduce: LOS STREAMS y las LazyLists.

Ese código último (con el LazyList) Que hemos creado en Scala, es MapReduce Puro(con evaluación diferida (lazy evaluation) de verdad).
Y ese código el problema es que se está ejecutando donde? en mi máquina! con mi CPU y mi RAM.

Y si tengo en lugar de 5 datos, 5 millones de datos? Eso me interesa? Me interesaría repartirlo.
Y Eso es lo único que nos ofrece Spark.
Spark tiene su propio tipo de datos para Listas Perezosas (lazy) que se llaman RDD (Resilient Distributed Dataset) que son listas perezosas (lazy) que se reparten entre varias máquinas y se les aplican las transformaciones map/reduce de forma distribuida.

En nuestro código map reduce, al pasar a Spark, NO HACE FALTA CAMBIAR NI UNA SANTA LINEA DE CÓDIGO.
---


Cuando mandamos nu trabajo a un cluster de spark, ese trabajo se divide en trozos (realmente no es el trabajo lo que se divide.. sino los datos). 
En Spark se hace el mismo trabajo en todos los nodos... pero cada nodo lo hace sobre una parte diferente de los datos.

Habitualmente si tengo n nodos en el cluster, parto el trabajo en un numero >> n de trozos (particiones).

Qué ventaja me da eso?

Si tengo 1M de datos... y 10 nodos... y parto los datos en 10 nodos, tengo particiones de 100.000 datos cada una.
Y esas particiones son enviadas 1 a cada nodo del cluster.
Dios no lo quiera, que tengamos la mala suerte que al procesar el dato 99.999 de una partición, el nodo que lo está procesando pete!
Spark, no tendría problema con ello (RDD = Resilient Distributed Dataset... Resilient=Que no voy a perder los datos aunque un nodo pete).
Spark en automático reenvía esa partición a otro nodo del cluster para que la procese.... pero... a empezar de 0... Lo que si tengo es una penalización en tiempo de procesamiento importante.
Eso no me interesa. ME interesa más hacer muchas particiones pequeñas, y que si una falla, solo pierda el trabajo de una partición pequeña. Esas particiones se encolan... y cada vez que un nodo del cluster está libre, coge la siguiente partición de la cola y la procesa.

Facilmente, en ese ejemplo de antes, el millón de datos, lo podría partir en 1000 particiones de 1000 datos cada una.
Y que cada nodo procese unas 100 particiones (100.000 datos) cada uno.... Quizás un nodo ha acabado más rápido que otros al ir procesando y coge más particiones de la cola. Y acabo con un nodo que ha procesado 107 particiones y otro que ha procesado 95... no importa. Lo importante es que el cluster esté siempre ocupado.

Ahora... los datos hay que mandarlos por RED a las máquinas. Eso sale gratis? en términos de tiempo? Tenemos teletransporte ya? NO
Esto SIEMPRE SIEMPRE va a tener una penalización si lo comparamos con haberlo ejecutado en una computadora local.
Pero en una computadora estoy atado a el número de Cores y la cantidad de RAM que tenga esa computadora.
Y cuando tengo conjuntos MUY grandes de datos, y tengo un cluster con muchos más nodos que CPUs y RAM que mi computadora local... esa penalización se diluye y ganaré rendimiento.

Solo si tengo un volumen de datos muy grande, y un cluster con muchos más recursos que mi computadora local, me interesará usar Spark.
Si no... quédate trabajando en local, pasa de Spark y punto pelota! De hecho te irá más rápido que hacerlo en Spark.


---

Apache Spark no es la única forma de hacer este tipo de procesamiento distribuido.
Apache Storm es otra. Stork no parte los datos... parte las operaciones.
Cada nodo del cluster de Storm hace una parte del trabajo (una operación map o reduce diferente). Todas las operaciones sobre Todos los datos... pero cada nodo hace una operación diferente.

