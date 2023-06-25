package utfpr.edu.br.motelanimal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import utfpr.edu.br.motelanimal.adapter.ListaQuartoAdapter
import utfpr.edu.br.motelanimal.adapter.OnListaAdapterClickListener
import utfpr.edu.br.motelanimal.databinding.ActivityQuartoListBinding
import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.Quarto

class QuartoListActivity :  AppCompatActivity(R.layout.activity_quarto_list) {
    private val quartoAdapter by lazy {
        ListaQuartoAdapter(this, Quarto.values(), object : OnListaAdapterClickListener{
            override fun onItemClick(pet: Pet) {
                TODO("Not yet implemented")
            }

            override fun onItemQuartoClick(quarto: Quarto) {
                Log.i(this@QuartoListActivity.localClassName, "Clicou no pet de ID ${quarto._id}")
                val intentNew = Intent(this@QuartoListActivity, QuartosActivity::class.java)
                intentNew.putExtra("id", quarto._id)
                startActivity(intentNew)
            }
        })
    }
    private val binding by lazy { ActivityQuartoListBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)

        binding.rview.adapter = quartoAdapter

        binding.toolBar.setNavigationOnClickListener { finish() }
    }
}