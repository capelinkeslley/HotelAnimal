package utfpr.edu.br.motelanimal.adapter

import utfpr.edu.br.motelanimal.entidades.Pet

interface OnListaAdapterClickListener {
    fun onItemClick(pet: Pet)
}