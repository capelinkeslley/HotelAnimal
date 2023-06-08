package utfpr.edu.br.motelanimal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import utfpr.edu.br.motelanimal.databinding.ActivityConsultasBinding
import utfpr.edu.br.motelanimal.databinding.ActivityLojaBinding

class ConsultasActivity : AppCompatActivity() {

    private val binding by lazy { ActivityConsultasBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate CONSULTAS")
        setContentView(binding.root)
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
}