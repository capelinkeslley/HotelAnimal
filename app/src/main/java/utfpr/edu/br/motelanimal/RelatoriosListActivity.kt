package utfpr.edu.br.motelanimal

import android.R
import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.util.Log
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import utfpr.edu.br.motelanimal.dao.ControleQuartoDatabaseHandler
import utfpr.edu.br.motelanimal.dao.PetsDatabaseHandler
import utfpr.edu.br.motelanimal.dao.RelatoriosDatabaseHandler
import utfpr.edu.br.motelanimal.databinding.ActivityRelatoriosListBinding
import utfpr.edu.br.motelanimal.entidades.ControleQuarto
import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.Relatorio
import utfpr.edu.br.motelanimal.entidades.RelatoriosListItem
import utfpr.edu.br.motelanimal.entidades.getEspecieById
import utfpr.edu.br.motelanimal.entidades.getQuartoById
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class RelatoriosListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRelatoriosListBinding.inflate(layoutInflater) }
    private val relatoriosDatabaseHandler by lazy { RelatoriosDatabaseHandler(this) }
    private val relatorios = mutableListOf<Relatorio>()

    private val controleQuartoDatabaseHandler by lazy { ControleQuartoDatabaseHandler(this) }
    private var controleQuarto: MutableList<ControleQuarto> = mutableListOf()

    private val petsDatabaseHandler by lazy { PetsDatabaseHandler(this) }
    private var pets: MutableList<Pet> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)

        binding.toolBar.setNavigationOnClickListener { finish() }
        binding.toolBarAdd.setNavigationOnClickListener { addNew() }

        binding.listaRelatorios.setOnItemClickListener { _, _, position, _ ->
            Log.i(this.localClassName, "Clicou no tutor de posicao ${position}")
            val relatorio = relatorios[position]
            val intentRelatorio = Intent(this, RelatorioActivity::class.java)
            intentRelatorio.putExtra("id", relatorio._id)
            startActivity(intentRelatorio)
        }
    }

    private fun addNew() {
        Log.i(this.localClassName, "addNew")
        val intentNew = Intent(this, RelatorioActivity::class.java)
        intentNew.putExtra("id", 0)
        startActivity(intentNew)
    }

    override fun onPause() {
        super.onPause()
        Log.i(this.localClassName, "onPause")
    }

    private fun buscaControleQuarto() {
        Log.i(this.localClassName, "buscaControleQuarto")
        controleQuarto = mutableListOf()
        val cursor = controleQuartoDatabaseHandler.findList("quarto", "active = 1")
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null) {
            while (cursor.moveToNext()) {
                controleQuarto.add(ControleQuarto(controleQuartoDatabaseHandler, cursor))
            }
        }
    }

    private fun loadRegistros() {
        Log.i(this.localClassName, "loadRegistros")

        if (pets.isEmpty() || controleQuarto.isEmpty()) {
            return
        }

        val cursor = relatoriosDatabaseHandler.findList()

        if (ObjectUtils.isNotEmpty(cursor)) {
            relatorios.clear()
            while (cursor!!.moveToNext()) {
                relatorios.add(Relatorio(relatoriosDatabaseHandler, cursor))
            }
        }

        val registros: MutableList<RelatoriosListItem> = mutableListOf()
        for (relatorio in relatorios) {
            val quarto = getQuartoById(relatorio.quarto)
            val controle = controleQuarto.find { it.quarto == quarto._id }!!
            val pet = pets.find { it._id == controle.pet }!!

            val item = RelatoriosListItem()
            item.titulo = relatorio.titulo

            item.quarto =
                quarto.numero.toString() + " - " + pet.nome + " - " + getEspecieById(pet.especie).nome

            registros.add(item)
        }

        binding.listaRelatorios.adapter = SimpleCursorAdapter(
            this,
            R.layout.simple_list_item_2,
            getCursorFromList(registros),
            arrayOf("titulo", "quarto"),
            intArrayOf(R.id.text1, R.id.text2),
            SimpleCursorAdapter.NO_SELECTION
        )
    }

    fun getCursorFromList(relatoriosListItens: MutableList<RelatoriosListItem>): Cursor {
        val columns = arrayOf("_id", "titulo", "quarto")
        val cursor = MatrixCursor(columns)

        for ((index, item) in relatoriosListItens.withIndex()) {
            val rowData = arrayOf(index, item.titulo, item.quarto)
            cursor.addRow(rowData)
        }

        return cursor
    }

    private fun buscaPets() {
        Log.i(this.localClassName, "buscaPets")
        pets = mutableListOf()
        val cursor = petsDatabaseHandler.findList()
        if (ObjectUtils.isNotEmpty(cursor) && cursor != null) {
            while (cursor.moveToNext()) {
                pets.add(Pet(petsDatabaseHandler, cursor))
            }
        }
    }

    override fun onResume() {
        Log.i(this.localClassName, "onResume")
        super.onResume()

        this.buscaControleQuarto()
        this.buscaPets()
        this.loadRegistros()
    }
}