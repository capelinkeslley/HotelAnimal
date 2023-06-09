package utfpr.edu.br.motelanimal.entidades

import android.database.Cursor
import utfpr.edu.br.motelanimal.dao.DataBaseHandler

class Pet() {

    var _id: Int = 0
    var foto: String = ""
    var nome: String = ""
    var especie: Int = 0
    var raca: String = ""
    var idade: Int = 0
    var tutor: Int = 0
    var cotutor: MutableList<Int> = mutableListOf()
    var dieta: String = ""
    var comportamento: String = ""
    var observacoes: String = ""

    constructor(
        database: DataBaseHandler, cursor: Cursor
    ) : this() {
        this._id = (database.getColumn(cursor, "_id")?.toInt() ?: 0)
        this.foto = (database.getColumn(cursor, "foto") ?: "")
        this.nome = (database.getColumn(cursor, "nome") ?: "")
        this.especie = (database.getColumn(cursor, "especie")?.toInt() ?: 0)
        this.raca = (database.getColumn(cursor, "raca") ?: "")
        this.idade = (database.getColumn(cursor, "idade")?.toInt() ?: 0)
        this.tutor = (database.getColumn(cursor, "tutor")?.toInt() ?: 0)
        this.dieta = (database.getColumn(cursor, "dieta") ?: "")
        this.comportamento = (database.getColumn(cursor, "comportamento") ?: "")
        this.observacoes = (database.getColumn(cursor, "observacoes") ?: "")
    }
}