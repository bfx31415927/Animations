/*
    По поводу ключевых кадров(см. пример ниже):
    поведение анимации можно дополнительно настроить
    с помощью функции with, например:
    animationSpec = keyframes {
            durationMillis = 1000
            0.dp.at(10).with(LinearEasing)
            100.dp.at(500).with(FastOutSlowInEasing)
            200.dp.at(700).with(LinearOutSlowInEasing)
 */
package com.example.animcombine

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animcombine.ui.theme.AnimCombineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        enableEdgeToEdge()
        setContent {
            AnimCombineTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimCombine()
                }
            }
        }
    }
}

enum class BoxPosition {
    Start, End
}

@Composable
fun AnimCombine() {

    var boxState by remember { mutableStateOf(BoxPosition.Start) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val transition = updateTransition(targetState = boxState, label = "Color and Motion")

    val animatedColor: Color by transition.animateColor(
        transitionSpec = {
            tween(4000)
        },
            label = "ColorAnimation"
    ) { state ->
            when (state) {
                BoxPosition.Start -> Color.Red
                BoxPosition.End -> Color.Magenta
            }
    }

    val animatedOffset: Dp by transition.animateDp(
        transitionSpec = {
            tween(4000)
        },
        label = "offsetAnimation"
    ) { state ->
        when (state) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - 70.dp
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(), // заполняем весь экран
    ) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = 20.dp)
                .size(70.dp)
                .background(animatedColor)
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
            Text(text = "Start Animation")
        }
    }
}