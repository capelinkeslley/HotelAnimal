package utfpr.edu.br.motelanimal

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import utfpr.edu.br.motelanimal.dao.ControleQuartoDatabaseHandler
import utfpr.edu.br.motelanimal.dao.PetsDatabaseHandler
import utfpr.edu.br.motelanimal.dao.RelatoriosDatabaseHandler
import utfpr.edu.br.motelanimal.databinding.ActivityRelatorioBinding
import utfpr.edu.br.motelanimal.entidades.ControleQuarto
import utfpr.edu.br.motelanimal.entidades.Especie
import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.Quarto
import utfpr.edu.br.motelanimal.entidades.Relatorio
import utfpr.edu.br.motelanimal.entidades.Tutor
import utfpr.edu.br.motelanimal.entidades.getEspecieById
import utfpr.edu.br.motelanimal.entidades.getQuartoById
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class RelatorioActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRelatorioBinding.inflate(layoutInflater) }
    private val relatoriosDatabaseHandler by lazy { RelatoriosDatabaseHandler(this) }
    private val petsDatabaseHandler by lazy { PetsDatabaseHandler(this) }
    private val controleQuartoDatabaseHandler by lazy { ControleQuartoDatabaseHandler(this) }

    private val quartosSelect: MutableList<Quarto> = mutableListOf()

    private var pets: MutableList<Pet> = mutableListOf()
    private var quartos: MutableList<ControleQuarto> = mutableListOf()
    private var relatorio: Relatorio = Relatorio()
    private var isFormDirt: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)

        relatorio._id = intent.getIntExtra("id", 0)

        buscaPets()
        buscaQuartos()

        if (relatorio._id != 0) {
            binding.toolBar.title = getString(R.string.editar)
            buscaDadosRelatorio()
            binding.toolBarExcluir.setNavigationOnClickListener { onClickExcluir() }
        } else {
            binding.toolBarExcluir.visibility = View.GONE
        }

        binding.toolBar.setNavigationOnClickListener { onClickReturn() }
        binding.toolBarSave.setNavigationOnClickListener { onClickSave() }

        bindValidationOnInputs()
    }

    private fun buscaPets() {
        pets = mutableListOf()
        val cursor = petsDatabaseHandler.findList()
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null) {
            while (cursor.moveToNext()) {
                pets.add(Pet(petsDatabaseHandler, cursor))
            }
        }
    }

    private fun buscaQuartos() {
        quartos = mutableListOf()
        val cursor = controleQuartoDatabaseHandler.findList("quarto", "active = 1")
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null) {
            while (cursor.moveToNext()) {
                quartos.add(ControleQuarto(controleQuartoDatabaseHandler, cursor))
            }
        }
        setQuartos()
    }

    private fun buscaDadosRelatorio() {
        val cursor = relatoriosDatabaseHandler.findOneBy("_id", relatorio._id.toString())
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null && cursor.moveToNext()) {
            relatorio = Relatorio(relatoriosDatabaseHandler, cursor)
            setQuartos()
            binding.titulo.setText(relatorio.titulo)
            binding.relatorio.setText(relatorio.relatorio)
        }
    }

    private fun setQuartos() {
        val spinnerText: MutableList<String> = mutableListOf()
        for (controle in quartos.filter { it.quarto != 10 }) {
            val quarto: Quarto = getQuartoById(controle.quarto)
            val pet: Pet = pets.find { it._id == controle.pet }!!
            quartosSelect.add(quarto)
            spinnerText.add(quarto.numero.toString() + " - " + pet.nome + " - " + getEspecieById(pet.especie).nome)
        }

        binding.quarto.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            spinnerText)

        if (relatorio.quarto != 0) {
            binding.quarto.setSelection(relatorio.quarto - 1)
        }
        binding.quarto.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                relatorio.quarto = quartosSelect.get(position)._id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
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
            relatorio.titulo = binding.titulo.text.toString().trim()
            relatorio.relatorio = binding.relatorio.text.toString().trim()

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
                if (relatoriosDatabaseHandler.delete(relatorio._id) != 0) {
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
            binding.titulo,
            binding.relatorio,
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
            binding.titulo -> validateTitulo()
            binding.relatorio -> validateRelatorio()
        }
    }

    private fun validateForm(): Boolean {
        return validateTitulo() && validateQuarto() && validateRelatorio() && isFormDirt
    }

    private fun validateTitulo(): Boolean {
        isFormDirt = true
        relatorio.titulo = binding.titulo.text.toString()
        binding.tituloLayout.error = null
        val notIsValid = ObjectUtils.isEmpty(relatorio.titulo) || relatorio.titulo.length < 3
        if (notIsValid) {
            binding.tituloLayout.error = "Campo Inválido"
        } else {
            relatorio.titulo =
                relatorio.titulo.substring(0, 1).uppercase() + relatorio.titulo.substring(1)
        }
        Log.i(
            this.localClassName,
            "Campo \"Titulo\" é ${if (notIsValid) "Inválido" else "Válido"}"
        )
        return !notIsValid
    }

    private fun validateQuarto(): Boolean {
        isFormDirt = true
        binding.quartoLayout.error = null
        val notIsValid = ObjectUtils.isEmpty(binding.quarto.selectedItem)
        if (notIsValid) {
            binding.quartoLayout.error = "Campo Inválido"
        }
        Log.i(
            this.localClassName,
            "Campo \"Quarto\" é ${if (notIsValid) "Inválido" else "Válido"}"
        )
        return !notIsValid
    }

    private fun validateRelatorio(): Boolean {
        isFormDirt = true
        relatorio.relatorio = binding.relatorio.text.toString()
        binding.relatorioLayout.error = null
        val notIsValid = ObjectUtils.isEmpty(relatorio.relatorio) || relatorio.relatorio.length < 3
        if (notIsValid) {
            binding.relatorioLayout.error = "Campo Inválido"
        } else {
            relatorio.relatorio =
                relatorio.relatorio.substring(0, 1).uppercase() + relatorio.relatorio.substring(1)
        }
        Log.i(
            this.localClassName,
            "Campo \"Relatório\" é ${if (notIsValid) "Inválido" else "Válido"}"
        )
        return !notIsValid
    }

    override fun finish() {
        Log.i(this.localClassName, "finish")
        if (isFormDirt) {
            if (intent.getStringExtra("op").equals("save")) {
                if (relatorio._id == 0) {
                    relatoriosDatabaseHandler.save(relatorio)
                    Toast.makeText(this, "Relatório cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                    super.finish()
                } else {
                    relatoriosDatabaseHandler.update(relatorio)
                    Toast.makeText(this, "Relatório atualizado com sucesso", Toast.LENGTH_SHORT).show()
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
}