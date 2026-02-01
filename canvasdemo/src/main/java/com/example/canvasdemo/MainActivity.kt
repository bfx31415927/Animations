package com.example.canvasdemo

import android.R.attr.x
import androidx.compose.foundation.Canvas
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.canvasdemo.ui.theme.MainScreenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreenTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun DrawLines() {
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .background(Color(0xFFADD8E6))
    ) {
        val height = size.height
        val width = size.width

        drawLine(
            start = Offset(x=0f, y = 0f),
            end = Offset(x = width, y = height),
            color = Color.Blue,
            strokeWidth = 16.0f
        )

        drawLine(
            start = Offset(x=width, y = 0f),
            end = Offset(x = 0f, y = height),
            color = Color.Green,
            strokeWidth = 16.0f,
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(30f, 10f, 10f, 10f), 0f)
        )
    }
}

@Composable
fun DrawRects() {
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .background(Color(0xFFADD8E6))
    ){
        val size = Size(66.dp.toPx(), 33.dp.toPx())

        drawRect(
            color = Color.Blue,
            size = size
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Canvas(
        modifier = Modifier
            .size(100.dp)
        .background(Color(0xFFADD8E6))
    ) {
        drawRect(
            color = Color.Blue,
            size = size / 2f
        )

        drawRect(
            color = Color.Blue,
            topLeft = Offset(x = 60.dp.toPx(), y = 60.dp.toPx()),
            size = size / 3f
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Canvas(
        modifier = Modifier
            .size(100.dp)
            .background(Color(0xFFADD8E6))
    ){
// inset: Сдвигает начало координат (0,0) на (20f, 10f), как ниже в примере
//        и уменьшает доступный размер size на эти значения:
//                      size.width -= 20f
//                      size.height -= 10f
        inset(20f, 10f) {
            drawRect(
                color = Color.Blue,
                size = size / 2f
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Canvas(
        modifier = Modifier
            .size(100.dp)
            .background(Color(0xFFADD8E6))
    ) {
        drawRoundRect(
            color = Color.Blue,
            size = size / 2.5f,
//            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 10.dp.toPx(), y = 10.dp.toPx()),
            cornerRadius = CornerRadius(
                x = 10.dp.toPx(),
                y = 10.dp.toPx()
            )
        )

        drawRoundRect(
            color = Color.Blue,
            size = size / 3f,
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x = 60.dp.toPx(), y = 60.dp.toPx()),
            cornerRadius = CornerRadius(
                x = 10.dp.toPx(),
                y = 10.dp.toPx()
            )
        )

        drawRoundRect(
            color = Color.Blue,
            size = size / 3f,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(10f, 6f), // 10px линия, 6px пробел
                    phase = 0f
                )
            ),
            topLeft = Offset(x = 10.dp.toPx(), y = 60.dp.toPx()),
            cornerRadius = CornerRadius(
                x = 10.dp.toPx(),
                y = 10.dp.toPx()
            )
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Canvas(
        modifier = Modifier
            .size(100.dp)
            .background(Color(0xFFADD8E6))
    ){
        rotate(45f) {
            drawRect(
                color = Color.Blue,
                topLeft = Offset(x = 25.dp.toPx(), y = 25.dp.toPx()),
                size = size / 2f
            )
        }
    }

}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DrawLines()

        Spacer(modifier = Modifier.height(20.dp))

        DrawRects()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainScreenTheme {
        MainScreen()
    }
}