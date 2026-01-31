package com.example.animatevisibility2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animatevisibility2.ui.theme.AnimateVisibility2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimateVisibility2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var boxVisible by remember { mutableStateOf(true) }

///*
    // Для последнего варианта AnimatedVisibility
    val state = remember { MutableTransitionState(false) }
    state.apply { targetState = true }
//*/

    val onClick = { newState: Boolean ->
        boxVisible = newState
    }

///*
//Для последнего варианта AnimatedVisibility:
// чтобы по нажатию кнопки [Hide] сработало исчезновение боксов
    val onClick1 = { newState: Boolean ->
        state.apply { targetState = newState }
        Unit
    }
//*/

    Column(
        Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomButton(text = "Show", targetState = true, onClick = onClick)

//            CustomButton(text = "Hide", targetState = false, onClick = onClick)

//            /* Для последнего варианта AnimatedVisibility:
            CustomButton(text = "Hide", targetState = false, onClick = onClick1)
//             */
        }

        Spacer(modifier = Modifier.height(20.dp))

        /*
        // В этой анимации  первый бокс анимируется только через fadeIn(...) / fadeOut(...),
        // а второй объединяет обе анимации:
        //                                   fadeOut(...) / fadeIn(...) / fadeOut(...) +
        //                                   slideInVertically(...) /slideOutVertically(...)
        AnimatedVisibility(
            visible = boxVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 5500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 5500))
        )
        {
            Row {
                Box(
                    modifier = Modifier.size(150.dp)
                        .background(Color.Blue)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Box(
                    Modifier
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = tween(durationMillis = 5500)),
                            exit = slideOutVertically(
                                animationSpec = tween(durationMillis = 5500))
                        )
                        .size(150.dp)
                        .background(Color.Blue)
                )
            }
        }
        */

        /*
        // В этой анимации установка родителя для enter/exit в EnterTransition.None необходима,
        // чтобы боксы анимировались независимо друг от друга.
        AnimatedVisibility(
            visible = boxVisible,
            enter = EnterTransition.None,
            exit =  ExitTransition.None
        )
        {
            Row {
                Box(
                    Modifier
                        .animateEnterExit(
                            enter = fadeIn(animationSpec = tween(durationMillis = 5500)),
                            exit = fadeOut(animationSpec = tween(durationMillis = 5500))
                        )
                        .size(width = 150.dp, height = 150.dp)
                        .background(Color.Blue)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Box(
                    Modifier
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = tween(durationMillis = 5500)),
                            exit = slideOutVertically(
                                animationSpec = tween(durationMillis = 5500))
                        )
                        .size(150.dp)
                        .background(Color.Blue)
                )
            }
        }
//        */

//        /*
        // Эта автозапускаемая анимация (спец. параметр visibleState)
        // см. также onClick1 !!!!
        AnimatedVisibility(
            visibleState = state,
            enter = fadeIn(animationSpec = tween(durationMillis = 5000)),
            exit = slideOutVertically()
        )
        {
            Row {
                Box(
                    Modifier
                        .size(width = 150.dp, height = 150.dp)
                        .background(Color.Blue)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Box(
                    Modifier
                        .size(150.dp)
                        .background(Color.Blue)
                )
            }
        }
//        */

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
    AnimateVisibility2Theme {
        MainScreen()
    }
}