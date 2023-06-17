package utfpr.edu.br.motelanimal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import utfpr.edu.br.motelanimal.adapter.OnSelectTutorAdapterClick
import utfpr.edu.br.motelanimal.adapter.SelectTutoresAdapter
import utfpr.edu.br.motelanimal.dao.TutorDatabaseHandler
import utfpr.edu.br.motelanimal.databinding.ActivitySelectCoTutoresBinding
import utfpr.edu.br.motelanimal.entidades.Tutor
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class SelectCoTutores : AppCompatActivity() {

    private val binding by lazy { ActivitySelectCoTutoresBinding.inflate(layoutInflater) }
    private val tutorDatabaseHandler by lazy { TutorDatabaseHandler(this) }
    private val listaAdapter by lazy {
        SelectTutoresAdapter(this, findAllTutores(), object : OnSelectTutorAdapterClick {
            override fun onItemClick(tutor: Tutor) {
                this@SelectCoTutores.isFormDirt = true
                Log.i(
                    this@SelectCoTutores.localClassName,
                    "Clicou no tutor de ID ${tutor._id} - ${tutor.checked}"
                )
            }
        })
    }

    private var isFormDirt: Boolean = false
    private var tutoresVinculados: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)
        binding.rview.adapter = listaAdapter

        val tutoresVinculadosString = intent.getStringExtra("cotutores")
        if (!tutoresVinculadosString.isNullOrBlank()) {
            tutoresVinculados = tutoresVinculadosString
                .split(",")
                .map { t -> t.toInt() } as MutableList<Int>
        }

        listaAdapter.refresh(this.findAllTutores())

        binding.toolBar.setNavigationOnClickListener { onClickReturn() }
        binding.toolBarSave.setNavigationOnClickListener { onClickSave() }
    }

    fun findAllTutores(): List<Tutor> {
        val cursor = tutorDatabaseHandler.findList()
        val tutores = mutableListOf<Tutor>()
        if (ObjectUtils.isNotEmpty(cursor)) {
            while (cursor!!.moveToNext()) {
                val tutor = Tutor(tutorDatabaseHandler, cursor)
                tutor.checked = tutoresVinculados.contains(tutor._id)
                tutores.add(tutor)
            }
        }
        return tutores
    }

    private fun onClickReturn() {
        Log.i(this.localClassName, "onClickReturn")
        intent.putExtra("op", "close")
        finish()
    }

    private fun onClickSave() {
        Log.i(this.localClassName, "onClickSave")
        intent.putExtra("op", "save")
        finish()
    }

    override fun finish() {
        Log.i(this.localClassName, "finish")
        if (isFormDirt) {
            if (intent.getStringExtra("op").equals("save")) {
                val intent = Intent()
                intent.putExtra("result",
                    listaAdapter.getSelectedItens().joinToString(",") { it._id.toString() })
                setResult(Activity.RESULT_OK, intent)
                super.finish()
            } else {
                MaterialAlertDialogBuilder(
                    this,
                    R.style.MaterialAlertDialog_Material3
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