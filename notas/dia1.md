# Entorno de trabajo para la formación:

IntelliJ IDEA Community Edition
- Configurar MAVEN. 
    c:\Usuarios\Federico\.m2\settings.xml

    settings.xml:
    - Proxy del banco
    - Artifactory del banco
    - Cuenta de usuario (Artifactory -> Token)

---

# Apache Spark manejado desde Scala

El objetivo es aprender a manejar Spark. Para lo cual, necesitamos aprender muchos conceptos de Spark.
Como lo vamos a manejar desde el lenguaje Scala, necesitamos aprender algunos conceptos de Scala.

Sin conocimiento previo de Scala, no podremos avanzar en Spark.

--- 

# Scala - 2 días: Jueves/Viernes

Scala es un lenguaje de programación. Pero muy especial.

Cuando trabajamos con JAVA:

      Crear ficheros .java ---> javac ---> bytecode (.class) ---> interpretados JVM (Java Virtual Machine) ---> ejecución

Qué es lo que ocurre... que JAVA es un lenguaje con una cantidad de CAGADAS GIGANTES en su gramática...
JAVA lo pensaron con la parte del cuerpo que tenemos entre la lumbar y las piernas: CON EL PUTO CULO !

Pero.... la arquitectura de la JVM es una locura! MARAVILLOSA ! Ahí si pusieron todo el cerebro.. quizás por eso ya no les quedó neurona libre para el lenguaje!

Qué es Scala? Es un lenguaje de programación que ofrece una alternativa a JAVA, pero que se ejecuta en la JVM.

      Creamos ficheros .scala ---> scalac ---> bytecode (.class) ---> interpretados JVM (Java Virtual Machine) ---> ejecución

Esto me permite aprovecharme de TODO el ecosistema de JAVA, pero con un lenguaje mucho más potente, moderno, flexible y expresivo, COMPACTO.

Pero claro... COMPACTO = NO EXPLICITO... hay muchas cosas que no escribo en el código, pero que están ahí... y eso hace que al principio cueste mucho entenderlo. Hay mucha magia que hace el compilador. Mucho código que crea en automático al compilar.

La curva de aprendizaje de Scala es MUY ALTA. Cuesta mucho empezar a manejarse con Scala. Lo que pasa es que cuando lo controlo, da gusto.
Con muy poco código hago cosas muy complejas.

Ésto no penséis que es nada especial: SCALA no es el único lenguaje que ha salido para aprovechar la JVM. Hay muchos otros lenguajes que también se ejecutan en la JVM: **Kotlin**, Clojure, Groovy, JRuby, Jython, etc.

En el mundo del BIGDATA, Scala es el lenguaje. Muchos , MUCHOS programas del ecosistema BIGDATA están escritos en Scala, entre ellos APACHE SPARK. Y por ende, es la mejor alternativa para manejar Spark. Spark puede manejarse desde: Scala, Python, R, Java. Pero el lenguaje nativo de Spark es Scala.

## Características de SCALA

### Tipado: estádico(fuerte) vs dinámico(débil)

Scala es un lenguaje de tipado estático. ¿Qué significa eso?

```py
texto = 'hola'      # Estamos asignando la variable "texto" al valor "hola"
```

> Qué es una variable? 

Todo lenguaje de programación maneja datos. Y esos datos será de un determinado tipo: texto, número entero, número decimal, fecha, booleano, etc.

Una variable no es una cajita (espacio de almacenamiento en memoria RAM) donde guardo un valor.... y luego otro...
O si... depende del lenguaje de programación. AH! resulta que el concepto de variable no es igual en todos los lenguajes de programación.
En C o en C++, una variable es una cajita en memoria RAM donde guardo un valor, que puede cambiar a lo largo del tiempo.

Pero resulta que en Python, Javascript, JAVA, SCALA una variable es otra cosa. En estos lenguajes, una variable es una referencia a un dato que tengo en RAM.

```py
texto = 'hola'      # Estamos asignando la variable "texto" al valor "hola"
```
1. Se crea el dato de tipo "str" (texto) en memoria RAM, con el valor "hola".
   Podemos imaginar la RAM como si fuera un cuaderno de cuadros (literalmente es lo que es)... y estamos abriendo el cuaderno por algún sitio y escribiendo allí "hola". 
2. Nos estamos creando una variable llamada "texto"
   Una variables es como un post-it. Lo que estamos haciendo aquí es tomar un post-it del toco de los  post-its y escribiendo en él "texto".
   En el post-it (variable) no escribo "hola", escribo "texto".
