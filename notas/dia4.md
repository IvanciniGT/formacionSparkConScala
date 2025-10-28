# Expresiones regulares

En casi todos los lenguajes podemos trabajar con expresiones regulares (regex o regexp). 
Y en casi todos ellos se usa una sintaxis heredada de un lenguaje de programación que hoy en día ya se usa poco: Perl.

Al trabajar con expresiones regulares lo que hacemos es definir patrones:
- Verificar si un texto cumple o no con un patrón
- Extraer partes de un texto que cumplen con un patrón
- Reemplazar partes de un texto que cumplen con un patrón
- Separar un texto en partes según un patrón

## PATRON ???

Un patron es una secuencia de SUBPATRONES

## Subpatrón:

  Secuencia de caracteres seguido de un modificador de cantidad.

    SECUENCIA DE CARACTERES :
                                                                            Ejemplo        Cumple?
        hola                    esa secuencia concreta de caracteres        Hola Menchu    NO
                                                                            hola Menchu    SI
        [hola]                  cualquiera de esos caracteres               Hola Menchu    SI y en muchos sitios 
                                                                            x√√√xxxxxxx
        [a-z]                   los caracteres de la a a la z en ASCII
        [A-Z]                   los caracteres de la A a la Z en ASCII
        [0-9]                   los caracteres del 0 al 9
        [a-zA-Z0-9ñMá]
        .                       cualquier caracter
    
    MODIFICADOR DE CANTIDAD :
        NO PONER NADA           La secuencia anterior debe aparecer 1 vez
        ?                       La secuencia anterior puede aparecer 0 o 1 vez
        *                       La secuencia anterior puede aparecer 0 o más veces
        +                       La secuencia anterior debe aparecer 1 o más veces
        {n}                     La secuencia anterior debe aparecer exactamente n veces
        {n,m}                   La secuencia anterior debe aparecer entre n y m veces

    CARACTERES ESPECIALES:
        ^                       Comienzo de texto
        $                       Fin de texto
        |                       Patrones alternativos (o se debe cumplir uno o se debe cumplir el otro)
        ()                      Agrupación de subpatrones


    Validar si un número de teléfono de España:    +34 918 18 18 18
                                                   +34 912 919 192
                                                   +34918291829
                                                   +34 918291829
                                                   918 18 18 18
                                                   912 919 192
                                                   911 291829
                                                   918291829
                                                   91 1291829
                                                   91 129 18 29

     ([+]34)? ?(([0-9]{3}((( [0-9]{2}){3})|(( [0-9]{3}){2})|(( ?[0-9]{6}))))|([0-9]{2}(( [0-9]{7})|(( [0-9]{3})( [0-9]{2}){2}))))

     regex101.com

Validar un DNI formato España:  1-8 dígitos LETRA (mayúscula o minúscula). Separador de la letra?
    12345678Z
     2345678Z
    12345678-Z
    12345678 Z
    12.345.678 Z
     2.345.678 Z
    123.928-Q
      1.928-Q
    15J





    ^(([0-9]{1,2}([.][0-9]{3}){2})|([0-9]{1,3}[.][0-9]{3})|([0-9]{1,8}))[ -]?[A-Za-z]$



    Dividir el número entre 23 y nos quedamos con el RESTO (%)
    Hay una tabla que tiene el ministerio de interior con la letra que corresponde a cada resto -> 23 letras posibles

    230023 | 23
           +---------
         0   10001             0-22



Nos van a llegar una coleccion de datos de personas... entre los que tenemos el DNI y queremos:
- Validar el DNI
- Normalizar el DNI



---


doglover    
estudio
friendforever
goodvibes
goodvibes
goodvibes
masverano
partyallnight
petlover
quieroserfuncionaria
veranito

    VVVV

GroupBy (hashtag => hashtag)
(doglover, ["doglover"])
(estudio, ["estudio"])
(friendforever, ["friendforever"])
(goodvibes, ["goodvibes", "goodvibes", "goodvibes"]) -> map -> (hashtag, ocurrencias) -> ( hashtag, len(ocurrencias) )
(masverano, ["masverano"])
(partyallnight, ["partyallnight"])
(petlover, ["petlover"])
(quieroserfuncionaria, ["quieroserfuncionaria"])
(veranito, ["veranito"])

vvvvv
groupBy (hashtag => hashtag.chatAt(0))

("d", [doglover])
("e", [estudio])
("f", [friendforever])
("g", [goodvibes, goodvibes, goodvibes])
("m", [masverano])
("p", [partyallnight, petlover])
("q", [quieroserfuncionaria])
("v", [veranito])



----
JVM                                                                JVM
numeroDeHashtagsEliminados                                  numeroDeHashtagsEliminados
   v                                                                 v
