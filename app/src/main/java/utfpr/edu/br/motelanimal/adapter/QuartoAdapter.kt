package utfpr.edu.br.motelanimal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import utfpr.edu.br.motelanimal.R
import utfpr.edu.br.motelanimal.entidades.Quarto

class QuartoAdapter(private val listaObjetos: Array<Quarto>) : RecyclerView.Adapter<QuartoAdapter.ObjetoViewHolder>() {

    inner class ObjetoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val numeroTextView: TextView = itemView.findViewById(R.id.numero)
        private val especieTextView: TextView = itemView.findViewById(R.id.especie)

        fun bind(objeto: Map<String, String>) {
            numeroTextView.text = objeto["numero"]
            especieTextView.text = objeto["especie"]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjetoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_quarto, parent, false)
        return ObjetoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ObjetoViewHolder, position: Int) {
        val objeto = listaObjetos[position]
//        holder.bind(objeto)
    }

    override fun getItemCount(): Int {
        return listaObjetos.size
    }
}
