package utfpr.edu.br.motelanimal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import utfpr.edu.br.motelanimal.databinding.SelectTutoresBinding
import utfpr.edu.br.motelanimal.entidades.Tutor

class SelectTutoresAdapter(
    private val context: Context,
    tutores: List<Tutor>,
    private val listener: OnSelectTutorAdapterClick
) : RecyclerView.Adapter<SelectTutoresAdapter.ViewHolder>() {

    private val tutors = tutores.toMutableList()

    class ViewHolder(binding: SelectTutoresBinding) : RecyclerView.ViewHolder(binding.root) {
        private val checkbox = binding.checkbox
        private val nome = binding.nome

        fun bind(tutor: Tutor) {
            nome.text = tutor.nomeCompleto
            checkbox.isChecked = tutor.checked

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SelectTutoresBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tutors.size

    fun getSelectedItens(): List<Tutor> {
        return tutors.filter { it.checked }.toList()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tutor = tutors[position]
        holder.bind(tutor)
        holder.itemView.setOnClickListener {
            listener.onItemClick(tutor)
            tutor.checked = !tutor.checked
            tutors.find { it._id == tutor._id }?.checked = tutor.checked
            notifyDataSetChanged()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(tutors: List<Tutor>) {
        this.tutors.clear()
        this.tutors.addAll(tutors)
        notifyDataSetChanged()
    }

}
