/*
    Программа анимирует нажатие кнопок [Show] и [Hide]
    Анимируется при нажатии кнопки ее размер и цвет.
    Формально кнопки нажимаются, чтобы скрыть/показать Box
 */
package com.example.animatevisibility1

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animatevisibility1.ui.theme.AnimateVisibility1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimateVisibility1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var boxVisible by remember {mutableStateOf(true)}
    val onClick = { newState: Boolean ->
        boxVisible = newState
    }
    Column(
        Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomButton(text = "Show", targetState = true, onClick = onClick)
            CustomButton(text = "Hide", targetState = false, onClick = onClick)
        }

        Spacer(modifier = Modifier.height(20.dp))
        /*
        //Box анимирует из полностью прозрачного в полностью непрозрачный
        // и обратно
        AnimatedVisibility(
            visible = boxVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000))
        )
        {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Blue)
            )
        }
        */

        /*
        //Box анимирует из точки и затем обратно в точку
        AnimatedVisibility(
            visible = boxVisible,
            enter = expandIn(
                expandFrom = Alignment.Center,
                animationSpec = tween(durationMillis = 1000)
            ),
            exit = shrinkOut(
                shrinkTowards = Alignment.Center,
                animationSpec = tween(durationMillis = 1000)
            )
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Blue)
            )
        }
        */
        //Box схлопывается  по горизонтали до уменьшающейся центральной вертикальной линии и обратно
        // (для expandHorizontally / expandVertically)
        // и соответственно схлопывается  по вертикали до верхней горизонтальной линии и обратно
        // (для expandVertically / shrinkVertically)
        AnimatedVisibility(
            visible = boxVisible,
            enter = expandHorizontally (animationSpec = tween(durationMillis = 1000)),
//            enter = expandVertically (/*expandFrom = Alignment.CenterVertically, */animationSpec = tween(durationMillis = 1000)),
            exit = shrinkHorizontally (animationSpec = tween(durationMillis = 1000))
//            exit = shrinkVertically (/*shrinkTowards = Alignment.CenterVertically, */animationSpec = tween(durationMillis = 1000))
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Blue)
            )
        }


    }
}

@Composable
fun CustomButton(
    text: String,
    targetState: Boolean,
    onClick: (Boolean) -> Unit,
    bgColor: Color = Color.Blue
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Анимация цвета: затемнение на 30% при нажатии
    val animatedColor by animateColorAsState(
        targetValue = if (isPressed) bgColor.darken(0.3f) else bgColor,
        animationSpec = tween(durationMillis = 150, easing = FastOutSlowInEasing)
    )

    // Анимация масштаба: уменьшение на 25% при нажатии
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.75f else 1f,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing)
    )

    Button(
        onClick = { onClick(targetState) },
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = animatedColor,
            contentColor = Color.White
        ),
        modifier = Modifier.scale(scale)
    ) {
        Text(text)
    }
}

fun Color.darken(factor: Float): Color {
    return Color(
        red = (this.red * factor).coerceIn(0f, 1f),
        green = (this.green * factor).coerceIn(0f, 1f),
        blue = (this.blue * factor).coerceIn(0f, 1f),
        alpha = this.alpha
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AnimateVisibility1Theme {
        MainScreen()
    }
}