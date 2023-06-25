package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mathgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    enum class GameMode {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.additionButton.setOnClickListener {
            launchGameActivity(GameMode.ADDITION)
        }

        binding.subtractionButton.setOnClickListener {
            launchGameActivity(GameMode.SUBTRACTION)
        }

        binding.multiplicationButton.setOnClickListener {
            launchGameActivity(GameMode.MULTIPLICATION)
        }

        binding.divisionButton.setOnClickListener {
            launchGameActivity(GameMode.DIVISION)
        }
    }

    private fun launchGameActivity(gameMode: GameMode) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("gameMode", gameMode.toString())
        startActivity(intent)
    }
}