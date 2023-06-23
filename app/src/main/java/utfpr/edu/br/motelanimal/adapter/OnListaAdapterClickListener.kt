package utfpr.edu.br.motelanimal.adapter

import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.Quarto

interface OnListaAdapterClickListener {
    fun onItemClick(pet: Pet)
    fun onItemQuartoClick(quarto: Quarto)
}