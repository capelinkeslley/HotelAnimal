package utfpr.edu.br.motelanimal.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import utfpr.edu.br.motelanimal.entidades.Quarto
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class QuartoDatabaseHandler(context: Context?) : DataBaseHandler(context, "quarto") {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS quarto (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "numero INTERGER," +
                    "especie INTEGER," +
                    "disponivel boolean "+
                    ");"
        )

        db.execSQL(
            "INSERT INTO quarto(numero, especie, disponivel) values (1, 0, true);"
        )
        db.execSQL(
            "INSERT INTO quarto(numero, especie, disponivel) values (2, 1, true);"
        )
        db.execSQL(
            "INSERT INTO quarto(numero, especie, disponivel) values (3, 2, true);"
        )
        db.execSQL(
            "INSERT INTO quarto(numero, especie, disponivel) values (4, 3, true);"
        )
        db.execSQL(
            "INSERT INTO quarto(numero, especie, disponivel) values (5, 0, true);"
        )
        db.execSQL("INSERT INTO quarto(numero, especie, disponivel) values(6, 1, true);")
    }

    fun save(quarto: Quarto): Long {
        val registro = ContentValues()
        registro.put("numero", quarto.numero)
        registro.put("especie", quarto.especie)
        registro.put("disponivel", quarto.disponivel)

        val cursor = super.findOneBy("numero", quarto.numero.toString())

        return if (ObjectUtils.isNotEmpty(cursor) && cursor!!.moveToNext()) {
            0
        } else {
            super.save(registro)
        }
    }

    fun update(quarto: Quarto): Int {
        val registro = ContentValues()
        registro.put("_id", quarto._id)
        registro.put("numero", quarto.numero)
        registro.put("especie", quarto.especie)
        registro.put("disponivel", quarto.disponivel)

        return super.update(registro, quarto._id)
    }

    fun findList(): Cursor? {
        return super.findList("numero ASC")
    }
}