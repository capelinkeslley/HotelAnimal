package utfpr.edu.br.motelanimal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import utfpr.edu.br.motelanimal.dao.PetsDatabaseHandler
import utfpr.edu.br.motelanimal.dao.TutorDatabaseHandler
import utfpr.edu.br.motelanimal.databinding.ActivityCheckInBinding
import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.Tutor
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class QuartoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCheckInBinding.inflate(layoutInflater) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quarto)
        Log.i(this.localClassName, "onCreate")
        binding.toolBar.setNavigationOnClickListener { finish() }
    }


}