package utfpr.edu.br.motelanimal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import utfpr.edu.br.motelanimal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)

        binding.btnCheckIn.setOnClickListener { onClickBtnCheckIn() }
        binding.btnCheckOut.setOnClickListener { onClickBtnCheckOut() }
        binding.btnConsultas.setOnClickListener { onClickBtnCheckConsultas() }
        binding.btnQuartos.setOnClickListener { onClickBtnQuartos() }
    }

    private fun onClickBtnCheckIn() {
        Log.i(this.localClassName, "onClickBtnCheckIn")
        startActivity(Intent(this, CheckInActivity::class.java))
    }

    private fun onClickBtnCheckOut() {
        Log.i(this.localClassName, "onClickBtnCheckOut")
        startActivity(Intent(this, CheckOutActivity::class.java))
    }

    private fun onClickBtnCheckConsultas() {
        Log.i(this.localClassName, "onClickBtnCheckConsultas")
        startActivity(Intent(this, ConsultasActivity::class.java))
    }

    private fun onClickBtnQuartos() {
        Log.i(this.localClassName, "onClickBtnQuartos")
        startActivity(Intent(this, QuartoListActivity::class.java))
    }

}