package uz.digital.datastore103

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.digital.datastore103.databinding.ActivityMainBinding
import uz.digital.datastore103.manager.DataStoreManager
import uz.digital.datastore103.model.User

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dataStore = DataStoreManager(this)

        binding.btnSave.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val lastName = binding.editLastName.text.toString().trim()
            val age = binding.editAge.text.toString().trim().toInt()
            if (name.isNotBlank() && lastName.isNotBlank() && age.toString().isNotBlank()) {
                val user = User(name, lastName, age)
                GlobalScope.launch(Dispatchers.IO) {
                    dataStore.saveUser(user)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Saved", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Enter data!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnGet.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                dataStore.getUser().collect {
                    binding.textView.text = it.toString()
                }
            }
        }
    }
}