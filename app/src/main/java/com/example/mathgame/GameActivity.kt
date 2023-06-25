package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.mathgame.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    private var correctAnswer = -1
    private var userScore = 0
    private var userLife = 3

    private lateinit var timer: CountDownTimer
    private val startTime: Long = 11000
    private var timeLeft: Long = startTime

    private lateinit var gameMode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameMode =  intent.getStringExtra("gameMode")!!

        supportActionBar?.title = when(gameMode) {
            MainActivity.GameMode.ADDITION.toString() -> resources.getString(R.string.addition)
            MainActivity.GameMode.SUBTRACTION.toString() -> resources.getString(R.string.subtraction)
            MainActivity.GameMode.MULTIPLICATION.toString() -> resources.getString(R.string.multiplication)
            MainActivity.GameMode.DIVISION.toString() -> resources.getString(R.string.division)
            else -> {""}
        }

        gameContinue()

        binding.nextButton.setOnClickListener {
            timer.cancel()
            timeLeft = startTime
            gameContinue()
            binding.answerTextView.text.clear()

            if(userLife == 0) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("score", userScore)
                startActivity(intent)
                finish()
            }
        }

        binding.okButton.setOnClickListener {
            val input = binding.answerTextView.text.toString()
            if(input == "") {
                Toast.makeText(this, resources.getString(R.string.answer_hint_toast), Toast.LENGTH_LONG).show()
            } else {
                timer.cancel()
                binding.okButton.isClickable = false
                val userAnswer = input.toInt()
                if(userAnswer == correctAnswer) {
                    userScore += 10
                    binding.questionTextView.text = resources.getString(R.string.answer_correct)
                    binding.scoreCount.text = userScore.toString()
                } else {
                    userLife--
                    binding.questionTextView.text = resources.getString(R.string.answer_incorrect)
                    binding.lifeCount.text = userLife.toString()
                }
            }
        }
    }

    private fun gameContinue() {
        binding.okButton.isClickable = true
        startTimer()
        var randomNumber1 = Random.nextInt(1, 100)
        var randomNumber2 = Random.nextInt(1, 100)

        correctAnswer = when(gameMode) {
            MainActivity.GameMode.ADDITION.toString() -> {
                binding.questionTextView.text = "$randomNumber1 + $randomNumber2 = ?"
                randomNumber1 + randomNumber2
            }
            MainActivity.GameMode.SUBTRACTION.toString() -> {
                if(randomNumber1 > randomNumber2) {
                    binding.questionTextView.text = "$randomNumber1 - $randomNumber2 = ?"
                    randomNumber1 - randomNumber2
                }
                else {
                    binding.questionTextView.text = "$randomNumber2 - $randomNumber1 = ?"
                    randomNumber2 - randomNumber1
                }
            }
            MainActivity.GameMode.MULTIPLICATION.toString() -> {
                randomNumber1 = Random.nextInt(2, 10)
                randomNumber2 = Random.nextInt(2, 10)
                binding.questionTextView.text = "$randomNumber1 × $randomNumber2 = ?"
                randomNumber1 * randomNumber2
            }
            MainActivity.GameMode.DIVISION.toString() -> {
                randomNumber1 = Random.nextInt(2, 10)
                randomNumber2 = randomNumber1 * Random.nextInt(2, 10)
                binding.questionTextView.text = "$randomNumber2 ÷ $randomNumber1 = ?"
                randomNumber2 / randomNumber1
            }
            else -> -1
        }
    }

    private fun startTimer() {
        timer = object:  CountDownTimer(timeLeft, 1000)  {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                binding.timeCount.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                timer.cancel()
                timeLeft = startTime

                userLife --
                binding.lifeCount.text = userLife.toString()
                binding.questionTextView.text = resources.getString(R.string.time_up)
                binding.okButton.isClickable = false
            }
        }.start()
    }

}