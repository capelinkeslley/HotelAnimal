package utfpr.edu.br.motelanimal

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import utfpr.edu.br.motelanimal.databinding.ActivityCheckOutBinding

class CheckOutActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCheckOutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)
        binding.toolBar.setNavigationOnClickListener { finish() }

        for( i in 0 until 3){
            val tableRow = TableRow(this)

            val textView1 = TextView(this)
            textView1.text = "Coluna 1 - Linha ${i + 1}"
            textView1.setPadding(8, 8, 8, 8)

            val textView2 = TextView(this)
            textView2.text = "Coluna 2 - Linha ${i + 1}"
            textView2.setPadding(8, 8, 8, 8)

            // Adicione mais colunas conforme necessário

            tableRow.addView(textView1)
            tableRow.addView(textView2)

            // Adicione mais células (TextView) à linha conforme necessário

            val pets = arrayOf("pet 1", "pet 2", "pet 3", "pet 4")
            val responsaveis = arrayOf("responsável 1", "responsável 2", "responsável 3", "responsável 4")

            val adapterPets = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, pets)
            val adapterResponsavel  = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, responsaveis)

            adapterPets.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
            adapterResponsavel.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)

            binding.pet.setAdapter(adapterPets)
            binding.responsavel.setAdapter(adapterResponsavel)

            binding.tableActivities.addView(tableRow)

            binding.btnCancel.setOnClickListener { onClickBtnCancel() }
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

    private fun onClickBtnCancel(){
        Log.i(this.localClassName, "onClickBtnCancel")
        startActivity(Intent(this, MainActivity::class.java))
    }
}