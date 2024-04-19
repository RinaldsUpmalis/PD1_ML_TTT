package com.example.pd1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pd1.databinding.ActivityGameBinding
import com.example.pd1.databinding.ActivityLoginBinding

class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
//piešķir pagaidu mainīgajiem lietas
        val pvpMode = intent.getIntExtra("pvpMode", -1)
        val firstPlayerName = intent.getStringExtra("username1")
        val secondPlayerName = intent.getStringExtra("username2")
        val playerTitle = findViewById<TextView>(R.id.CurrentPlayer)
        val restartButton: Button = findViewById<Button>(R.id.btn_restart)
        playerTitle.text = firstPlayerName
        //izveido sarakstu ar pogām
        val buttons: MutableList<Button> = mutableListOf(
                    findViewById<Button>(R.id.btn_0),
                    findViewById<Button>(R.id.btn_1),
                    findViewById<Button>(R.id.btn_2),
                    findViewById<Button>(R.id.btn_3),
                    findViewById<Button>(R.id.btn_4),
                    findViewById<Button>(R.id.btn_5),
                    findViewById<Button>(R.id.btn_6),
                    findViewById<Button>(R.id.btn_7),
                    findViewById<Button>(R.id.btn_8)
        )
        //šo visu labumu iebāž objektā game
        val game = GameState(firstPlayerName, secondPlayerName, pvpMode, playerTitle, restartButton, buttons)
        //un ja dators startēja spēli, tad viņš šajā momentā izpilda gājienu
        if (pvpMode==0) {
            game.computerMove()
        }

//game objektā iesūta pogu kuru uzspieda un indeksu
        binding.btn0.setOnClickListener {
            game.editBoard(findViewById<Button>(R.id.btn_0), 0)
        }
        binding.btn1.setOnClickListener {
            game.editBoard(findViewById<Button>(R.id.btn_1), 1)
        }
        binding.btn2.setOnClickListener {
            game.editBoard(findViewById<Button>(R.id.btn_2), 2)
        }
        binding.btn3.setOnClickListener {
            game.editBoard(findViewById<Button>(R.id.btn_3), 3)
        }
        binding.btn4.setOnClickListener {
            game.editBoard(findViewById<Button>(R.id.btn_4), 4)
        }
        binding.btn5.setOnClickListener {
            game.editBoard(findViewById<Button>(R.id.btn_5), 5)
        }
        binding.btn6.setOnClickListener {
            game.editBoard(findViewById<Button>(R.id.btn_6), 6)
        }
        binding.btn7.setOnClickListener {
            game.editBoard(findViewById<Button>(R.id.btn_7), 7)
        }
        binding.btn8.setOnClickListener {
            game.editBoard(findViewById<Button>(R.id.btn_8), 8)
        }
//spēles beigās šī poga parādās un viņu uzspiežot spēle restartējas
        binding.btnRestart.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}