Máquina 1                                        CLUSTER DE SPARK
Programa Scala/Spark    =>>>>>>>>>>>>>>         Maestro        ---> Nodo1 (datos[1]) + todas las funciones (embebidos más datos)
    Mi PC                 datos                 datos          ---> Nodo2 (datos[2]) + todas las funciones (embebidos más datos)
    Servidor              funciones             funciones
                                                                    Se ejecuta:  palabra => palabra.startsWith("#")
Defino:                                                             + datos
palabra => palabra.startsWith("#")

Con 2 nodos, prepararía solo 2 paquetes de datos? NO... posiblemente le mandaría a cada nodo 50-100 paquetes.. no se... depende del tamaño de los datos y del número de nodos.
Pero desde luego, no va a ser un paquete por nodo.
Y el problema es que si a un nodo le mando 50 paquetes de datos, le mando las funciones para aplicar sobre esos datos 50 .

---

Spark = ETLs

Hay muchas estrategias de ETLs:
- ETL : Extract, Transform, Load
- TEL : Transform, Extract, Load
- ELT : Extract, Load, Transform
- ETLT: Extract, Transform, Load, Transform
- TELT: Transform, Extract, Load, Transform
- TETLT: Transform, Extract, Transform, Load, Transform

BBDD --> SPARK --> BBDD (tabla con la que puedo hacer un join)
- Extraer datos de una BBDD
- Hago limpieza y transformaciones con Spark
- Cargo los datos transformados en una BBDD
- Hago un join con otra tabla de la BBDD para enriquecer los datos (Y esto sería muchísimo más eficiente que hacer el join en Spark)


Spark no es una BBDD... mañana veremos que podemos ejecutar muchas de estas cosas usando SQL... y eso nos despistará, porque podemos pensar que Spark es una BBDD... pero no lo es.
Y hay operaciones que son mucho más eficientes en una BBDD que en Spark... por los índices. Y Esto marca totalmente la diferencia para algunas operaciones.

NO TODOS LOS JOINS se resuelven de la misma forma:
- Merge Join <--- En Spark van como el culo... se basan en conjuntos de datos previamente ordenados
- Loop Join <---- En Spark van muy bien

---

# Broadcast y Acumuladores son dos conceptos similares, aunque opuestos... y complementarios.

Broadcast: Una variable que defino y establezco en la máquina donde lanzo el programa Spark, 
                y que quiero que esté disponible en todas las máquinas del cluster para que puedan leerla.
Acumulador: Una variable que defino en la máquina donde lanzo el programa Spark,
            pero que establezco (permito escritura) en todas las máquinas del cluster,
            y que quiero que esté disponible para su lectura en la máquina donde lancé el programa Spark

        Broadcast: Se escribe en local y se lee en el cluster
        Acumulador: Se escribe en el cluster y se lee en local

        Ambas 2 nos permiten trabajar con variables Compartidas en un entorno distribuido.

Esto si es algo especial que nos ofrece Spark, que va más allá del MapReduce.
Es algo debido a trabajar en un entorno distribuido.

Cuando estoy con Scala sin Spark, no necesito ni Broadcast ni Acumuladores, ya que no estoy en un entorno distribuido.
Al pasar a Spark, el map-reduce no cambia (o mínimamente si he usado funciones de scala de más alto nivel no soportadas por Spark),
pero si necesito Broadcast y Acumuladores para compartir variables entre las distintas máquinas del cluster.


---

Como esto es muy complejo! Spark ofrece una alternativa para dulcificar su uso.
Es una librería adicional que existe en Spark llamada Spark SQL.
Esa librería nos ofrece una sintaxis SQL para trabajar con los datos.
Una sintaxis a la que estamos acostumbrados, y que nos abstrae del map-reduce.

Claro... eso lo puedo usar cuando tengo datos que sean fácilmente representables en tablas cuadradas... que es para lo que SQL está pensado.
Cuando tengo datos que no tienen una estructura tabular, no puedo usar Spark SQL, y me toca bajar a este detalle.
Por suerte, el 90% de los datos que manejamos en el mundo real son tabulares.
Por desgracia, el 10% de los casos no.
Y en general, en muchos proyectos me tocará procesar una parte de las operaciones/transformaciones con Spark SQL, y otra parte con el API de bajo nivel de Spark (map-reduce).

El trabajar con SQL es muy fácil. No tengo mucho que enseñaros. Mañana en 60-90 minutos os lo enseño todo.
Necesitaremos aprender a cómo pasar de SparkSQL (DataFrames) a SparkCore (RDDs) y viceversa.

Imaginad que tengo una tabla de clientes, donde me viene un DNI.... y quiero validarlo.
Tengo en SQL una función que me permita validar un DNI? NO... Este no es el escenario donde necesitemos ir a RDDs y SparkCore.
SparkSQL me permite definir mis propias funciones (UDFs: User Defined Functions) para este tipos de escenarios.
Pero otro tipo de transformaciones más complejas, que no encajen en SQL, me tocará hacerlas en RDDs y SparkCore.