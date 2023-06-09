package utfpr.edu.br.motelanimal

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import utfpr.edu.br.motelanimal.dao.TutorDatabaseHandler
import utfpr.edu.br.motelanimal.databinding.ActivityTutorBinding
import utfpr.edu.br.motelanimal.entidades.Tutor
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class TutorActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTutorBinding.inflate(layoutInflater) }
    private val tutorDatabaseHandler by lazy { TutorDatabaseHandler(this) }

    private var tutor: Tutor = Tutor()
    private var isFormDirt: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)
        tutor._id = intent.getIntExtra("id", 0)

        if (tutor._id != 0) {
            binding.toolBar.title = getString(R.string.editar)
            buscaDadosTutor()

            binding.toolBarExcluir.setNavigationOnClickListener { onClickExcluir() }
        } else {
            binding.toolBarExcluir.visibility = View.GONE
        }

        binding.toolBar.setNavigationOnClickListener { onClickReturn() }
        binding.toolBarSave.setNavigationOnClickListener { onClickSave() }

        bindValidationOnInputs()
    }

    private fun buscaDadosTutor() {
        val cursor = tutorDatabaseHandler.findOneBy("_id", tutor._id.toString())
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null && cursor.moveToNext()) {
            tutor = Tutor(tutorDatabaseHandler, cursor)
            binding.nomeCompleto.setText(tutor.nomeCompleto)
            binding.cpf.setText(tutor.cpf)
            binding.email.setText(tutor.email)
            binding.telefonePrincipal.setText(tutor.telefonePrincipal)
            binding.cep.setText(tutor.cep)
            binding.bairro.setText(tutor.bairro)
            binding.endereco.setText(tutor.endereco)
            binding.cidade.setText(tutor.cidade)
            binding.complemento.setText(tutor.complemento)
            binding.observacoes.setText(tutor.observacoes)
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
                if (tutorDatabaseHandler.delete(tutor._id) != 0) {
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
            binding.nomeCompleto,
            binding.cpf,
            binding.email,
            binding.telefonePrincipal,
            binding.cep,
            binding.bairro,
            binding.endereco,
            binding.cidade,
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
            binding.nomeCompleto -> validateNomeCompleto()
            binding.cpf -> validateCpf()
            binding.email -> validateEmail()
            binding.telefonePrincipal -> validateTelefonePrincipal()
            binding.cep -> validateCep()
            binding.bairro -> validateBairro()
            binding.endereco -> validateEndereco()
            binding.cidade -> validateCidade()
        }
    }

    private fun validateForm(): Boolean {
        return validateNomeCompleto() &&
                validateCpf() &&
                validateEmail() &&
                validateTelefonePrincipal() &&
                validateCep() &&
                validateBairro() &&
                validateEndereco() &&
                validateCidade() && isFormDirt
    }

    private fun validateNomeCompleto(): Boolean {
        isFormDirt = true
        tutor.nomeCompleto = binding.nomeCompleto.text.toString()
        binding.nomeCompletoLayout.error = ""
        val notIsValid = ObjectUtils.isEmpty(tutor.nomeCompleto) || tutor.nomeCompleto.length < 3
        if (notIsValid) {
            binding.nomeCompletoLayout.error = "Campo Inválido"
        } else {
            tutor.nomeCompleto =
                tutor.nomeCompleto.substring(0, 1).uppercase() + tutor.nomeCompleto.substring(1)
        }
        Log.i(
            this.localClassName,
            "Campo \"nomeCompleto\" é ${if (notIsValid) "Inválido" else "Válido"}"
        )
        return !notIsValid
    }

    private fun validateCpf(): Boolean {
        isFormDirt = true
        tutor.cpf = binding.cpf.text.toString()
        binding.cpfLayout.error = ""
        val notIsValid = ObjectUtils.isEmpty(tutor.cpf) || tutor.cpf.length != 11
        if (notIsValid) {
            binding.cpfLayout.error = "CPF Inválido"
        }
        Log.i(this.localClassName, "Campo \"cpf\" é ${if (notIsValid) "Inválido" else "Válido"}")
        return !notIsValid
    }

    private fun validateEmail(): Boolean {
        isFormDirt = true
        tutor.email = binding.email.text.toString()
        binding.emailLayout.error = ""
        val notIsValid = !(tutor.email.contains('@') && tutor.email.contains('.'))
        if (notIsValid) {
            binding.emailLayout.error = "E-mail Inválido"
        }
        Log.i(this.localClassName, "Campo \"email\" é ${if (notIsValid) "Inválido" else "Válido"}")
        return !notIsValid
    }

    private fun validateTelefonePrincipal(): Boolean {
        isFormDirt = true
        tutor.telefonePrincipal = binding.telefonePrincipal.text.toString()
        binding.telefonePrincipalLayout.error = ""
        val notIsValid = ObjectUtils.isEmpty(tutor.telefonePrincipal)
        if (notIsValid) {
            binding.telefonePrincipalLayout.error = "Telefone Principal Inválido"
        }
        Log.i(
            this.localClassName,
            "Campo \"telefonePrincipal\" é ${if (notIsValid) "Inválido" else "Válido"}"
        )
        return !notIsValid
    }

    private fun validateCep(): Boolean {
        isFormDirt = true
        tutor.cep = binding.cep.text.toString()
        binding.cepLayout.error = ""
        val notIsValid = ObjectUtils.isEmpty(tutor.cep)
        if (notIsValid) {
            binding.cepLayout.error = "CEP Inválido"
        }
        Log.i(this.localClassName, "Campo \"cep\" é ${if (notIsValid) "Inválido" else "Válido"}")
        return !notIsValid
    }

    private fun validateBairro(): Boolean {
        isFormDirt = true
        tutor.bairro = binding.bairro.text.toString()
        binding.bairroLayout.error = ""
        val notIsValid = ObjectUtils.isEmpty(tutor.bairro)
        if (notIsValid) {
            binding.bairroLayout.error = "Bairro Inválido"
        }
        Log.i(this.localClassName, "Campo \"bairro\" é ${if (notIsValid) "Inválido" else "Válido"}")
        return !notIsValid
    }

    private fun validateEndereco(): Boolean {
        isFormDirt = true
        tutor.endereco = binding.endereco.text.toString()
        binding.enderecoLayout.error = ""
        val notIsValid = ObjectUtils.isEmpty(tutor.endereco)
        if (notIsValid) {
            binding.enderecoLayout.error = "Endereço Inválido"
        }
        Log.i(
            this.localClassName,
            "Campo \"endereco\" é ${if (notIsValid) "Inválido" else "Válido"}"
        )
        return !notIsValid
    }

    private fun validateCidade(): Boolean {
        isFormDirt = true
        tutor.cidade = binding.cidade.text.toString()
        binding.cidadeLayout.error = ""
        val notIsValid = ObjectUtils.isEmpty(tutor.cidade)
        if (notIsValid) {
            binding.cidadeLayout.error = "Cidade Inválido"
        }
        Log.i(this.localClassName, "Campo \"cidade\" é ${if (notIsValid) "Inválido" else "Válido"}")
        return !notIsValid
    }

    override fun finish() {
        Log.i(this.localClassName, "finish")
        if (isFormDirt) {
            if (intent.getStringExtra("op").equals("save")) {
                if (tutor._id == 0) {
                    if (tutorDatabaseHandler.save(tutor) == 0L) {
                        binding.cpfLayout.error = "CPF Já cadastrado"
                        binding.cpf.requestFocus()
                    } else {
                        Toast.makeText(this, "Tutor cadastrado com sucesso", Toast.LENGTH_SHORT)
                            .show()
                        super.finish()
                    }
                } else {
                    tutorDatabaseHandler.update(tutor)
                    Toast.makeText(this, "Tutor atualizado com sucesso", Toast.LENGTH_SHORT).show()
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