
def saluda():            # Definir una función
    print("Hola")

saluda()                 # Llamar a la función
saluda()                 # Llamar a la función nuevamente

mi_variable = "hola"     # Variable que apunta a un string
print(mi_variable)       # Imprimir el valor de la variable

mi_variable = saluda     # Variable que apunta a una función
print(mi_variable)       # Imprimir la referencia de la función

mi_variable()            # Llamar a la función a través de la variable

print("hola".upper())  # Llamar a un método de string
texto="hola"
print(texto.upper())    # Llamar a un método de string a través de una

###################################################

def transformar_texto(texto):
    return texto.lower()

#########

def funcion_generadora_de_saludos_formales(nombre):
    return f"Buenos días, {nombre}. Es un placer saludarle."

print(funcion_generadora_de_saludos_formales("Menchu"))

def funcion_generadora_de_saludos_informales(nombre):
    return f"¡Hola, {nombre}! ¿Qué tal?"

def imprimir_saludo(funcion_generadora_de_saludos, nombre):
    saludo = funcion_generadora_de_saludos(nombre)
    print(saludo)


def imprimir_saludos(funcion_generadora_de_saludos, muchos_nombres):
    for nombre in muchos_nombres:
        saludo = funcion_generadora_de_saludos(nombre)
        print(saludo)

imprimir_saludo(funcion_generadora_de_saludos_formales, "Señor Pérez")
imprimir_saludo(funcion_generadora_de_saludos_informales, "Ana")

# Qué me permite, inyectar lógica a una función desde fuera.

nombres = ["Ana", "Luis", "María", "Pedro"]
imprimir_saludos(funcion_generadora_de_saludos_formales, nombres)
imprimir_saludos(funcion_generadora_de_saludos_informales, nombres)

# OTRO EJEMPLO
def doblar(numero):          # Definir una funcion que dobla un número
    return numero * 2

def triplar(numero):          # Definir una funcion que triplica un número
    return numero * 3

def imprimir_resultado_de_doblar(lista_numeros):
    for numero in lista_numeros:
        resultado = doblar(numero)   # Llamar a la función doblar
        print(f"El doble de {numero} es {resultado}")

def imprimir_resultado_de_triplar(lista_numeros):
    for numero in lista_numeros:
        resultado = triplar(numero)   # Llamar a la función doblar
        print(f"El triple de {numero} es {resultado}")

    # Para cada número de la lista de números, aplicar la operación triplar, y su resultado imprimirlo.

def imprimir_resultado_de_aplicar_operacion(operacion, lista_numeros):
    for numero in lista_numeros:
        resultado = operacion(numero)   # Llamar a la función doblar
        print(f"El resultado de aplicar la operación sobre {numero} es {resultado}")

    # Para cada número de la lista de números, aplicar la operación recibida como parámetro, y su resultado imprimirlo.

numeros = [1, 2, 3, 4, 5]
imprimir_resultado_de_doblar(numeros)
imprimir_resultado_de_triplar(numeros)
imprimir_resultado_de_aplicar_operacion(doblar, numeros)
imprimir_resultado_de_aplicar_operacion(triplar, numeros)

def cuatriplicar(numero):
    return numero * 4


imprimir_resultado_de_aplicar_operacion(cuatriplicar, numeros)


def doblar_lista(lista_numeros):
    dobles = []
    for numero in lista_numeros:
        el_doble = doblar(numero)
        dobles.append(el_doble)
    return dobles

    # Dada una lista de numeros, devuelve otra lista donde cada elemento es el resultado de doblar el elemento correspondiente de la lista original.
    # [1, 2, 3]
    #  v  v  v      doblar (x2)
    # [2, 4, 6]

print(doblar_lista([1, 2, 3, 4, 5]))  



def aplicar_operacion_a_lista(operacion, lista): # = FUNCION MAP de MapReduce
    resultados = []
    for dato in lista:
        resultado = operacion(dato)
        resultados.append(resultado)
    return resultados

    # Dada una lista de datos, devuelve otra lista donde cada elemento es el resultado de aplicar la operación recibida como parámetro, al elemento correspondiente de la lista original.
    # Transformar cada elemento de la lista original, mediante la operación recibida como parámetro.... dando lugar a una nueva lista con el resultado de esas transformaciones.
    # Y sabéis que función acabamos de definir? LA FUNCION MAP!!!!!

print(aplicar_operacion_a_lista(doblar, [1, 2, 3, 4, 5]))
print(aplicar_operacion_a_lista(triplar, [1, 2, 3, 4, 5]))
print(aplicar_operacion_a_lista(cuatriplicar, [1, 2, 3, 4, 5]))

def poner_en_mayusculas(texto):
    return texto.upper()

print(aplicar_operacion_a_lista(poner_en_mayusculas, ["hola", "adiós", "hasta luego"]))

# Python trae esa función de serie, se llama map()
print(list(map(doblar, [1, 2, 3, 4, 5])))
print(list(map(poner_en_mayusculas, ["hola", "adiós ", "hasta luego"])))

# Es una función que existe en cualquier lenguaje que soporte programación funcional.
# La función map es una función de orden que transformar cada elemento de la lista original,
# mediante la operación recibida como parámetro (lo que llamamos la función de mapeo).... dando lugar a una nueva lista con el resultado de esas transformaciones.

# Como digo... esa función existe en python... y en scala, y en java... No tiene nada que ver con Spark!


print(aplicar_operacion_a_lista(transformar_texto, ["hola", "adiós", "hasta luego"]))
# En estos casos, cuando el definir una función no aporta legibilidad al código, ni quiero reutilizarla en otro sitio...
# Hay una forma alternativa de definir funciones que me ofrecen todos los lenguajes que soportan programación funcional: LAS EXPRESIONES LAMBDA

# Qué es una expresión lambda?

print("hola")                  # Statement/Instrucción/ Sentencia(FRASE o ORACION).. Esta linea es una triste FRASE en PYTHON
numero = 7 * 9                 # Otro statement.. otra triste FRASE en PYTHON
         #####                   Eso es una expresión: 7 * 9          Una expresión es un trozo de código que tiene un valor.

# De entrada una expresión, será un trozo de código que devuelve una función anónima declarada dentro de la propia expresión.
 # Esto es una expresión lambda... Me devuelve una referencia a una función que acabo de definir, sin nombre, dentro de la propia expresión.

print(aplicar_operacion_a_lista(lambda texto: texto.lower(), ["hola", "adiós", "hasta luego"]))
# Este es el código que vamos a encontrar en Spark con Scala.... solo que un línea de código en spark/scala, 
# REPITO, en una única línea de código en spark/scala, tendremos 10 expresiones como esa.
# En Scala la sintaxis para definir una expresión lambda es diferente:

# En realidad la de python es muy rara... en otros lenguajes es más parecida a la de Scala.. y se hace con el operador "->"

# lambda texto : texto.lower()    # Python
# texto => texto.toLowerCase()    # Scala

# Todos los programas que vamos a montar en Apache Spark con Scala van a tener UNA UNICA LÍNEA DE CÓDIGO
# que va a ser una concatenación de 10, 20, 30,... funciones de tipo map, que reciben dentro expresiones lambda.

# Oye... de eso va la programación MAPREDUCE .. y Spark es un motor de procesamiento MAPREDUCE que trabaja en memoria (in-memory)