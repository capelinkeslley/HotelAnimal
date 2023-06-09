package utfpr.edu.br.motelanimal.entidades

enum class Especie(val _id: Int, val nome: String) {
    DESCONHECIDO(0, "Desconhecida"),
    CACHORRO(1, "Cachorro"),
    GATO(2, "Gato"),
    URSO(3, "Urso")
}

fun getEspecieById(id: Int): Especie {
    return Especie.values().find { it._id == id } ?: Especie.DESCONHECIDO
}
