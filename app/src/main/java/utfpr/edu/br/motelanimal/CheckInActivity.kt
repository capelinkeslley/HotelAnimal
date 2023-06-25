package utfpr.edu.br.motelanimal

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import utfpr.edu.br.motelanimal.dao.ControleQuartoDatabaseHandler
import utfpr.edu.br.motelanimal.dao.PetsDatabaseHandler
import utfpr.edu.br.motelanimal.databinding.ActivityCheckInBinding
import utfpr.edu.br.motelanimal.entidades.ControleQuarto
import utfpr.edu.br.motelanimal.entidades.Funcionario
import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.Quarto
import utfpr.edu.br.motelanimal.entidades.getFuncionarioById
import utfpr.edu.br.motelanimal.entidades.getQuartoByEspecie
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class CheckInActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCheckInBinding.inflate(layoutInflater) }

    private val petList = mutableListOf<Pet>()
    private val petDatabaseHandler by lazy { PetsDatabaseHandler(this) }
    private val controleQuartoDatabaseHandler by lazy { ControleQuartoDatabaseHandler(this) }
    private val controleQuartoHandler by lazy { ControleQuartoDatabaseHandler(this) }
    private var controleQuarto: ControleQuarto = ControleQuarto()
<<<<<<< Updated upstream
=======
    private val quartosDisponiveis = mutableListOf<Int>()
>>>>>>> Stashed changes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)
        binding.toolBar.setNavigationOnClickListener { finish() }

        setPets()
        setFuncionarios()
        binding.toolBarSave.setNavigationOnClickListener { onClickSave() }

        binding.btnCancel.setOnClickListener { onClickBtnCancel() }
    }

    private fun setPets() {
        petList.clear()
        val cursor = petDatabaseHandler.findList()
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null) {
            while (cursor.moveToNext()) {
                petList.add(Pet(petDatabaseHandler, cursor))
            }
        }
        binding.pet.adapter = ArrayAdapter(
            this,
            R.layout.simple_list_item_1,
            petList.map { it.nome })

        if (controleQuarto.pet != 0) {
            val pet = petList.filter { it._id == controleQuarto.pet }.firstOrNull()
            val index = if (pet != null) petList.indexOf(pet) else 0
            binding.pet.setSelection(index)
        }

        binding.pet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                controleQuarto.pet = petList[position]._id

                setQuartos(petList[position].especie)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setQuartos(especie: Int) {
        val cursor = controleQuartoHandler.whereActive("active = 1")
        val quartosIndisponiveis: MutableList<Int> = mutableListOf()
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null) {
            while (cursor.moveToNext()) {
                quartosIndisponiveis.add(ControleQuarto(controleQuartoDatabaseHandler, cursor).quarto)
            }
        }
        val quartos: List<Quarto> = getQuartoByEspecie(especie, quartosIndisponiveis)

        binding.quarto.adapter = ArrayAdapter(
            this,
            R.layout.simple_list_item_1,
            quartos.map { it.name })

        if (controleQuarto.quarto != 0) {
            binding.quarto.setSelection(controleQuarto.quarto - 1)
        }
        binding.quarto.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                controleQuarto.quarto = quartos.get(position)._id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    private fun setFuncionarios() {
        binding.responsavel.adapter = ArrayAdapter(
            this,
            R.layout.simple_list_item_1,
            Funcionario.values().filter { it._id != 0 })

        if (controleQuarto.responsavel != 0) {
            binding.responsavel.setSelection(controleQuarto.responsavel - 1)
        }
        binding.responsavel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                controleQuarto.responsavel = getFuncionarioById(position + 1)._id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun onClickSave() {
        Log.i(this.localClassName, "onClickSave")
        controleQuarto.active = 1
        controleQuartoHandler.save(controleQuarto)
        Toast.makeText(this, "Check-in realizado com sucesso", Toast.LENGTH_SHORT).show()

        super.finish()
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

    private fun onClickBtnCancel() {
        Log.i(this.localClassName, "onClickBtnCancel")
        startActivity(Intent(this, MainActivity::class.java))
    }
}