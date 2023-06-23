package utfpr.edu.br.motelanimal.entidades

class RelatoriosListItem() {

    var _id: Int = 0
    var titulo: String = ""
    var quarto: String = ""

    constructor(titulo: String, quarto: String) : this() {
        this.titulo = titulo
        this.quarto = quarto
    }

}