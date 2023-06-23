package utfpr.edu.br.motelanimal

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import utfpr.edu.br.motelanimal.dao.ControleQuartoDatabaseHandler
import utfpr.edu.br.motelanimal.dao.PetsDatabaseHandler
import utfpr.edu.br.motelanimal.databinding.ActivityQuartoBinding
import utfpr.edu.br.motelanimal.entidades.ControleQuarto
import utfpr.edu.br.motelanimal.entidades.Pet
import utfpr.edu.br.motelanimal.entidades.Quarto
import utfpr.edu.br.motelanimal.entidades.getQuartoById
import utfpr.edu.br.motelanimal.utils.ObjectUtils

class QuartoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityQuartoBinding.inflate(layoutInflater) }
    private val controleQuartoDatabaseHandler by lazy { ControleQuartoDatabaseHandler(this) }
    private val petDatabaseHandler by lazy { PetsDatabaseHandler(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quarto)
        Log.i(this.localClassName, "onCreate")
        binding.toolBar.setNavigationOnClickListener { finish() }

        val id: Int = intent.getIntExtra("id", 0)
        val quarto: Quarto = getQuartoById(id)
        var petId: Int = 0
        val controleQuarto: Cursor? =  controleQuartoDatabaseHandler.whereActive("active = 1 and quarto = ${quarto._id}")

        val quartosIndisponiveis: MutableList<Int> = mutableListOf()
        if (ObjectUtils.isNotEmpty(controleQuarto) && controleQuarto != null) {
            while (controleQuarto.moveToNext()) {
                quartosIndisponiveis.add(ControleQuarto(controleQuartoDatabaseHandler, controleQuarto).quarto)
                if(petId == 0){
                    petId = ControleQuarto(controleQuartoDatabaseHandler, controleQuarto).pet
                }
            }
        }

        if(quartosIndisponiveis.size == 0){
            Log.i(this.localClassName, "Direcionar para o CheckIn")
            startActivity(Intent(this, CheckInActivity::class.java))
        }

        val pet: Pet = petDatabaseHandler.getPetById(petId)
//        binding.numero.setText(quarto.numero.toString())
//        binding.especie.setText(quarto.especie.nome)
//        binding.pet.setText(pet.nome)

        val especieEditText = findViewById<TextInputEditText>(R.id.especie)
        especieEditText.setText(quarto.especie.nome)
        val numeroEditText = findViewById<TextInputEditText>(R.id.numero)
        numeroEditText.setText(quarto.numero.toString())
        val petEditText = findViewById<TextInputEditText>(R.id.pet)
        petEditText.setText(pet.nome)
    }
}