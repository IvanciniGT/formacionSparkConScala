object MapReduce {

    def main(args: Array[String]): Unit = {













        val otros_numeros = LazyList(1,2,3,4,5)
        val resultado = otros_numeros
            .map( (numero:Int) => numero * 2  )
            .map( numero => numero - 5  )
            .map( _ / 2  )
            .filter( _ >= 0)
            .toList





        println(resultado)

    }

}

