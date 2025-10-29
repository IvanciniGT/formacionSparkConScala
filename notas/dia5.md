
Soy amazon... y quiero tener mi listado de productos que vendo en mi web.

Puedo tenerlo en una tabla SQL? NI DE COÑA !

PRODUCTOS
ID   NOMBRE       PULGADAS TALLA
1    Televisor    55
2.   Calzoncillos -        M

700 tablas

- Quiero es una BBDD Mongo (JSON y esquemas variables)
- Neo4J (Grafos) = RELACIONES... en SQL Flipas!

               Amistad (nivel: Fuerte)
    Persona1  ---------------------------> Persona2
                                              | Tio
                                              v
              ---------------------------> Persona3
                Pareja (nivel: Muy fuerte)

Monta esto en SQL... Cada tipo de relación ---> Infinito sería una tabla de cruce.
Flipa luego en las queries
Dame todas las personas relacionadas con Felipe (que consulto en 800 tablas?)

NO VALE SQL PARA TODO. Aquí entra un Neo4J (Que es lo que usa Facebook)


FACTURAS

ID NOMBRE CLIENTE FECHA, CIF, 



#goodVibes
#goodVibes
#VeranitoGenial
#EnLaPlayaConMiPerro

SELECT
    hashtag, count(*) as ocurrencias
FROM
    (SELECT SUBSTR(LOWER(hashtag), 2) as hashtag FROM HASHTAGS_SUCIOS) as hashtags_limpios
GROUP BY
    hashtag


TWEET:
 {
    "timestamp": "2024-06-01T12:34:56Z",
    "id": 123456,
    "text": "Me encanta el #veranito y los #goodvibes con mi #doglover",
    "owner": {
        "id": 654321,
        "name": "Juan Perez",
    },
    "device": {
        "id": 111222,
        "type": "iPhone",
        "os_version": "14.6"
    }
 }



                    Extracción de los hashtags.   Recuentos
 RDD [Tweet]            -> RDD[String]         -> Dataframe[ColumanHashtag] -> Dataframe[hashtag, Recuento]

 DataFrame[5 columnas]  -> RDD[String]         -> Dataframe[ColumanHashtag] -> Dataframe[hashtag, Recuento]


 ---

# Parquet

Parquet es binario, a diferencia de TXT/CSV que es texto plano.

Dicho de otra forma: Un DNI en un archivo csv, json, ocupa 9 bytes
En un binario, si guardo el número como número: 4 bytes
Y si acaso guardo la letra como carácter: 1 byte

Cuando trabajo con muchos datos, me interesan formatos binarios como Parquet o Avro. No de texto plano.

Dentro de los binarios tenemos Parquet y Avro (los más usados en Big Data)

Ambos empiezan igual: Esquema + Datos
Pero cambian en la forma de guardar los datos.
- AVRO orientado a filas
- PARQUET orientado a columnas


---

AVRO:
Schema:
    Nombre: String
    Edad: Int
    DNI: String
Datos:
    "Juan", 30, "12345678Z"
    "Ana",  25, "87654321X"
    "Luis", 40, "11223344A"
---

PARQUET:
Schema:
    Nombre: String
    Edad: Int
    DNI: String
Datos:
    Nombres: "Juan", "Ana", "Luis"
    Edades: 30, 25, 40
    DNIs: "12345678Z", "87654321X", "11223344A"

Si necesito procesar DATO A DATO, me interesa AVRO (es lo que solemos dejar en un KAFKA)
Para operaciones de Análisis de datos, me interesa PARQUET (es lo que solemos dejar en un HDFS o S3)
Calculame la media de las ventas para las distintas provincias.