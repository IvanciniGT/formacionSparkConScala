
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