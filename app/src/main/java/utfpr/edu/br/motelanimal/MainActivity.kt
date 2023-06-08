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