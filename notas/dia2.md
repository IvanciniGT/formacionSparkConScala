# Versiones de Scala:

Actualmente estamos en versión 3.3.
Pero no vamos a estar usándola.... por limitaciones de Spark.

# Maven

Maven es una herramienta de automatización de trabajo en proyectos de desarrollo. Principalmente se utiliza en proyectos Java, aunque también puede ser utilizado en otros lenguajes de programación, especialmente en proyectos que utilizan la Máquina Virtual de Java (JVM: SCALA)

## Estructura que tiene un proyecto Maven

proyecto/
|-- src/
|   |-- main/
|      |-- scala/        # Código fuente en Scala
|-- target/              # Carpeta donde se genera el código compilado y empaquetado
|-- pom.xml               # Archivo de configuración de nuestro proyecto en Maven

## pom.xml

En este archivo tenemos:

- Definir las coordenadas (son los datos que lo identifican) de nuestro proyecto: groupId, artifactId, version
    - version       = versión de nuestro proyecto... Habitualmente usamos esquema de versionado semántico (MAJOR.MINOR.PATCH)
    - artifactId    = id del proyecto
    - groupId       = grupo al que pertenece el proyecto. Todo proyecto JAVA, SCALA, etc tiene un groupId único que nos sirve para juntar conceptualmente todos los proyectos de una misma utilidad dentro de una misma organización.
        - groupId: Proceso de carga de datos desde la BBDD X        => carga-datos-bbdd-x
        - artifactId: Programa de ingesta                           => ingesta-datos-bbdd-x
        - artifactId: Programa de transformación                    => transformacion-datos-bbdd-x
- Dependencias de mi proyecto (librerías externas que necesito para que mi proyecto funcione) ~ requirements.txt (de python)
  Las dependencias también se identifican por sus coordenadas: groupId, artifactId, version 
- Todo tarea maven es ejecutada por un plugin. Maven por si mismo no sabe hacer nada.
  Hay muchos plugins que vienen preconfigurados en maven (compilar código, ejecutar tests, crear un JAR, etc) (Más para java)
  En el caso de scala habrá que configurar unas cuantas cosas.

## Goals de maven

Maven me permite automatizar muchas tareas:
- Descarga de dependencias, desde internet o desde el repositorio de artefactos de mi organización
- Compilación del código fuente
- Empaquetado del código compilado en un JAR
- Ejecución del programa
- ...

Esos son los GOALS de Maven.

Algunos habituales son:
- mvn clean            -> Borra la carpeta target (donde está el código compilado y empaquetado)
- mvn compile          -> Compila el código fuente
                            Básicamente los archivos .scala que hay en src/main/scala, dando lugar a archivos .class (bytecode de la JVM)
                            que se guardarán en una carpeta target/classes
- mvn package          -> Empaqueta el código compilado en un JAR
                            Mete la carpeta target/classes en un archivo zip (.jar) que se guarda en target/nombreproyecto-version.jar
- mvn install          -> Esto solo copia nuestro archivo .jar en mi carpeta .m2
                          En nuestro caso... para trabajar con Spark no lo usarmos mucho
Nuestro plugin de ejecución de programas (exec-maven-plugin) nos permite ejecutar programas directamente desde maven:
- mvn exec:java -Dexec.mainClass="com.miempresa.MiClasePrincipal

## Carpeta .m2

Cuando instalamos y usamos maven, maven crea una carpeta oculta en la carpeta de nuestro usuario llamada .m2
c:\Users\ivan\.m2       (Windows)
 /home/ivan/.m2         (Linux, MacOS)

En esa carpeta hay 2 cosas:
- settings.xml          -> Archivo de configuración global de maven (repositorios, proxies, etc)
- repository/           -> Cuando maven descarga librerías (dependencias) A mi máquina para mis proyectos, 
                           las pone en esta carpeta.
                           Así si otro proyecto necesita la misma librería, maven no tiene que volver a descargarla de internet.
                           Incluso puedo copiar los archivos .jar que yo voy generando en mis proyectos a esta carpeta, para que otros proyectos de MI COMPUTADORA los puedan usarlos como dependencias.
                           
---

Todo lo queremos hacer mediante una herramienta tipo MAVEN. Hay programas (entornos de desarrollo) que me permiten compilar el proyecto a golpe de botón... ejecutarlo con play! ESO NO VALE PARA NADA! NO LO USAMOS NUNCA.
Necesitamos que el entorno se integre con maven. MAVEN es la herramienta que tanto YO, como el ENTORNO debe usar para compilar, ejecutar, testear, etc. IMPORTANTISIMO.

