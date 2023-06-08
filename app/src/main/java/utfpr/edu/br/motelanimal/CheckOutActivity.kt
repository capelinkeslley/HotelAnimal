package utfpr.edu.br.motelanimal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import utfpr.edu.br.motelanimal.databinding.ActivityCheckOutBinding
import utfpr.edu.br.motelanimal.databinding.ActivityConsultasBinding

class CheckOutActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCheckOutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)

        binding.toolBar.title = "Check-Out";
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