3. =    Asignar la variable al dato
   Lo que hacemos aquí es pegar el post-it (variable) en el cuadro del cuaderno (RAM) donde hemos escrito "hola".
   Y gracias a ese post-it (variable) puedo más adelante encontrar el dato "hola" en medio de todo ese cuaderno (RAM).

```py
texto = True      # Estamos asignando la variable "texto" al valor booleano True
```
1. Se crea el dato de tipo "bool" (booleano) en memoria RAM, con el valor True. (se guarda en un sitio diferente al dato "hola" de antes)
2. Despegamos el post-it (variable) del cuadro del cuaderno (RAM) donde estaba pegado (al lado del dato "hola")
   Y lo pegamos en el cuadro del cuaderno (RAM) donde hemos escrito "True".

   En la RAM tenemos ahora 2 datos "hola" y "True".
   Lo que pasa es que el "hola"" se queda huérfano de variable ( o no). Pero si se queda huérfano, el problema es que ese dato, desde Python, Java, JS, Scala, ya nunca más podré acceder a él (en C por ejemplo si podría, si conociera la dirección de memoria donde está guardado... en número del cuadrito del cuaderno)... y se convierte en BASURA: GARBAGE. Y quizás entre o no el GARBAGE COLLECTOR (que es un proceso que existe en la JVM de java, o en el interprete de python, etc) y borre ese dato de la RAM para liberar espacio.

   Ha cambiado algo de tipo de dato? NO
   - Tenía antes un dato de tipo str (texto) con valor "hola"
   - Ahora tengo otro dato de tipo bool (booleano) con valor True
   No hay nada que haya cambiado! con respecto a los tipos de datos. Simplemente hemos creado otro dato diferente en RAM de otro tipo.

```java
String texto = "hola";      // Estamos asignando la variable "texto" al valor "hola"
```

1. Se crea el dato de tipo "String" (texto) en memoria RAM, con el valor "hola".
2. Se crea la variable llamada "texto" de tipo "String". ANDA OSTIAS ! Si resulta que JAVA las variables TAMBIEN tienen tipo !!!!!
   Es como si tengo post-its de diferentes colores. 
   - Un post-it rojo los puedo pegar al lado de datos de tipo String
   - Un post-it azul los puedo pegar al lado de datos de tipo Integer
   - Un post-it verde los puedo pegar al lado de datos de tipo Boolean.   ESTO NO EXISTE EN PYTHON, ni en JS.
   En python o en JS las variables NO TIENE TIPO. Los datos si. Y una variable puede apuntar a cualquier tipo de dato.
   En JAVA y en SCALA las variables TAMBIÉN TIENEN TIPO. Y los datos... Y una variable solo puede apuntar a datos de su mismo tipo ( o un subtipo).
3. =    Asignar la variable al dato
   Lo que hacemos aquí es pegar el post-it rojo (variable de tipo String) en el cuadro del cuaderno (RAM) donde hemos escrito "hola".

```java
texto = true;      // ERROR DE COMPILACIÓN. No puedes apuntar desde un variable de tipo String a un dato de tipo Boolean
```

- Lenguajes de tipado estático o fuerte son aquellos en los que las variables tienen tipo.
- Lenguajes de tipado dinámico o débil son aquellos en los que las variables no tienen tipo.

Qué es mejor?
- Curiosamente, los 2 lenguajes más usados en el mundo son lenguajes de tipado dinámico: Python y Javascript.
- No nos engañemos. El tipado dinámico, aunque es más sencillo (y por eso más popular) NO VALE en proyectos grandes. NO SIRVE. A LA BASURA. DESCARTADO TOTALMENTE! ES UN PROBLEMON DE NARICES!

Por qué?

```py
def generar_informe(titulo, datos):
    # Generar informe a partir de los datos
    pass
```
Pregunta, qué le tengo que pasar a la función? NPI, no lo se! Y como tenga 100 funciones y seamos 5 en equipo, flipas!
Lo único que puedo hacer es:
- Mirar el código fuente de la función (si tengo acceso)
- Mirar la documentación (si existe)

En serio???? Para saber cómo comunicarme con una función tengo que mirar su código fuente o su documentación???? Eso es inviable en proyectos grandes.

Si estoy en un proyecto con mi primo, montando un programa con 5 funciones... nos apañamos.
Si estoy montando un script sencillito de sacar unos datos de una base de datos y hacer un informe...  o llevarlos a otro sitio, me vale.