Tenemos que tener en cuenta que SCALA no es como PYTHON. SCALA ES COMPILADO! PYTHON NO.
En un proyecto python, lo que distribuimos es el código fuente (en archivos .py, ipynb, etc). 
En SCALA (y en JAVA) lo que distribuimos es el código compilado (archivos .class dentro de un .jar). Eso es lo que se ejecuta en la JVM.
Quién genera eso? UN PROGRAMA QUE CORRE EN UN SERVIDOR. Eso no lo hacéis en local. Lo puedo hacer.. para un a prueba mia.
Pero mi proyecto, al final, sube a un sistema de control de versiones: GIT

Y ahí no vamos a subir nunca jamás la carpeta TARGET. SOLO SE SUBE LA CARPETA src y el archivo pom.xml
Y lo que queremos es luego un programa, que alguien por ahí habrá configurado, que sea capaz de:
- Descargar mi proyecto de GIT
- Compilarlo con MAVEN
- Empaquetarlo con MAVEN
- Subir el JAR resultante a un repositorio de artefactos (Nexus, Artifactory, etc)
- De ese Nexus o Artifactory, alguien (otro programa en otro servidor) más podrá descargar mi JAR y lo ejecutará en un cluster de SPARK.

---

Spark con Scala.

Ya dijimos que Spark podemos usarlo desde PYTHON (pyspark) o desde SCALA (spark con scala) o desde JAVA (spark con java).

Si lo que quiero es tirar una query SQL y hacerle un par de cositas. USA PYTHON! NI DE COÑA uses SCALA!
Es absurdo! Cada lenguaje tiene sus casos de uso.

Ahora... cuando quiero procesos más complejos con Spark, que se van a quedar en un servidor corriendo los martes a las 3:00,... donde tendré varios compañeros a lo largo de tiempo trabajando en el mismo proyecto... Ahí SCALA es la mejor opción.

Python cuando sale allá por el 1992 era un lenguaje chorra, que nadie en el mundo del desarrollo de software usaba
Con el tiempo se ha convertido en el lenguaje programación más usado del mundo. Con diferencia...
Pero, no nos engañemos. Lo es porque :
- Es el lenguaje que la mayor parte de la gente aprende primero: POR LO SIMPLE QUE ES
- Mucha gente hoy en día hace programas MUY SIMPLONES: 
  - Scripts para automatizar tareas
  - Modelos de IA
  - Análisis de datos
Y para esas cosas, python es genial. Por que son programas MUY SIMPLES: SCRIPTS!

---

# Tipos de datos BASICOS

Las computadoras por dentro entienden de textos? NO ... y un número? Tampoco... y una fecha? Nasti de plasti!
Las computadoras entienden de 0s y 1s... mentira también.. tampoco entienden de 0s y 1s...
Entienden de estados binarias:
- Llega corriente eléctrica o no llega corriente eléctrica en este momento por tal patilla del microprocesador.
- Tal segmento del HDD está magnetizado en un sentido o en otro.
- Tal trocito de la memoria RAM tiene carga eléctrica o no la tiene.
Los humanos normalmente estas cosas las representamos con 0s y 1s... para entendernos y que nos sea más fácil hablar.. pero bueno es una aproximación válida

RAM [ ][ ][ ][ ][ ][ ][ ][ ]
     1
     0

Una cosa es lo que ocurre a nivel físico... y otra lo que interpreta el software que corre encima de ese hardware.
Al final entiendo que en una de esas casillas , como pueden estar en 2 estados físicos (llegandole corriente o no) puedo representar 2 estados lógicos (0 o 1).
Y yo les asigno un significado a esos estados lógicos. Por ejemplo
- 0 = falso       = llueve        = el ti@ tiene pasta en la cuenta
- 1 = verdadero   = no llueve     = el ti@ no tiene pasta en la cuenta

En una de esas casillas podemos guardar 1 valor a elegir entre 2 estados lógicos diferentes (0 o 1)... eso es lo que llamamos un 1.

En un bit puedo guardar potencialmente 2 valores diferentes (0 o 1)
Otra cosa, como digo es el significado que yo le doy a esos valores yo humano

