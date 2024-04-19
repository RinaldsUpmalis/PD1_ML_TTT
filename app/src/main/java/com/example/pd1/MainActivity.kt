package com.example.pd1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pd1.databinding.ActivityMainBinding
// aplikācijaa ir kļūda, kāmēr dators veic gājienu, cilvēks var arī izdarīt gājienu
//tas salauž spēli
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//pvpMode -1 norādīs ka ir pvp, 0 norādīts ka pvc
    var pvpMode = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.pvpBtn.setOnClickListener{
            pvpMode = -1
            logIn()
        }

        binding.pvcBtn.setOnClickListener{
            pvpMode = 0
            logIn()
        }

    }

    fun logIn() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("pvpMode", pvpMode)
        startActivity(intent)
    }

}