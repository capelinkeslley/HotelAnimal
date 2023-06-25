package utfpr.edu.br.motelanimal.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import utfpr.edu.br.motelanimal.QuartoListActivity
import utfpr.edu.br.motelanimal.databinding.CardQuartosBinding
import utfpr.edu.br.motelanimal.entidades.Quarto
import utfpr.edu.br.motelanimal.entidades.getEspecieById

class ListaQuartoAdapter(
    private val context: QuartoListActivity,
    quartos: Array<Quarto>,
    private val listener: OnListaAdapterClickListener
) : RecyclerView.Adapter<ListaQuartoAdapter.ViewHolder>() {

    private val quartos = quartos.toMutableList()

    class ViewHolder(binding: CardQuartosBinding) : RecyclerView.ViewHolder(binding.root) {
        private val numero = binding.numero
        private val especie = binding.especie
        private val foto = binding.status

        fun bind(quarto: Quarto) {
            numero.text = quarto.numero.toString()
            val especieNome = getEspecieById(quarto.especie._id).nome


            especie.text = especieNome
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardQuartosBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = quartos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quarto = quartos[position]
        holder.bind(quarto)
        holder.itemView.setOnClickListener { listener.onItemQuartoClick(quarto) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(quartos: List<Quarto>) {
        this.quartos.clear()
        this.quartos.addAll(quartos)
        notifyDataSetChanged()
    }

}
