package utfpr.edu.br.motelanimal.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import utfpr.edu.br.motelanimal.entidades.ControleQuarto
import utfpr.edu.br.motelanimal.entidades.RelTutorPet
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class ControleQuartoDatabaseHandler(context: Context) : DataBaseHandler(context, "controlequarto") {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS controlequarto (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "quarto INTEGER NOT NULL," +
                    "responsavel INTEGER NOT NULL, " +
                    "pet INTEGER NOT NULL," +
                    "FOREIGN KEY (quarto) REFERENCES quarto(id)," +
                    "FOREIGN KEY (pet) REFERENCES pet(id)" +
                    ");"
        )
    }

    fun save(rel: ControleQuarto): Long {
        val registro = ContentValues()
        registro.put("quarto", rel.quarto)
        registro.put("pet", rel.pet)
        registro.put("responsavel", rel.responsavel)

        return super.save(registro)
    }
}