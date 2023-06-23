package utfpr.edu.br.motelanimal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import utfpr.edu.br.motelanimal.adapter.ListaAdapter
import utfpr.edu.br.motelanimal.adapter.OnListaAdapterClickListener
import utfpr.edu.br.motelanimal.dao.PetsDatabaseHandler
import utfpr.edu.br.motelanimal.databinding.ActivityPetsListBinding
import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.Quarto
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class PetsListActivity : AppCompatActivity(R.layout.activity_pets_list) {

    private val binding by lazy { ActivityPetsListBinding.inflate(layoutInflater) }
    private val petsDatabaseHandler by lazy { PetsDatabaseHandler(this) }
    private val listaAdapter by lazy {
        ListaAdapter(this, findAllPets(), object : OnListaAdapterClickListener {
            override fun onItemClick(pet: Pet) {
                Log.i(this@PetsListActivity.localClassName, "Clicou no pet de ID ${pet._id}")
                val intentNew = Intent(this@PetsListActivity, PetActivity::class.java)
                intentNew.putExtra("id", pet._id)
                startActivity(intentNew)
            }

            override fun onItemQuartoClick(quarto: Quarto) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)
        binding.rview.adapter = listaAdapter
        binding.toolBar.setNavigationOnClickListener { finish() }
        binding.toolBarAdd.setNavigationOnClickListener { addNew() }
    }

    private fun addNew() {
        Log.i(this.localClassName, "addNew")
        val intentNew = Intent(this, PetActivity::class.java)
        intentNew.putExtra("id", 0)
        startActivity(intentNew)
    }

    fun findAllPets(): List<Pet> {
        val cursor = petsDatabaseHandler.findList()
        val pets = mutableListOf<Pet>()
        if (ObjectUtils.isNotEmpty(cursor)) {
            while (cursor!!.moveToNext()) {
                pets.add(Pet(petsDatabaseHandler, cursor))
            }
        }
        return pets
    }

    override fun onResume() {
        Log.i(this.localClassName, "onResume")
        super.onResume()
        listaAdapter.refresh(this.findAllPets())
    }

    override fun onPause() {
        super.onPause()
        Log.i(this.localClassName, "onPause")
    }

    override fun onStart() {
        super.onStart()
        Log.i(this.localClassName, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i(this.localClassName, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(this.localClassName, "onDestroy")
    }
}