Para casos muy simples, me vale! Para proyectos más grandes, con muchas personas, o donde haya rotación de personal, o donde el proyecto lo escriba uno y lo mantenga otro... ESO NO VALE. ME DA UN HUEVO DE DOLORES DE CABEZA. Y por eso estáis aquñí!

```java
public Path generarInforme(String titulo, List<Integer> datos) {
}
```

Y qué pasa con Scala? Donde entra tipado dinámico o tipado estático? Estático.. pero.. aquí sale otro conepoto.

```scala
// var texto: String = "hola"   // Estoy definiendo explícitamente el tipo de la variable
var texto = "hola"
texto = true                    // ERROR DE COMPILACIÓN. No puedes apuntar desde un variable de tipo String a un dato de tipo Boolean
```

Estoy definiendo un tipo para la variable? NO EXPLICITAMEMTE... pero en Scala tenemos el concepto de Inferencia de tipos.
En el caso de arriba, la variable "texto" es de tipo String. No lo he definido explicitamente (ojo, que podría).
En el caso de Scala, si una variable no tiene el tipo explicitamente definido, el compilador de Scala lo asignará automáticamente, en base al valor con el que se inicializa la variable.

### Paradigmas de programación

Un "paradigma de programación" es un nombre "hortera" que los desarrolladores ponemos a las formas en las que podemos escribir código en un lenguaje de programación.
En los lenguajes humanos, también tenemos este concepto.. aunque no les ponemos ese nombre "hortera".

> Felipe, pon una silla debajo de la ventana.                                   IMPERATIVO
> Felipe, debajo de la ventana, debe haber una silla. Es tu responsabilidad.    DECLARATIVO

- Programación imperativa               Cuando le damos órdenes/instrucciones a la computadora que debe procesar de forma secuencial.
                                        Hay veces que queremos romper la secuencia y aparecen las típicas estructuras de control de flujo: condicionales (if, switch) y bucles (for, while). 
                                        Scala admite programación imperativa, igual que python, java...
- Programación procedural               Cuando el lenguaje me permite agrupar esas intrucciónes bajo un nombre. 
                                        Y posteriormente ejecutarlas mediante ese nombre.
                                        Aparecen las funciones/procedimientos/métodos/subrutinas.
                                        Scala admite programación procedural, igual que python, java...
                                        Por qué creamos funciones? Qué aporta?
                                        - Reutilización de código
                                        - Facilitar la estructuración del código, y su mantenimiento, corrección de errores, etc.
- Programación funcional                Es quizás la más compleja de entender.. Y LA MAS IMPORTANTE PARA NOSOTROS  ...
                                        Como concepto es simple a rabiar.
                                        Cuando el lenguaje de programación me permite que una variable apunte a una función, y posteriormente ejecutar la función desde la variable, decimos que el lenguaje soporta programación funcional.
                                        El hecho no es lo que es la programación funcional, sino lo que puedo empezar a hacer, una vez que el lenguaje soporta programación funcional:
                                        - Puedo crear funciones que reciben como parámetros otras funciones.
                                        - Puedo crear funciones que devuelven como resultado otras funciones (closures).

                                        Pero... cuando usamos programación funcional, puede ser que necesite crear funciones por un tercer motivo:
                                        - Reutilización de código
                                        - Facilitar la estructuración del código, y su mantenimiento, corrección de errores,
                                        - Porque una una función a la que quiero llamar, me obligue a pasarle otra función como parámetro.

- Programación orientada a objetos      En todo lenguaje manejamos datos... y esos datos tienen un tipo. 
                                        Todo lenguaje trae una serie de tipos de datos incorporados: enteros, decimales, texto, booleanos, fechas, lista, mapa,...
                                        Hay lenguajes que me permiten crear mis propios tipos de datos, con sus características y comportamientos. Los lenguajes que me permiten hacer eso son lenguajes orientados a objetos.

                                        TIPO DE DATO        CARACTERISTICAS             OPERACIONES
                                        -----------------------------------------------------------

                                        Textos              Secuencia de caracteres      Concatenar, Substring, Buscar, Mayúsculas

                                        Fecha               Dia, Mes, Año                Restar fechas, Preguntar si cae en Jueves

                                        Cuenta Corriente    Número de cuenta, Titular, Saldo     Ingresar dinero, Retirar dinero, Consultar saldo ** Tipo de dato CUSTOM!, con sus características y comportamientos **

                                        CLASE  = TIPO DE DATO
                                        OBJETO = DATO de un determinado TIPO DE DATO (Una instancia de una clase)

                                        "Hola" = Objeto de tipo String (clase)

                                        Scala, Python, Java.. todos soportan programación orientada a objetos.

