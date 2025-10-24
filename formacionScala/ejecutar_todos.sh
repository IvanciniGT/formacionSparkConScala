#!/bin/bash

echo "========================================"
echo "EJECUTANDO TODOS LOS EJEMPLOS DE SCALA"
echo "========================================"

# Lista de archivos y sus nombres de objetos principales
declare -A archivos=(
    ["1-basico"]="BasicoScala"
    ["2-variables"]="VariablesYTipos"
    ["3-imperativo"]="ProgramacionImperativa"
    ["4-procedural"]="ProgramacionProcedural"
    ["5-funcional"]="ProgramacionFuncional"
    ["6-textos"]="ManipulacionTextos"
    ["7-fechas"]="FechasYTiempos"
    ["8-colecciones"]="ColeccionesBasicas"
    ["9-streams"]="StreamsYEvaluacion"
    ["10-mapreduce"]="ConceptosMapReduce"
    ["11-clases"]="ClasesYProcesamiento"
)

# Función para ejecutar un archivo
ejecutar_archivo() {
    local archivo=$1
    local clase=$2
    
    echo ""
    echo "========================================="
    echo "EJECUTANDO: $archivo"
    echo "CLASE: $clase"
    echo "========================================="
    
    mvn exec:java -Dexec.mainClass="$clase" -q
    
    if [ $? -eq 0 ]; then
        echo "✅ $archivo ejecutado correctamente"
    else
        echo "❌ Error ejecutando $archivo"
        return 1
    fi
}

# Compilar primero
echo "Compilando proyecto..."
mvn compile -q

if [ $? -ne 0 ]; then
    echo "❌ Error compilando el proyecto"
    exit 1
fi

echo "✅ Compilación exitosa"

# Ejecutar todos los archivos
errores=0
for archivo in "${!archivos[@]}"; do
    ejecutar_archivo "$archivo" "${archivos[$archivo]}"
    if [ $? -ne 0 ]; then
        errores=$((errores + 1))
    fi
    
    # Pausa entre ejecuciones
    echo ""
    echo "Presiona Enter para continuar con el siguiente archivo (o Ctrl+C para salir)..."
    read -r
done

echo ""
echo "========================================"
echo "RESUMEN FINAL"
echo "========================================"
echo "Total archivos: ${#archivos[@]}"
echo "Errores: $errores"

if [ $errores -eq 0 ]; then
    echo "🎉 ¡TODOS LOS ARCHIVOS FUNCIONAN CORRECTAMENTE!"
    echo ""
    echo "🎓 El proyecto de formación está listo para usar:"
    echo "   - Conceptos básicos de Scala ✅"
    echo "   - Programación funcional ✅" 
    echo "   - Colecciones y operaciones ✅"
    echo "   - MapReduce patterns ✅"
    echo "   - Clases y procesamiento ✅"
    echo ""
    echo "🚀 Los estudiantes están listos para Apache Spark!"
else
    echo "⚠️  Se encontraron $errores errores"
fi

echo ""
echo "Para ejecutar archivos individuales:"
echo "mvn exec:java -Dexec.mainClass=\"NombreClase\""
echo ""
echo "Ejemplo:"
for archivo in "${!archivos[@]}"; do
    echo "mvn exec:java -Dexec.mainClass=\"${archivos[$archivo]}\""
    break
done