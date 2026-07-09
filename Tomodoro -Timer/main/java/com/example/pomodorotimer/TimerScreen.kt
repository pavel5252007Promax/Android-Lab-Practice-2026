package com.example.pomodorotimer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale

@Composable
fun TimerScreen(
    theme: ThemeData,
    onBack: () -> Unit,
    soundManager: SoundManager,
    workMinutes: Int,
    restMinutes: Int,
    longRestMinutes: Int,
    pomodorosBeforeLongRest: Int
) {
    var timeLeft by remember(key1 = workMinutes, key2 = restMinutes, key3 = longRestMinutes) {
        mutableStateOf(workMinutes * 60)
    }
    var isRunning by remember { mutableStateOf(false) }
    var isWork by remember { mutableStateOf(true) }
    var isLongRest by remember { mutableStateOf(false) }
    var pomodoroCount by remember { mutableStateOf(0) }
    var isFirstStart by remember { mutableStateOf(true) }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val timeString = String.format("%02d:%02d", minutes, seconds)

    val modeText = when {
        isWork -> "Работаем"
        isLongRest -> "Длинный отдых"
        else -> "Отыхаем"
    }

    LaunchedEffect(isRunning, timeLeft, workMinutes, restMinutes, longRestMinutes) {
        if (isRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }

        if (timeLeft == 0 && isRunning) {
            if (isWork) {
                // Заканчивается РАБОТА
                theme.endSound?.let { soundManager.playSound(it) }

                pomodoroCount++

                if (pomodoroCount >= pomodorosBeforeLongRest) {
                    // Переход на ДЛИННЫЙ ОТДЫХ
                    isLongRest = true
                    isWork = false
                    timeLeft = longRestMinutes * 60
                    pomodoroCount = 0
                    // theme.longRestSound?.let { soundManager.playSound(it) }
                    // Пока используем тот же endSound
                    theme.endSound?.let { soundManager.playSound(it) }
                } else {
                    // Переход на КОРОТКИЙ ОТДЫХ
                    isWork = false
                    isLongRest = false
                    timeLeft = restMinutes * 60
                }
            } else {
                // Заканчивается ОТДЫХ (короткий или длинный)
                theme.startSound?.let { soundManager.playSound(it) }

                isWork = true
                isLongRest = false
                timeLeft = workMinutes * 60
            }
        }
    }

    LaunchedEffect(isRunning) {
        if (isRunning && isFirstStart) {
            isFirstStart = false
            theme.startSound?.let { soundManager.playSound(it) }
        }
        if (!isRunning) {
            isFirstStart = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            soundManager.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Фон (как был)
        if (theme.id == "Shrek pomodoro") {
            Image(
                painter = painterResource(id = R.drawable.shrek2),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
        }
        else if(theme.id == "Classic pomodoro"){
            Image(
                painter = painterResource(id = R.drawable.classic),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
        }
        else if(theme.id == "Kitchen"){
            Image(
                painter = painterResource(id = R.drawable.narutoback),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
        }
        else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(theme.backgroundColor))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = theme.emoji,
                fontSize = 64.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = modeText,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Tomato: $pomodoroCount/$pomodorosBeforeLongRest",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = timeString,
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold,
                color = Color(theme.timerColor),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { isRunning = !isRunning },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = if (isRunning) "Пауза" else "Старт",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }

                Button(
                    onClick = {
                        isRunning = false
                        isWork = true
                        isLongRest = false
                        pomodoroCount = 0  // ← ОБНУЛЯЕМ СЧЁТЧИК!
                        timeLeft = workMinutes * 60
                        soundManager.stopSound()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Сброс",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            TextButton(
                onClick = onBack
            ) {
                Text(
                    text = "Назад к выбору темы",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 25.sp
                )
            }
        }
    }
}