package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNumber: EditText
    private lateinit var buttonGenerate: Button
    private lateinit var buttonGoToSecond: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
    }

    private fun initViews() {
        editTextNumber = findViewById(R.id.editTextNumber)
        buttonGenerate = findViewById(R.id.buttonGenerate)
        buttonGoToSecond = findViewById(R.id.buttonGoToSecond)

        buttonGenerate.isEnabled = false
        buttonGoToSecond.isEnabled = false
    }

    private fun setupListeners() {
        editTextNumber.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val text = s.toString()
                if (text.isNotEmpty() && text.toIntOrNull() != null && text.toInt() > 0) {
                    buttonGenerate.isEnabled = true
                } else {
                    buttonGenerate.isEnabled = false
                }
            }
        })

        buttonGenerate.setOnClickListener {
            generateFilmList()
        }

        buttonGoToSecond.setOnClickListener {
            val intent = android.content.Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun generateFilmList() {
        val count = editTextNumber.text.toString().toInt()
        val filmNames = listOf(
            "Inception", "Shutter Island", "Blood Diamond", "Snatch",
            "The Matrix", "Pulp Fiction", "Forrest Gump", "Interstellar",
            "The Dark Knight", "Fight Club", "Goodfellas", "Gladiator",
            "Titanic", "Avatar", "The Godfather", "The Shawshank Redemption"
        )

        val films = mutableListOf<FilmModel>()
        for (i in 0 until count) {
            val randomName = filmNames.random()
            val randomYear = Random.nextInt(1990, 2025)
            films.add(
                FilmModel(
                    id = (i + 1).toString(),
                    posterUrl = "https://picsum.photos/seed/${i + 1}/200/300",
                    name = randomName,
                    description = "Description for $randomName",
                    releaseDate = randomYear
                )
            )
        }

        DataRepository.filmsList = films
        buttonGoToSecond.isEnabled = true
    }
}