package utfpr.edu.br.motelanimal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import utfpr.edu.br.motelanimal.databinding.CardPetsBinding
import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.getEspecieById
import utfpr.edu.br.motelanimal.utils.tryLoadImage

class ListaAdapter(
    private val context: Context,
    pets: List<Pet>,
    private val listener: OnListaAdapterClickListener
) : RecyclerView.Adapter<ListaAdapter.ViewHolder>() {

    private val pets = pets.toMutableList()

    class ViewHolder(binding: CardPetsBinding) : RecyclerView.ViewHolder(binding.root) {
        private val nome = binding.nome
        private val especie = binding.especie
        private val foto = binding.foto

        fun bind(pet: Pet) {
            nome.text = pet.nome
            val especieNome = getEspecieById(pet.especie).nome
            val racaNome = pet.raca

            val textoEspecie = if (racaNome != "") {
                "$especieNome - $racaNome"
            } else {
                especieNome
            }

            especie.text = textoEspecie
            foto.tryLoadImage(pet.foto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardPetsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = pets.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pet = pets[position]
        holder.bind(pet)
        holder.itemView.setOnClickListener { listener.onItemClick(pet) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(pets: List<Pet>) {
        this.pets.clear()
        this.pets.addAll(pets)
        notifyDataSetChanged()
    }

}
