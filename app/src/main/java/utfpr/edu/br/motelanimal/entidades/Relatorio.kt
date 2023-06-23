package utfpr.edu.br.motelanimal.entidades

import android.database.Cursor
import utfpr.edu.br.motelanimal.dao.DataBaseHandler

class Relatorio() {

    var _id: Int = 0
    var quarto: Int = 0
    var titulo: String = ""
    var relatorio: String = ""

    constructor(
        database: DataBaseHandler, cursor: Cursor
    ) : this() {
        this._id = (database.getColumn(cursor, "_id")?.toInt() ?: 0)
        this.quarto = (database.getColumn(cursor, "quarto")?.toInt() ?: 0)
        this.titulo = (database.getColumn(cursor, "titulo") ?: "")
        this.relatorio = (database.getColumn(cursor, "relatorio") ?: "")
    }
}