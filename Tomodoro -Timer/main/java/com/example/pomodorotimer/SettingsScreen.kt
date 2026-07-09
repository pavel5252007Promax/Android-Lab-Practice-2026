package com.example.pomodorotimer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    workMinutes: Int,
    restMinutes: Int,
    longRestMinutes: Int,
    pomodorosBeforeLongRest: Int,
    onWorkMinutesChange: (Int) -> Unit,
    onRestMinutesChange: (Int) -> Unit,
    onLongRestMinutesChange: (Int) -> Unit,
    onPomodorosBeforeLongRestChange: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Настройки",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Время работы
        SettingsCard(
            title = "Время работы",
            value = workMinutes,
            onValueChange = onWorkMinutesChange,
            minValue = 1,
            maxValue = 60,
            unit = "мин"
        )

        // Время отдыха
        SettingsCard(
            title = "Время отдыха",
            value = restMinutes,
            onValueChange = onRestMinutesChange,
            minValue = 1,
            maxValue = 30,
            unit = "мин"
        )

        // Длинный отдых
        SettingsCard(
            title = "Длинный отдых",
            value = longRestMinutes,
            onValueChange = onLongRestMinutesChange,
            minValue = 10,
            maxValue = 60,
            unit = "мин"
        )

        // Количество помодоро до длинного отдыха
        SettingsCard(
            title = "Кол-во циклов до длинного отдыха",
            value = pomodorosBeforeLongRest,
            onValueChange = onPomodorosBeforeLongRestChange,
            minValue = 2,
            maxValue = 8,
            unit = "шт"  // ← здесь штуки!
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(0.7f),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = "Сохранить и вернуться",
                fontSize =19.sp
            )}
    }
}

@Composable
fun SettingsCard(
    title: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    minValue: Int,
    maxValue: Int,
    unit: String = "мин"
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD0D0D0)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        if (value > minValue) {
                            onValueChange(value - 1)
                        }
                    },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.size(48.dp)
                ) {
                    Text(text = "−", fontSize = 20.sp)
                }

                Text(
                    text = "$value $unit",  // ← здесь показываем единицу
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Button(
                    onClick = {
                        if (value < maxValue) {
                            onValueChange(value + 1)
                        }
                    },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.size(48.dp)
                ) {
                    Text(text = "+", fontSize = 20.sp)
                }
            }
        }
    }
}