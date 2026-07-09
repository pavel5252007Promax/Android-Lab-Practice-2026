package com.example.pomodorotimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val soundManager = SoundManager(this)
        setContent {
            MaterialTheme {
                var currentScreen by remember { mutableStateOf("theme_selection") }
                var selectedTheme by remember { mutableStateOf(themes[0]) }

                var workMinutes by rememberSaveable { mutableStateOf(1) }
                var restMinutes by rememberSaveable { mutableStateOf(5) }
                var longRestMinutes by rememberSaveable { mutableStateOf(15) }  // ← новое
                var pomodorosBeforeLongRest by rememberSaveable { mutableStateOf(4) }  // ← новое

                when (currentScreen) {
                    "theme_selection" -> {
                        ThemeSelectionScreen(
                            onThemeSelected = { theme ->
                                selectedTheme = theme
                                currentScreen = "timer"
                            },
                            onSettingsClick = {
                                currentScreen = "settings"
                            }
                        )
                    }
                    "timer" -> {
                        TimerScreen(
                            theme = selectedTheme,
                            onBack = {
                                currentScreen = "theme_selection"
                            },
                            soundManager = soundManager,
                            workMinutes = workMinutes,
                            restMinutes = restMinutes,
                            longRestMinutes = longRestMinutes,  // ← новое
                            pomodorosBeforeLongRest = pomodorosBeforeLongRest  // ← новое
                        )
                    }
                    "settings" -> {
                        SettingsScreen(
                            workMinutes = workMinutes,
                            restMinutes = restMinutes,
                            longRestMinutes = longRestMinutes,
                            pomodorosBeforeLongRest = pomodorosBeforeLongRest,
                            onWorkMinutesChange = { workMinutes = it },
                            onRestMinutesChange = { restMinutes = it },
                            onLongRestMinutesChange = { longRestMinutes = it },
                            onPomodorosBeforeLongRestChange = { pomodorosBeforeLongRest = it },
                            onBack = {
                                currentScreen = "theme_selection"
                            }
                        )
                    }
                }
            }
        }
    }
}