Claro.. a veces quiero guardar cosas que necesitan (que potencialmente pueden albergar más de 2 valores diferentes):
Edad: 0-1?
Empiezo a juntar casillas:
2 bits: 00 01 10 11  -> 4 valores diferentes
3 bits: 000 001 010 011 100 101 110 111  -> 8 valores diferentes
4 bits: 0000 0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1101 1110 1111  -> 16 valores diferentes

8 bits = 1 byte -> 256 valores diferentes (0-255)

          ¿Qué significado le doy a aquello?
            NUMERO ENTERO: 0-255      NUMERO CON SIGNO: -128 a 127      CARACTERES
00000000      0                           -128                              a
00000001      1                           -127                              b
00000010      2                           -126                              c   
00000011      3                           -125
...
11111111      255                          127

2 bytes = 16 bits -> 65.536 valores diferentes (Por ejemplo, si fueran número enteros 0-65.535.. si quiero usar esos valores para representar números con signo, sería -32.768 a 32.767)

4 bytes = 32 bits -> 4.294.967.296 valores diferentes


Con los caracteres pasa algo especial.
- Los americanos usan los mismos que los españoles? algunos. Los americanos no usan: ñ á é í ó ú ü ¿ ¡
- Los chinos usan los mismos caracteres que los españoles? NO
- Los arabes usan los mismos caracteres que los españoles? NO
- Los rusos
- Los griegos
- ....

La humanidad usa en torno a 150.000 caracteres diferentes en sus distintos idiomas.
Y están recogidos en un estandar llamado UNICODE.

Si soy Americano, con 256 caracteres distintos me vale : Creo mi propio juego de caracteres (ASCII)

  11011010 = ®


Si soy Español, necesito más caracteres: Creo mi propio juego de caracteres (ISO-8859-1) (quito algunos delos americanos.. que la traen al peiro! y pongo los mios)

  11011010 = á

Si quiero representar todos los caracteres del mundo: Uso UNICODE

  UTF-8, UTF-16, UTF-32
  En UTF-8, los 255 caracteres más usados por la humanidad se guardan en 1 byte
  Los siguientes 65.281 caracteres se guardan en 2 bytes
  El resto se guardan en 4 bytes



Números 
Textos
Fecha y hora

> DNI

  12345678A

  ¿Cómo guardo esto en un fichero o base de datos?
  
  > Puedo guardarlo como un texto: VARCHAR(9)
  
  Cuánto ocupa eso en Disco o en RAM? Cuánto ocupa un char? Depende... depende del juego de caracteres.
  Para estos caracteres tan comunes, con 1 byte por carácter me vale: 9 bytes en ram o en disco.

  Habría una forma más óptima?

  > Guardar el número como un entero: 12345678 y la letra aparte: 'A'

  La letra será 1 byte (si es ASCII o ISO-8859-1 o UTF-8)
  El número.. 4 bytes
  TOTAL 5 BYTES... Ahorro 4 BYTES!... En % = 44.44% de espacio ahorrado!

  > Podría ser más agresivo:

  Guardo solo en número. La letra se calcula en autom. desde el número.
  TOTAL 4 BYTES... Ahorro 5 BYTES!... En % = 55.55% de espacio ahorrado!

Pregunta ... esto es importante?

> El almacenamiento hoy en día es barato o caro? 

Es lo más caro con diferencia en un entorno de producción... y nosotros humanos mortales flipamos con esto.
Yo digo.. joder si esto ha bajado mucho. 

50.000 ptas (300€) y tenía 10Mbs
Hoy en día con 300€ tengo 20TB (20.000.000 Mbs)

Claro... ha bajado mucho.

Y puedo creerme que como yuo NO SOY TONTO y voy al MEDIA MARKT y compro un disco duro de 2TB por 80€... eso es barato.

Pero en una empresa es muy MUY diferente:
- En casa, el HDD le uso para leer/escribir 2 veces al mes.
- En una empresa, el HDD le uso para leer/escribir 24 horas al día.
Y eso implica que los componentes con los que está hecho el disco deben ser diferentes.. y son mucho más caros.

Western RED PRO  ->  170€
Oracle 2Tbs NVME -> 3600€

Estamos x20

Pero eso es el principio.
Los datos ¡son lo más valioso que tiene una empresa hoy en día!
Y cada daato en un entorno de producción, debe estar replicado al menos 3 veces (por si falla un disco, o un servidor, o un centro de datos entero)
Es decir, que para tener 2Tbs en un entorno de prod, necesito comprar 3 discos de 2Tbs... x3(discos un x20 más caros) = x60

Pero espera.. ahora mete backups... de al menos 2 semanas atrás... x10... = x70

