package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity() {

    private lateinit var editTextFilter: EditText
    private lateinit var buttonSort: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilmAdapter
    private var currentList: List<FilmModel> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupRecyclerView()
        setupListeners()

        currentList = DataRepository.filmsList
        adapter.submitList(currentList)
    }

    private fun initViews() {
        editTextFilter = findViewById(R.id.editTextFilter)
        buttonSort = findViewById(R.id.buttonSort)
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun setupRecyclerView() {
        adapter = FilmAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        buttonSort.setOnClickListener {
            val query = editTextFilter.text.toString().trim()

            if (query.isEmpty()) {
                currentList = DataRepository.filmsList
                adapter.submitList(currentList)
                return@setOnClickListener
            }

            try {
                val yearQuery = query.toInt()
                val filteredList = DataRepository.filmsList.filter { film ->
                    film.releaseDate <= yearQuery
                }
                currentList = filteredList
                adapter.submitList(currentList)
            } catch (e: NumberFormatException) {
                val filteredList = DataRepository.filmsList.filter { film ->
                    film.name.contains(query, ignoreCase = true)
                }
                currentList = filteredList
                adapter.submitList(currentList)
            }
        }
    }
}