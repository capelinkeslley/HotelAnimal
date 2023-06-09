package utfpr.edu.br.motelanimal.entidades

import android.database.Cursor
import utfpr.edu.br.motelanimal.dao.DataBaseHandler

class Tutor() {

    var _id: Int = 0
    var nomeCompleto: String = ""
    var cpf: String = ""
    var email: String = ""
    var telefonePrincipal: String = ""
    var cep: String = ""
    var bairro: String = ""
    var endereco: String = ""
    var cidade: String = ""
    var complemento: String = ""
    var observacoes: String = ""

    constructor(
        database: DataBaseHandler, cursor: Cursor
    ) : this() {
        this._id = (database.getColumn(cursor, "_id")?.toInt() ?: 0)
        this.nomeCompleto = (database.getColumn(cursor, "nomeCompleto") ?: "")
        this.cpf = (database.getColumn(cursor, "cpf") ?: "")
        this.email = (database.getColumn(cursor, "email") ?: "")
        this.telefonePrincipal = (database.getColumn(cursor, "telefonePrincipal") ?: "")
        this.cep = (database.getColumn(cursor, "cep") ?: "")
        this.bairro = (database.getColumn(cursor, "bairro") ?: "")
        this.endereco = (database.getColumn(cursor, "endereco") ?: "")
        this.cidade = (database.getColumn(cursor, "cidade") ?: "")
        this.complemento = (database.getColumn(cursor, "complemento") ?: "")
        this.observacoes = (database.getColumn(cursor, "observacoes") ?: "")
    }
}