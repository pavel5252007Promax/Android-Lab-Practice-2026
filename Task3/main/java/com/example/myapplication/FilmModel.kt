package com.example.myapplication



data class FilmModel(
    val id: String,
    val posterUrl: String,
    val name: String,
    val description: String?,
    val releaseDate: Int
)