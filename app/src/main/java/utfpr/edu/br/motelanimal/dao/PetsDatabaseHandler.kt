package utfpr.edu.br.motelanimal.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.RelTutorPet

class PetsDatabaseHandler(var context: Context) : DataBaseHandler(context, "pet") {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS pet (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "foto varchar(2048)," +
                    "nome varchar(256)," +
                    "especie INTEGER," +
                    "raca varchar(256)," +
                    "idade INTEGER," +
                    "tutor INTEGER," +
                    "dieta varchar(256)," +
                    "comportamento varchar(256)," +
                    "observacoes varchar(256)" +
                    ");"
        )
    }

    fun save(pet: Pet): Long {
        val registro = ContentValues()
        registro.put("foto", pet.foto.trim())
        registro.put("nome", pet.nome.trim())
        registro.put("especie", pet.especie)
        registro.put("raca", pet.raca.trim())
        registro.put("idade", pet.idade)
        registro.put("tutor", pet.tutor)
        registro.put("dieta", pet.dieta.trim())
        registro.put("comportamento", pet.comportamento.trim())
        registro.put("observacoes", pet.observacoes.trim())

        val idPet = super.save(registro)

        val tutorDataBaseHandler = RelTutorPetDatabaseHandler(context)
        tutorDataBaseHandler.deleteByPet(idPet.toInt())
        for (cotutor in pet.cotutor) {
            tutorDataBaseHandler.save(RelTutorPet(cotutor, idPet.toInt()))
        }

        return idPet
    }

    fun update(pet: Pet): Int {
        val registro = ContentValues()
        registro.put("foto", pet.foto.trim())
        registro.put("nome", pet.nome.trim())
        registro.put("especie", pet.especie)
        registro.put("raca", pet.raca.trim())
        registro.put("idade", pet.idade)
        registro.put("tutor", pet.tutor)
        registro.put("dieta", pet.dieta.trim())
        registro.put("comportamento", pet.comportamento.trim())
        registro.put("observacoes", pet.observacoes.trim())

        val tutorDataBaseHandler = RelTutorPetDatabaseHandler(context)
        tutorDataBaseHandler.deleteByPet(pet._id)
        for (cotutor in pet.cotutor) {
            tutorDataBaseHandler.save(RelTutorPet(cotutor, pet._id))
        }

        return super.update(registro, pet._id)
    }

    fun whereActive(): Cursor? {
        return super.findList( null, "active = 1")
    }
}