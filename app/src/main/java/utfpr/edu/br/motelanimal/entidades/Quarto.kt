package utfpr.edu.br.motelanimal.entidades

import utfpr.edu.br.motelanimal.dao.ControleQuartoDatabaseHandler
import utfpr.edu.br.motelanimal.dao.PetsDatabaseHandler

enum class Quarto(val _id: Int, val numero: Int, val especie: Especie) {
    _000(10, 303, Especie.DESCONHECIDO),
    _101(1, 101, Especie.CACHORRO),
    _102(2, 102, Especie.URSO),
    _103(3, 103, Especie.URSO),
    _201(4, 201, Especie.CACHORRO),
    _202(5, 202, Especie.GATO),
    _203(6, 203, Especie.GATO),
    _301(7, 301, Especie.URSO),
    _302(8, 302, Especie.GATO),
    _303(9, 303, Especie.CACHORRO),
}

fun geQuartoById(id: Int): Quarto {
    return Quarto.values().find { it._id == id } ?: Quarto._000
}

fun getQuartoByEspecie(especie: Int, except: MutableList<Int>): List<Quarto> {
    return Quarto.values().filterNot { it._id in except }
        .filter { it.especie._id == especie }
}


