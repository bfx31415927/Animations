package com.example.simplethermometer

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplethermometer.ui.theme.SimpleThermometerTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleThermometerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val temperatures = listOf(-51, -25, +25, 70)
    var temperature  by remember {mutableStateOf(-51)}

    val animatedColor: Color by animateColorAsState(
        targetValue = when {
            temperature <= -50 ->  Color.Blue
            (temperature > -50).and(temperature < 0) -> Color(0xFF87CEFA) //светло-синий
            temperature >= 0 && temperature <= 50 -> Color.Green
            else -> Color.Red
        }
    )

    // Запускаем корутину для последовательного изменения температуры с задержкой
    LaunchedEffect(Unit) {
        for (temp in temperatures) {
            temperature = temp
            delay(2000) // Пауза в 2 секунды между изменениями
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(), // заполняем весь экран
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = 20.dp, height = 400.dp)
                .background(animatedColor)
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(text = temperature.toString() + "°C")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleThermometerTheme {
        MainScreen()
    }
}