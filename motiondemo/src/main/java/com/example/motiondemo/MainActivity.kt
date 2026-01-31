package com.example.motiondemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat.enableEdgeToEdge
import com.example.motiondemo.ui.theme.MotionDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MotionDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MotionDemo()
                }
            }
        }
    }
}

enum class BoxPosition {
    Start, End
}

@Composable
fun MotionDemo() {
    var boxState by remember { mutableStateOf(BoxPosition.Start) }
    val boxSizeLength = 70.dp

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val animateOffset by animateDpAsState(
        targetValue = when (boxState) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - boxSizeLength
        },
        animationSpec = tween(durationMillis = 5000), label = "Motion"
    )

    Column(
        modifier = Modifier.fillMaxWidth(), // заполняем весь экран
    ) {
        Box(
            modifier = Modifier
                .offset(x = animateOffset, y = 20.dp)
                .size(boxSizeLength)
                .background(Color.Red)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                boxState = when (boxState) {
                    BoxPosition.Start -> BoxPosition.End
                    BoxPosition.End -> BoxPosition.Start
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Move Box")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MotionDemoTheme {
        MotionDemo()
    }
}