---








---

# Spark - 3 días: Lunes/Martes/Miércoles (aquí nos irán saliendo cositas adicionales de Scala que iremos viendo sobre la marcha)

Apache Spark es un reemplazo del motor de procesamiento MapReduce de Apache Hadoop, preparado para trabajar en memoria (in-memory).


MapReduce: Una forma de crear programas, para manejo de colecciones de datos, que soporta procesamiento paralelo y distribuido.
           Y se basa puramente en programación funcional.


---

# BigData

Apache Spark es una herramienta del ecosistema BigData.

## Qué es eso de BigData?

BigData es un enfoque, que aplicamos cuando trabajamos con datos y tenemos un problema al aplicar técnicas tradicionales (ya no me sirvenm y necesito alternativa). Tiene que ver con usar el poder de cómputo y almacenamiento de muchas máquinas (clusters) como si fueran 1 solo sistema.
Tiene más que ver con la infraestructura que con la aplicación en sí misma... aunque es una combinación de ambas cosas.

Trabajamos con datos:
- Quizás quiero almacenar datos o no!
- Quizás quiero procesar datos o no!  (SPARK)
- Quizás quiero analizar datos o no!
- Quizás quiero transmitir datos o no!

Por qué podrían dejar de servirme las técnicas tradicionales?
- Volumen de datos muy grande
    > Imaginad que tengo un pincho USB nuevito... sacado del paquete... de 16 GB. Y quiero guardar en él un video de la comunión de mi primo. Y ese video ocupa 5 Gbs. Puedo guardarlo? NO SE... Depende del sistema de ficheros que tenga el pincho USB.
        FAT-16: Soporta ficheros de hasta 2 GB
        Puedo irme a sistemas de archivos más avanzados: FAT-32, exFAT, NTFS, ext4, etc.
        Pero incluso esos tienen un límite máximo de tamaño de fichero.
        Y si quiero guardar un fichero de 10 PB? Ningún sistema de ficheros
        Donde ni siquiera existen HDD de este tamaño??? Qué sistema de archivos uso? HDFS (Hadoop Distributed File System, que una una implementación opensource de un sistema de ficheros distribuido que ideó la empresa Google: Google File System (GFS).
        Que me permite guardar archivos a trozos en HDD ubicados en máquinas diferentes, y acceder a ellos como si fuera un único sistema de ficheros.
- Velocidad de generación de los datos y su ventana de vida útil
    > Clash royale.   2v2
      En un segundo yo puedo hacer fácil un par de movimientos.. que deben mandarse a los teléfonos de mis compañeros y de mis rivales.
      En total hablamos de que 1 segundo mio supone 6 mensajes que hay que enviar.
      Pero somos 4 jugando => 24 mensajes por segundo.
      Si en un momento hay 50000k partidas en paralelo => 1.2 millones de mensajes por segundo.
      Quiero guardarlos? NO!
      Quiero analizarlos en tiempo real? NO!
      Quiero procesarlos en tiempo real? NO!
      Lo único que quiero es asegurar su entrega... transmitirlos! Y No hay servidor en el mundo que aguante esa carga.
      La solución? En lugar de tratar de usar un super computador, usaré cientos de ordenadores de mierda (commodity hardware), y repartiré la carga entre todos ellos.
- Quiero la lista de la compra... pa'l mercadona: Producto x cantidad
      
      EXCEL... aunque si la lista sube a más de 500.000 filas.. el Excel empieza a hacerse caquita!
      MariaDB, PostgreSQL... aunque si la lista sube a más de 10 millones de filas... la base de datos empieza a hacerse caquita!
      SQL Server  ... aunque empieza a ser carita! a no ser que empiece a subir a más de 1km de filas!
      Siempre me quedará oracle... Eso si .. si le tiene que meter 1mm de filas... flipa!

      Tendré que buscar una BBDD distribuida... HBase, Cassandra, MongoDB, etc.que no se ejcutan sobre 1 solo servidor, sino que se repartan los datos entre muchos servidores.

Lo que sea que quiera hacer.

Papers originales de Google sobre BigData:
    - MapReduce
    - Google File System (GFS)
    - BigTable
HADOOP:
    - HDFS (Hadoop Distributed File System)  -> Implementación opensource de GFS
    - MapReduce  -> Implementación opensource de MapReduce = RUINA !
Spark:
    - Spark Core -> Reemplazo de MapReduce  HADOOP