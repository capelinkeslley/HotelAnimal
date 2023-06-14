package utfpr.edu.br.motelanimal

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
import utfpr.edu.br.motelanimal.dao.QuartoDatabaseHandler
import utfpr.edu.br.motelanimal.databinding.ActivityCheckInBinding
import utfpr.edu.br.motelanimal.entidades.ControleQuarto
import utfpr.edu.br.motelanimal.entidades.Especie
import utfpr.edu.br.motelanimal.entidades.Funcionario
import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.Quarto
import utfpr.edu.br.motelanimal.entidades.getEspecieById
import utfpr.edu.br.motelanimal.entidades.getFuncionarioById
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class CheckInActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCheckInBinding.inflate(layoutInflater) }

    private val petList = mutableListOf<Pet>()
    private val quartoList = mutableListOf<Quarto>()
    private val petDatabaseHandler by lazy { PetsDatabaseHandler(this) }
    private val quartoDatabaseHandler by lazy { QuartoDatabaseHandler(this) }
    private val controleQuartoHandler by lazy { ControleQuartoDatabaseHandler(this) }
    private var pet: Pet = Pet()
    private var quarto: Quarto = Quarto()
    private var controleQuarto: ControleQuarto = ControleQuarto()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)
        binding.toolBar.setNavigationOnClickListener { finish() }

        val pets = arrayOf("pet 1", "pet 2", "pet 3", "pet 4")
        val responsaveis = arrayOf("responsável 1", "responsável 2", "responsável 3", "responsável 4")
        val quarto = arrayOf("quarto 1", "quarto 2", "quarto 3", "quarto 4")

        val adapterPets = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, pets)
        val adapterResponsavel  = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, responsaveis)
        val adapterQuarto = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, quarto)

        adapterPets.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        adapterResponsavel.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        adapterQuarto.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)

        setPets()
        setFuncionarios()
        setQuartos()

        binding.toolBarSave.setNavigationOnClickListener { onClickSave() }

        binding.btnCancel.setOnClickListener { onClickBtnCancel() }

        val cursor = controleQuartoHandler.findList()

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
            android.R.layout.simple_list_item_1,
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
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setQuartos() {
        quartoList.clear()
        val cursor = quartoDatabaseHandler.findList()
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null) {
            while (cursor.moveToNext()) {
                quartoList.add(Quarto(quartoDatabaseHandler, cursor))
            }
        }
        binding.quarto.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            quartoList.map { it.numero })

        if (controleQuarto.quarto != 0) {
            val quarto = quartoList.filter { it._id == controleQuarto.quarto }.firstOrNull()
            val index = if (quarto != null) quartoList.indexOf(quarto) else 0
            binding.quarto.setSelection(index)
        }

        binding.quarto.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                controleQuarto.quarto = quartoList[position]._id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setFuncionarios() {
        binding.responsavel.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
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
        controleQuartoHandler.save(controleQuarto)
        Toast.makeText(this, "Check-in realizado com sucesso", Toast.LENGTH_SHORT).show()
        super.finish()
    }

    override fun onPause() {
        super.onPause()
        Log.i(this.localClassName, "onPause");
    }

    override fun onStart() {
        super.onStart()
        Log.i(this.localClassName, "onStart");
    }

    override fun onStop() {
        super.onStop()
        Log.i(this.localClassName, "onStop");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(this.localClassName, "onDestroy");
    }

    private fun onClickBtnCancel(){
        Log.i(this.localClassName, "onClickBtnCancel")
        startActivity(Intent(this, MainActivity::class.java))
    }
}