package utfpr.edu.br.motelanimal.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import utfpr.edu.br.motelanimal.entidades.RelTutorPet
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class RelTutorPetDatabaseHandler(context: Context) : DataBaseHandler(context, "reltutorpet") {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS reltutorpet (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tutor INTEGER NOT NULL," +
                    "pet INTEGER NOT NULL," +
                    "FOREIGN KEY (tutor) REFERENCES tutor(id)," +
                    "FOREIGN KEY (pet) REFERENCES pet(id)" +
                    ");"
        )
    }

    fun save(rel: RelTutorPet): Long {
        val registro = ContentValues()
        registro.put("tutor", rel.tutor)
        registro.put("pet", rel.pet)

        return super.save(registro)
    }

    fun findByPet(petID: Int): List<RelTutorPet> {
        val cursor = super.findList(null, null)
        val list = mutableListOf<RelTutorPet>()
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null) {
            while (cursor.moveToNext()) {
                list.add(RelTutorPet(this, cursor))
            }
            return list.filter { it.pet == petID }
        }
        return list
    }

    fun deleteByPet(pet: Int) {
        val cursor = super.findList(null, null)
        val list = mutableListOf<RelTutorPet>()
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null) {
            while (cursor.moveToNext()) {
                list.add(RelTutorPet(this, cursor))
            }
            list.filter { it.pet == pet }.forEach { this.delete(it._id) }
        }
    }
}