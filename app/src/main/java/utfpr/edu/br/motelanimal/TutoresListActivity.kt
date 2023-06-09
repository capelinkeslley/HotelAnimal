package utfpr.edu.br.motelanimal

//noinspection SuspiciousImport
import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import utfpr.edu.br.motelanimal.dao.TutorDatabaseHandler
import utfpr.edu.br.motelanimal.databinding.ActivityTutoresBinding
import utfpr.edu.br.motelanimal.entidades.Tutor
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class TutoresListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTutoresBinding.inflate(layoutInflater) }
    private val tutorDatabaseHandler by lazy { TutorDatabaseHandler(this) }
    private val tutores = mutableListOf<Tutor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)
        binding.toolBar.setNavigationOnClickListener { finish() }
        binding.toolBarAdd.setNavigationOnClickListener { addNew() }

        binding.listaTutores.setOnItemClickListener { _, _, position, _ ->
            Log.i(this.localClassName, "Clicou no tutor de posicao ${position}")
            val tutor = tutores[position]
            val intentNewTutor = Intent(this, TutorActivity::class.java)
            intentNewTutor.putExtra("id", tutor._id)
            startActivity(intentNewTutor)
        }
    }

    private fun addNew() {
        Log.i(this.localClassName, "addNew")
        val intentNewTutor = Intent(this, TutorActivity::class.java)
        intentNewTutor.putExtra("id", 0)
        startActivity(intentNewTutor)
    }

    private fun loadRegistros() {
        Log.i(this.localClassName, "loadRegistros")
        val cursor = tutorDatabaseHandler.findList()

        binding.listaTutores.adapter = SimpleCursorAdapter(
            this,
            R.layout.simple_list_item_1, cursor, arrayOf("nomeCompleto"), intArrayOf(R.id.text1),
            SimpleCursorAdapter.NO_SELECTION
        )

        if (ObjectUtils.isNotEmpty(cursor)) {
            tutores.clear()
            while (cursor!!.moveToNext()) {
                tutores.add(Tutor(tutorDatabaseHandler, cursor))
            }
        }

        if (ObjectUtils.isNotEmpty(tutores)) {
            binding.semRegistros.visibility = View.GONE
        }
    }

    override fun onResume() {
        Log.i(this.localClassName, "onResume")
        super.onResume()
        this.loadRegistros()
    }
}