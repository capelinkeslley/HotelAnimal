package utfpr.edu.br.motelanimal.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import utfpr.edu.br.motelanimal.entidades.Tutor
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class TutorDatabaseHandler(context: Context?) : DataBaseHandler(context, "tutor") {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS tutor (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nomeCompleto varchar(256)," +
                    "cpf varchar(256)," +
                    "email varchar(256)," +
                    "telefonePrincipal varchar(256)," +
                    "cep varchar(256)," +
                    "bairro varchar(256)," +
                    "endereco varchar(256)," +
                    "cidade varchar(256)," +
                    "complemento varchar(256)," +
                    "observacoes varchar(256)" +
                    ");"
        )
    }

    fun save(tutor: Tutor): Long {
        val registro = ContentValues()
        registro.put("nomeCompleto", tutor.nomeCompleto)
        registro.put("cpf", tutor.cpf)
        registro.put("email", tutor.email)
        registro.put("telefonePrincipal", tutor.telefonePrincipal)
        registro.put("cep", tutor.cep)
        registro.put("bairro", tutor.bairro)
        registro.put("endereco", tutor.endereco)
        registro.put("cidade", tutor.cidade)
        registro.put("complemento", tutor.complemento)
        registro.put("observacoes", tutor.observacoes)

        val cursor = super.findOneBy("cpf", tutor.cpf)

        return if (ObjectUtils.isNotEmpty(cursor) && cursor!!.moveToNext()) {
            0
        } else {
            super.save(registro)
        }
    }

    fun update(tutor: Tutor): Int {
        val registro = ContentValues()
        registro.put("_id", tutor._id)
        registro.put("nomeCompleto", tutor.nomeCompleto.trim())
        registro.put("cpf", tutor.cpf.trim())
        registro.put("email", tutor.email.trim())
        registro.put("telefonePrincipal", tutor.telefonePrincipal.trim())
        registro.put("cep", tutor.cep.trim())
        registro.put("bairro", tutor.bairro.trim())
        registro.put("endereco", tutor.endereco.trim())
        registro.put("cidade", tutor.cidade.trim())
        registro.put("complemento", tutor.complemento.trim())
        registro.put("observacoes", tutor.observacoes.trim())
        return super.update(registro, tutor._id)
    }

    fun findList(): Cursor? {
        return super.findList("nomeCompleto ASC")
    }
}