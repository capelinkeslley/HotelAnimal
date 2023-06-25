package utfpr.edu.br.motelanimal.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import utfpr.edu.br.motelanimal.entidades.Relatorio

class RelatoriosDatabaseHandler(context: Context?) : DataBaseHandler(context, "relatorios") {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS relatorios (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "quarto integer not null," +
                    "titulo varchar(256)," +
                    "relatorio varchar(1024)" +
                    ");"
        )
    }

    fun save(relatorio: Relatorio): Long {
        val registro = ContentValues()
        registro.put("quarto", relatorio.quarto)
        registro.put("titulo", relatorio.titulo.trim())
        registro.put("relatorio", relatorio.relatorio.trim())
        return super.save(registro)
    }

    fun update(relatorio: Relatorio): Int {
        val registro = ContentValues()
        registro.put("_id", relatorio._id)
        registro.put("quarto", relatorio.quarto)
        registro.put("titulo", relatorio.titulo.trim())
        registro.put("relatorio", relatorio.relatorio.trim())
        return super.update(registro, relatorio._id)
    }

    fun findList(query: String? = null): Cursor? {
        return super.findList("titulo ASC", query)
    }
}