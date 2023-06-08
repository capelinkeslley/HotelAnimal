package utfpr.edu.br.motelanimal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import utfpr.edu.br.motelanimal.databinding.ActivityConsultasBinding

class ConsultasActivity : AppCompatActivity() {

    private val binding by lazy { ActivityConsultasBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)
        binding.toolBar.setNavigationOnClickListener { finish() }

        binding.btnTutores.setOnClickListener { onClickBtnTutores() }
        binding.btnPets.setOnClickListener { onClickBtnPets() }
        binding.btnRelatorios.setOnClickListener { onClickBtnRelatorios() }
    }

    private fun onClickBtnTutores() {
        Log.i(this.localClassName, "onClickBtnTutores")
        startActivity(Intent(this, TutoresListActivity::class.java))
    }

    private fun onClickBtnPets() {
        Log.i(this.localClassName, "onClickBtnPets")
        startActivity(Intent(this, PetsListActivity::class.java))
    }

    private fun onClickBtnRelatorios() {
        Log.i(this.localClassName, "onClickBtnRelatorios")
        startActivity(Intent(this, RelatoriosListActivity::class.java))
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