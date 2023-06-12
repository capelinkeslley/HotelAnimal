package utfpr.edu.br.motelanimal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import utfpr.edu.br.motelanimal.databinding.ActivityCheckInBinding

class CheckInActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCheckInBinding.inflate(layoutInflater) }

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

        binding.pet.setAdapter(adapterPets)
        binding.responsavel.setAdapter(adapterResponsavel)
        binding.quarto.setAdapter(adapterQuarto)

        binding.btnCancel.setOnClickListener { onClickBtnCancel() }

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