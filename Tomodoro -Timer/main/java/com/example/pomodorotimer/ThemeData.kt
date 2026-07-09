package com.example.pomodorotimer

data class ThemeData(
    val id: String,
    val name: String,
    val emoji: String,
    val imageRes: Int? = null,
    val backgroundColor: Int,
    val timerColor: Int,
    val startSound: Int? = null,
    val endSound: Int? = null,
    val isComingSoon: Boolean = false
)

val themes = listOf(
    ThemeData (
        id = "Classic pomodoro",
        name = "Классический помодоро",
        emoji = "",
        imageRes = R.drawable.fondark,
        startSound = R.raw.bugaga,
        endSound = R.raw.shrek_sound,
        backgroundColor = 0xFFE53935.toInt(),
        timerColor = 0xFFFFFFFF.toInt()

    ),

    ThemeData (
        id = "Shrek pomodoro",
        name = "Шрэк помодоро",
        emoji = "",
        imageRes = R.drawable.shrekofon,
        backgroundColor = 0xFF4CAF50.toInt(),
        timerColor = 0xFFFFFF00.toInt(),
        startSound = R.raw.shrek_soundd,
        endSound = R.raw.squir
    ),

    ThemeData(
        id = "Kitchen",
        name = "Кухня",
        emoji = "",
        imageRes = R.drawable.naruto,
        backgroundColor = 0xFF1A237E.toInt(),
        timerColor = 0xFFFFFFFF.toInt(),
        isComingSoon = false,
        startSound = R.raw.matadora,
        endSound = R.raw.squir
    ),
    ThemeData(
        id = "new",
        name = "новая",
        emoji = "",
        imageRes = R.drawable.tomas,
        backgroundColor = 0xFF1A237E.toInt(),
        timerColor = 0xFFFFFFFF.toInt(),
        isComingSoon = true,
    )
)

fun getThemeById(id: String): ThemeData {
    return themes.find { it.id == id } ?: themes[0]
}