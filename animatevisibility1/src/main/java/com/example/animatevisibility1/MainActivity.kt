/*
    1. Программа анимирует нажатие кнопок [Show] и [Hide]
        Анимируется при нажатии кнопки ее размер и цвет.
        Формально кнопки нажимаются, чтобы скрыть/показать Box
    2. Программа проверяет различные варианты анимаций,
        чтобы скрыть/показать Box
    3. Выяснилось, что комбинирование эффектов анимации сложнее (пока не сделал!)

 */
package com.example.animatevisibility1

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.ui.unit.IntOffset
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
        //Box анимирует из точки в центре верхней линии бокса и затем обратно в точку
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

        /*
        // 1. Box схлопывается  по горизонтали до уменьшающейся центральной вертикальной линии и обратно
        // (для expandHorizontally / expandVertically)
        // 2. и соответственно схлопывается  по вертикали до верхней горизонтальной линии и обратно
        // (для expandVertically / shrinkVertically)
//        AnimatedVisibility(
//            visible = boxVisible,
//            enter = expandHorizontally (animationSpec = tween(durationMillis = 1000)),
////            enter = expandVertically (/*expandFrom = Alignment.CenterVertically, */animationSpec = tween(durationMillis = 1000)),
//            exit = shrinkHorizontally (animationSpec = tween(durationMillis = 1000))
////            exit = shrinkVertically (/*shrinkTowards = Alignment.CenterVertically, */animationSpec = tween(durationMillis = 1000))
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(200.dp)
//                    .background(Color.Blue)
//            )
//        }
         */

        /*
        //Box анимирует из точки центра бокса и затем обратно в эту точку
        AnimatedVisibility(
            visible = boxVisible,
            enter = scaleIn (animationSpec = tween(durationMillis = 1000)),
            exit = scaleOut (animationSpec = tween(durationMillis = 1000))
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Blue)
            )
        }
         */

        /*
        // 1. Для slideInHorizontally/slideOutHorizontally Box появляется полностью
        // и скользит вдоль гориз. оси к нужному месту,
        // а затем все наоборот
        // initialOffsetX: если <0, то скользит слева на указ. значение (в Dp),
        //                 если >0, то скользит справа
        // targetOffsetX: аналогично
        // 2. Для slideInVertically/slideOutVertically все аналогично, только по верт. оси
        AnimatedVisibility(
            visible = boxVisible,
//            enter = slideInHorizontally (initialOffsetX = {-500}, animationSpec = tween(durationMillis = 1000)),
//            exit = slideOutHorizontally (targetOffsetX = {+500}, animationSpec = tween(durationMillis = 1000))
            enter = slideInVertically (initialOffsetY = {-500}, animationSpec = tween(durationMillis = 1000)),
            exit = slideOutVertically (targetOffsetY = {+500}, animationSpec = tween(durationMillis = 1000))
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Blue)
            )
        }
         */

        /*
        // При slideIn Box появляется и скользит под настраиваемым углом
        // (в примере ниже - из левого нижнего угла с расстояния 500dp)
        // При slideOut Box скользит под настраиваемым углом и исчезает
        // (в примере ниже - к правому нижнему углу на расстояние 500dp)
        AnimatedVisibility(
            visible = boxVisible,
            enter = slideIn(
                initialOffset = { IntOffset(-500, +500) },
                animationSpec = tween(durationMillis = 1000)
            ),
            exit = slideOut(
                targetOffset = { IntOffset(500, +500) },
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

//        /*
        // FastOutSlowInEasing:     Быстрое начало(FastOut), плавное замедление(SlowIn)
        // LinearOutSlowInEasing:   Подходит для анимаций,
        //                          где важно быстро начать движение, но не резко заканчивать.
        //                          Отличается от FastOutSlowInEasing более плавным стартом
        // FastOutLinearInEasing:   Быстрый старт → далее равномерное движение → резковатое завершение.
        // LinearEasing:            Равномерное движение объекта по прямой без разгона и торможения
        // CubicBezierEasing:       Определяется изменением скорости по четырем точкам внутри кривой Безье
        AnimatedVisibility(
            visible = boxVisible,
//            enter = slideInHorizontally(animationSpec = tween(durationMillis = 5000, easing = FastOutSlowInEasing)),
//            enter = slideInHorizontally(animationSpec = tween(durationMillis = 5000, easing = LinearOutSlowInEasing)),
//            enter = slideInHorizontally(animationSpec = tween(durationMillis = 5000, easing = FastOutLinearInEasing)),
//            enter = slideInHorizontally(animationSpec = tween(durationMillis = 5000, easing = LinearEasing)),
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 5000,
                easing = CubicBezierEasing(0f, 1f, 0.5f,1f))),
            exit =  slideOutVertically()
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Blue)
            )
        }
//         */
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