package utfpr.edu.br.motelanimal.entidades

import android.database.Cursor
import utfpr.edu.br.motelanimal.dao.DataBaseHandler

enum class Funcionario(val _id: Int, val nome: String) {

    JOAO(0, "João"),
    FELIPE(1, "Felipe"),
    VANESSA(2, "Vanessa"),
    MARIA(3, "Maria")
}

fun getFuncionarioById(id: Int): Funcionario {
    return Funcionario.values().find { it._id == id } ?: Funcionario.JOAO
}