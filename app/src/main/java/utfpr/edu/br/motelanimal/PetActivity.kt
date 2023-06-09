package utfpr.edu.br.motelanimal

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import utfpr.edu.br.motelanimal.dao.PetsDatabaseHandler
import utfpr.edu.br.motelanimal.dao.TutorDatabaseHandler
import utfpr.edu.br.motelanimal.databinding.ActivityPetBinding
import utfpr.edu.br.motelanimal.databinding.BuscarImagemBinding
import utfpr.edu.br.motelanimal.entidades.Especie
import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.Tutor
import utfpr.edu.br.motelanimal.entidades.getEspecieById
import utfpr.edu.br.motelanimal.utils.ObjectUtils
import utfpr.edu.br.motelanimal.utils.tryLoadImage

class PetActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPetBinding.inflate(layoutInflater) }
    private val tutorDatabaseHandler by lazy { TutorDatabaseHandler(this) }
    private val petsDatabaseHandler by lazy { PetsDatabaseHandler(this) }
    private val tutoresList = mutableListOf<Tutor>()

    private var pet: Pet = Pet()
    private var isFormDirt: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)

        pet._id = intent.getIntExtra("id", 0)

        if (pet._id != 0) {
            binding.toolBar.title = getString(R.string.editar)
            buscaDadosPet()

            binding.toolBarExcluir.setNavigationOnClickListener { onClickExcluir() }
        } else {
            binding.toolBarExcluir.visibility = View.GONE
        }

        binding.toolBar.setNavigationOnClickListener { onClickReturn() }
        binding.toolBarSave.setNavigationOnClickListener { onClickSave() }
        binding.foto.setOnClickListener { onClickImage() }

        setEspecies()
        setTutores()

        bindValidationOnInputs()
    }

    private fun setEspecies() {
        binding.especie.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            Especie.values().filter { it._id != 0 })

        if (pet.especie != 0) {
            binding.especie.setSelection(pet.especie - 1)
        }
        binding.especie.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                pet.especie = getEspecieById(position + 1)._id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setTutores() {
        tutoresList.clear()
        val cursor = tutorDatabaseHandler.findList()
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null) {
            while (cursor.moveToNext()) {
                tutoresList.add(Tutor(tutorDatabaseHandler, cursor))
            }
        }
        binding.tutor.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            tutoresList.map { it.nomeCompleto })

        if (pet.tutor != 0) {
            val tutorPet = tutoresList.filter { it._id == pet.tutor }.firstOrNull()
            val index = if (tutorPet != null) tutoresList.indexOf(tutorPet) else 0
            binding.tutor.setSelection(index)
        }

        binding.tutor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                pet.tutor = tutoresList[position]._id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun onClickImage() {
        Log.i(this.localClassName, "onClickImage")
        val binding = BuscarImagemBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Imagem")
            .setView(binding.root)
            .setPositiveButton("Confirmar") { dialog, _ ->
                this.binding.foto.tryLoadImage(binding.url.text.toString())
                this.pet.foto = binding.url.text.toString()
                this.isFormDirt = true
                dialog.dismiss()
            }
            .setNeutralButton("Fechar", null)
            .create()

        binding.btnCarregar.setOnClickListener {
            Log.i(this.localClassName, "onClickCarregar ${binding.url.text}")
            binding.urlLayout.error = null
            if (ObjectUtils.isEmpty(binding.url.text)) {
                binding.urlLayout.error = "URL Inválida"
            }

            binding.imageView.tryLoadImage(binding.url.text.toString())
        }

        dialog.show()
    }

    private fun buscaDadosPet() {
        val cursor = petsDatabaseHandler.findOneBy("_id", pet._id.toString())
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null && cursor.moveToNext()) {
            pet = Pet(petsDatabaseHandler, cursor)
            binding.foto.tryLoadImage(pet.foto)
            binding.nome.setText(pet.nome)
            setEspecies()
            binding.raca.setText(pet.raca)
            binding.idade.setText(pet.idade.toString())
            setTutores()
            binding.dieta.setText(pet.dieta)
            binding.comportamento.setText(pet.comportamento)
            binding.observacoes.setText(pet.observacoes)
        }
    }

    private fun onClickReturn() {
        Log.i(this.localClassName, "onClickReturn")
        intent.putExtra("op", "close")
        finish()
    }

    private fun onClickSave() {
        Log.i(this.localClassName, "onClickSave")
        if (validateForm()) {
            pet.dieta = binding.dieta.text.toString().trim()
            pet.comportamento = binding.comportamento.text.toString().trim()
            pet.observacoes = binding.observacoes.text.toString().trim()

            intent.putExtra("op", "save")
            finish()
        }
    }

    private fun onClickExcluir() {
        Log.i(this.localClassName, "onClickExcluir")
        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setMessage("Você tem certeza que deseja excluir?\nA operação não pode ser revertida!")
            .setPositiveButton("Confirmar") { dialog, _ ->
                dialog.dismiss()
                if (petsDatabaseHandler.delete(pet._id) != 0) {
                    Toast.makeText(this, "Registro excluido com sucesso", Toast.LENGTH_SHORT).show()
                    super.finish()
                } else {
                    Toast.makeText(this, "Erro ao excluir registro!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun bindValidationOnInputs() {
        val fields = arrayOf(
            binding.nome,
            binding.raca,
            binding.idade,
        )

        fields.forEach { field ->
            Log.i(this.localClassName, "Relizando setOnFocusChangeListener no campo ${field.hint}")
            field.setOnFocusChangeListener { _, hasFocus ->
                Log.i(
                    this.localClassName,
                    "${field.hint} ${if (hasFocus) "recebeu" else "perdeu"} foco"
                )
                if (!hasFocus) {
                    Log.i(this.localClassName, "Validando campo ${field.hint}")
                    validateField(field)
                }
            }
        }
    }

    private fun validateField(field: View) {
        when (field) {
            binding.nome -> validateNome()
            binding.raca -> validateRaca()
            binding.idade -> validateIdade()
        }
    }

    private fun validateForm(): Boolean {
        return validateNome() && validateRaca() && validateIdade() && isFormDirt
    }

    private fun validateNome(): Boolean {
        isFormDirt = true
        pet.nome = binding.nome.text.toString()
        binding.nomeLayout.error = null
        val notIsValid = ObjectUtils.isEmpty(pet.nome) || pet.nome.length < 3
        if (notIsValid) {
            binding.nomeLayout.error = "Campo Inválido"
        } else {
            pet.nome = pet.nome.substring(0, 1).uppercase() + pet.nome.substring(1)
        }
        Log.i(
            this.localClassName,
            "Campo \"nome\" é ${if (notIsValid) "Inválido" else "Válido"}"
        )
        return !notIsValid
    }

    private fun validateRaca(): Boolean {
        isFormDirt = true
        pet.raca = binding.raca.text.toString()
        binding.racaLayout.error = null
        val notIsValid = ObjectUtils.isEmpty(pet.raca) || pet.raca.length < 3
        if (notIsValid) {
            binding.racaLayout.error = "Campo Inválido"
        } else {
            pet.raca = pet.raca.substring(0, 1).uppercase() + pet.raca.substring(1)
        }
        Log.i(
            this.localClassName,
            "Campo \"raca\" é ${if (notIsValid) "Inválido" else "Válido"}"
        )
        return !notIsValid
    }

    private fun validateIdade(): Boolean {
        isFormDirt = true
        pet.idade = binding.idade.text.toString().toInt()
        binding.idadeLayout.error = null
        val notIsValid = ObjectUtils.isEmpty(pet.idade) || pet.idade < 0
        if (notIsValid) {
            binding.idadeLayout.error = "Campo Inválido"
        }
        Log.i(
            this.localClassName,
            "Campo \"idade\" é ${if (notIsValid) "Inválido" else "Válido"}"
        )
        return !notIsValid
    }

    override fun finish() {
        Log.i(this.localClassName, "finish")
        if (isFormDirt) {
            if (intent.getStringExtra("op").equals("save")) {
                if (pet._id == 0) {
                    petsDatabaseHandler.save(pet)
                    Toast.makeText(this, "Pet cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                    super.finish()
                } else {
                    petsDatabaseHandler.update(pet)
                    Toast.makeText(this, "Pet atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    super.finish()
                }
            } else {
                MaterialAlertDialogBuilder(
                    this,
                    com.google.android.material.R.style.MaterialAlertDialog_Material3
                )
                    .setMessage("Você tem certeza que deseja sair?\nTodos os dados serão perdidos!")
                    .setPositiveButton("Confirmar") { dialog, _ ->
                        dialog.dismiss()
                        super.finish()
                    }
                    .setNegativeButton("Cancelar") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
        } else {
            super.finish()
        }
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