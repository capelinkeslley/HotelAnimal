package utfpr.edu.br.motelanimal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import utfpr.edu.br.motelanimal.databinding.ActivityTutoresBinding

class TutoresListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTutoresBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(this.localClassName, "onCreate")
        setContentView(binding.root)
        binding.toolBar.title = getString(R.string.tutores)
        binding.toolBar.setNavigationOnClickListener { finish() }
        binding.toolBarAdd.setNavigationOnClickListener { addNew() }
    }

    private fun addNew() {
        Log.i(this.localClassName, "addNew")
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