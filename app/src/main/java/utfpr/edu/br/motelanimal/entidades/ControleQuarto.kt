package utfpr.edu.br.motelanimal.entidades

import android.database.Cursor
import utfpr.edu.br.motelanimal.dao.DataBaseHandler

class ControleQuarto() {

    var _id: Int = 0
    var quarto: Int = 0
    var pet: Int = 0
    var responsavel: Int = 0

    constructor(
        database: DataBaseHandler, cursor: Cursor
    ) : this() {
        this._id = database.getColumn(cursor, "_id")!!.toInt()
        this.quarto = database.getColumn(cursor, "quarto")!!.toInt()
        this.responsavel = database.getColumn(cursor, "responsavel")!!.toInt()
        this.pet = database.getColumn(cursor, "pet")!!.toInt()
    }

    constructor(quarto: Int, pet: Int, responsavel: Int) : this() {
        this.quarto = quarto
        this.responsavel = responsavel
        this.pet = pet
    }
}