Y el HDD que en casa me cuesta 80€... en una empresa me cuesta  80 x 70 = 5600€

MUY CARO...Pero el problema ya no es solo ese.

Si los datos ocupan x2, mis programas tardan x2 en leerlos del HDD, el doble en mandarlos por red, el doble en subirlos a memoria, el doble en procesarlos, el doble en guardarlos de nuevo en disco.
Los tiempos se me van.

Y estamos en el mundo BIG DATA... donde los datos son enormes.... para eso es Spark!
Si estoy con 30k datos.. Usa el Excel y pasa de esto.. y de echar cuentas.

Python tiene el tipo de datos: int 4bytes (-2.147.483.648 a 2.147.483.647)

---

# Esquema semántico de versionado (semver)

v1.2.3

                Cuando se incrementan
1 = MAJOR       Se incrementa cuando hacemos breaking changes (cambios que rompen compatibilidad con versiones anteriores)
2 = MINOR       Se incrementa cuando se añaden funcionalidades
                También cuando marcamos una función como obsoleta (deprecated)
                + Además pueden venir arreglos de bugs
3 = PATCH       Se incrementa cuando hay arreglos de bugs


## MAJOR = BREAKING CHANGES

En un caso com el que nosotros vamos a trabajar: Proyectos Spark con Scala, un breaking change puede ser:
- Cambiar la estructura de datos que devuelve una función
- Cambiar la estructura de datos de entrada


---

# Distribución y ejecución del código:

Lo primero que tengo es que asegurar que mi código funciona. HAGO PRUEBAS EN LOCAL.
Para ello, ejecutaré mi programa en local.. A ser posible con pruebas automatizadas.

Una vez que mi código funciona en local, lo subo a GIT, solo la carpeta src y el archivo pom.xml.

Lo que tiene que ocurrir a partir de ahí es:
- 1.  Que alguien, yo o no yo, descargue el proyecto de GIT (Si soy yo, ya lo tengo en local) --> AUTO
- 2.  Ejecutar un mvn package para compilar y empaquetar el proyecto en un JAR                --> AUTO
- 3.  Subir ese JAR a un repositorio de artefactos: Artifactory                               --> AUTO
- 4.  Que alguien, yo o no yo, descargue ese JAR de Artifactory                               --> AUTO
- 5.  Mandarlo a un cluster de Spark para que se ejecute                                      --> AUTO
      spark-submit
En la realidad, lo que hacemos es aplicar una cultura DEVOPS!


# DEVOPS

Cultura, movimiento, filosofía en pro de la automatización de todos los trabajos entre el DEV ---> OPS

# Automatizar?

Crear una máquina (o modificar el comportamiento de una que ya tenemos) para que haga lo que antes hacíamos nosotros manualmente.

Automatizar el lavado de la ropa:
LAVADORA (programas: algodón, lana, 90º, etc)

Automatizar la compilación, empaquetado y despliegue de un proyecto software:
COMPUTADORA (Programas):
- MAVEN: Compilar y empaquetar en automático

Las autoamtizaciones las montamos en 2 niveles:
- Automatizamos tareas individuales (mvn package, mvn test, spark-submit, etc)
- Orquestar esas tareas individuales para que se ejecuten todas y en el orden correcto 
     (Jenkins, Bamboo, TeamCity, GitHub Actions, GitLab CI/CD, etc)

En Jenkins montamos lo que llamamos circuitos de CI/CD (Integración continua / Entrega Continua / Despliegue continuo)
Un proceso de integración continua es un programa que se encarga de:
- Descargar el código fuente de GIT
- Compilarlo y empaquetarlo con MAVEN
- Ejecutar los tests automatizados: mvn test
- Mandar a SonarQube el código fuente y los resultados de los tests para que analice la calidad del código
- Otro tipo de pruebas
- Prepara un informe de resultados de las pruebas

Un proceso de entrega continua es un programa que se encarga de:
- Arranca después del proceso de integración continua
- Si todo ha ido bien, sube el JAR resultante a un repositorio de artefactos : Artifactory

Un proceso de despliegue continuo es un programa que se encarga de:
- Arranca después del proceso de entrega continua
- Si todo ha ido bien, descarga el JAR de Artifactory y lo manda a un cluster de Spark para que se ejecute ahí. (O mandarlo a otro tipo de sistema para programar la ejecución periódica del mismo)

En el caso de Scala, usamos cosas más potentes: SonarQube


