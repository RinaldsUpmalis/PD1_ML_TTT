package com.example.pd1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pd1.databinding.ActivityLoginBinding
import com.example.pd1.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        var pvpMode = intent.getIntExtra("pvpMode", -1)
        //ja ir pvc (pvp!=-1) tad randomā izvēlas vai dators būs pirmaiz vai otrais
        if (pvpMode!=-1) {
            pvpMode = (0..1).random()
            val secondPlayerPC = "Computer"
            val editText: EditText = if (pvpMode == 0) {
                //šeit aatiecīgi ieraksta Computer ja tas ir pirmais vai otrādi
                findViewById<EditText>(R.id.editTextText5)
            } else {
                findViewById<EditText>(R.id.editTextText6)
            }
            //Computer aili disablo
            editText.setText(secondPlayerPC)
            editText.isEnabled = false
            editText.isFocusable = false
            editText.isClickable = false
        }

        binding.button.setOnClickListener {
            val username1 = binding.editTextText5.text.toString().trim()
            val username2 = binding.editTextText6.text.toString().trim()
            //šeit pārbauda vai abi vārdi ievadīti
            if (username1.isEmpty() || username2.isEmpty()) {
                Toast.makeText(this, "Please enter both usernames", Toast.LENGTH_SHORT).show()
            } else {//visu info aizsūtam tālāk
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("username1", username1)
                intent.putExtra("username2", username2)
                intent.putExtra("pvpMode", pvpMode)
                startActivity(intent)

                finish()
            }
        }
    }
}