package utfpr.edu.br.motelanimal.entidades

import android.database.Cursor
import utfpr.edu.br.motelanimal.dao.DataBaseHandler

class RelTutorPet() {

    var _id: Int = 0
    var tutor: Int = 0
    var pet: Int = 0

    constructor(
        database: DataBaseHandler, cursor: Cursor
    ) : this() {
        this._id = database.getColumn(cursor, "_id")!!.toInt()
        this.tutor = database.getColumn(cursor, "tutor")!!.toInt()
        this.pet = database.getColumn(cursor, "pet")!!.toInt()
    }

    constructor(tutor: Int, pet: Int) : this() {
        this.tutor = tutor
        this.pet = pet
    }
}