mvn compile        -> Compila src
mvn test-compile   -> Compila test
mvn test           -> Ejecuta los tests en target/test-classes
mvn package        -> Solo empaqueta la compilación de src



---

# Workflow que tenemos en GIT


----master/main------------------------------------C3
                                                  / 
                              -----release-------C3 <-- Aquí hacer pruebas de sistema
                                                /         
---desa/dev-------------C1 ------------------ C3
                          \                 / <--- Aquí ya se deben exigir que las pruebas pasen (unitarias/integración)
  feature                  C1---> C2 ---> C3



----master/main----------------------------------C3
                                               / <-- Aquí hacer pruebas de sistema
---desa/dev-------------C1 ------------------ C3
                          \                 / <--- Aquí ya se deben exigir que las pruebas pasen (unitarias/integración)
  feature                  C1---> C2 ---> C3



---desa/dev-------------C1 ------------------ C3 -------+---------- C7
                          \                 /            \         /
  feature1                 C1---> C2 ---> C3              \       / ff: Fast-Forward (Es copiar un commit de una rama a otra)
                            \                              \     /.  En este caso, me conviene haber hecho antes las pruebas 
  feature2                   C1---> C4 ----> C5 -----> C6 --> C7 (Merge: Aquí me pueden dar los conflictos)


# COMMIT en GIT?

- ~~Un paquete de cambios que meto el código!~~ NO ES ESTO.. al menos en GIT. 
  Si lo era en sistemas de control de versiones antiguos como SVN (subversion) , CVS
- En GIT un commit es una copia COMPLETA de todo el proyecto en un momento dado del tiempo: 
      BOTON DERECHO EN LA CARPETA Y meter en archivo .zip

  En GIT, Un patch (el paquete de cambios) se calcula dinámicamente comparando 2 commits diferentes.
  Los cambios que se intrujeron en el feature1 serías: C3-C1




Reglas de master:
- Lo que hay se considera apto para producción
- Nunca se puede hacer un commit directo en master

Reglas de dev:
- Lo que hay es lo que se considera apto para la siguiente versión
- Nunca pueden hacer commits directos en dev (depende la complejidad del proyecto, esta regla a veces nos la saltamos)

Para hacer subir un commit a desa desde featureX, hacemos un pullRequest (merge request) desde featureX a desa.

- En Gitlab se habla de Merge Request
- En BitBucket, Github se habla de Pull Request

Y cuando hacemos esto (antes de hacerlo (PR/MR)) debemos haber ejecutado localmente los test (para adelantarme al Jenkins).

En los ETLs que montamos con Spark... los procesos son bastante lineales en general:
1. ORIGEN
2. TRANSFORMACIÓN
3. CARGA

Y en este caso, las pruebas de integración = Pruebas de sistema
Hay veces que las ETS son mas complejas:

1. ORIGEN 1
2. TRANSFORMACIÓN. <<<<< (Rama feature1) Para pasar a dev, desde su rama, prueba que Su transformación sea capaz de trabajar con el origen 1
         ORIGEN 2
         ORIGEN 3
         TRANSFORMACION  <<<<(Rama feature2) Para pasar a dev, desde su rama, prueba que Su transformación sea capaz de trabajar con los orígenes 2 y 3
3. CARGA

En casos como este, las pruebas de Integración != Sistema : Donde pruebo el pipeline completo


En general al trabajar con Spark, que son programas muy sencillos en cuanto a flujo de trabajo y componentes (el algoritmo puede ser más o menos complejo, pero el flujo de trabajo es sencillo), las pruebas de integración y las de sistema suelen coincidir.


----

Habitualmente en el mundo Scala, la herramienta de automatización que usamos no es MAVEN, es SBT (Scala Build Tool)

Lo que pasa es que en el banco, que estáis muy acostumbrados a JAVA y por ende a MAVEN, se ha decido no usar SBT y seguir con MAVEN.
Y lo aplicamos tanto a JAVA como a SCALA.

---

En el caso de SCALA hay otra herramienta que se mete de por medio: JACOCO: JAva COde COverage
Eso lo que hace es, al ejecutar las pruebas, asegurarse que esas pruebas hayan ido por todas las lineas de código que tengo.
Si hago pruebas, pero hay 4 if, y 3 funciones que no he probado con mis datos de prueba... JACOCO me pone mala nota... Y Sonar entonces me deniega el QUALITY GATE... y entonces Jenkins no me deja subir el JAR a Artifactory.