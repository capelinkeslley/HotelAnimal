package utfpr.edu.br.motelanimal.entidades

import android.database.Cursor
import utfpr.edu.br.motelanimal.dao.DataBaseHandler

class Quarto () {

    var _id: Int = 0
    var especie: Int = 0
    var disponivel: Boolean = true
    var numero: Int = 0


    constructor(
        database: DataBaseHandler, cursor: Cursor
    ) : this() {
        this._id = (database.getColumn(cursor, "_id")?.toInt() ?: 0)
        this.especie = (database.getColumn(cursor, "especie")?.toInt() ?: 0)
        this.disponivel = (database.getColumn(cursor, "disponivel")?.toBoolean() ?: true)
        this.numero = (database.getColumn(cursor, "numero")?.toInt() ?: 0)
    }
}