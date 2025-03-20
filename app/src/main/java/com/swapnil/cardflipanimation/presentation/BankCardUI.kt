package com.swapnil.cardflipanimation.presentation

import android.icu.text.ListFormatter.Width
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swapnil.cardflipanimation.presentation.components.BankCardBackground
import com.swapnil.cardflipanimation.presentation.components.BankCardLabelAndText
import com.swapnil.cardflipanimation.presentation.components.BankCardNumber
import com.swapnil.cardflipanimation.presentation.components.SpaceWrapper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

// Our star of the show: BankCardUi
@Composable
fun BankCardUi(
    modifier: Modifier = Modifier, // Modifier as Parameter
    baseColor: Color = Color(0xFF1252C8),
    cardNumber: String = "",
    cardHolder: String = "",
    expires: String = "",
    cvv: String = "",
    brand: String = "",
    priority: Float,
    index: Int,
    color: Color,
    onMoveToBack: () -> Unit,
    image: Int,
    imageHeight: Dp = 70.dp,
    imageWidth: Dp = 80.dp,
    imageYOffset: Dp = 22.dp,
    imageXOffset: Dp = 10.dp,
    isText: Boolean = false

) {
    // Bank Card Aspect Ratio
    val bankCardAspectRatio = 1.586f // (e.g., width:height = 85.60mm:53.98mm)
    val yOffset = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    val scale = remember { Animatable(priority) }
    var isRightFlick by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Offset cards so that the back cards are slightly visible
    val getInitialOffset = when (index) {
        0 -> 0f      // Front card (fully visible)
        1 -> -25f     // Middle card (partially visible)
        2 -> -50f     // Back card (even lower)
        3 -> -75f     // Back card (even lower)
        else -> 0f
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            // Aspect Ratio in Compose
            .aspectRatio(bankCardAspectRatio)
            .offset(y = (getInitialOffset + yOffset.value).dp)
            .graphicsLayer(
                rotationZ = rotation.value,
                scaleX = scale.value,
                scaleY = scale.value
            )
            .background(color, RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        isRightFlick = offset.x > size.width / 2
                    },
                    onDrag = { _, dragAmount ->
                        scope.launch {
                            yOffset.snapTo(yOffset.value - 100f)
                            rotation.snapTo(-100f / 5)
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            coroutineScope {
                                // Step 1: Animate the card off-screen (UP instead of DOWN)
                                val offsetJob = launch {
                                    yOffset.animateTo(
                                        targetValue = -250f, // Moves upwards instead of down
                                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                                    )
                                }
                                val rotationJob = launch {
                                    rotation.animateTo(
                                        targetValue = if (isRightFlick) 360f else -360f,
                                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                                    )
                                }
                                val scaleJob = launch {
                                    scale.animateTo(
                                        targetValue = 0.8f,
                                        animationSpec = tween(300)
                                    )
                                }
                                joinAll(
                                    offsetJob,
                                    rotationJob,
                                    scaleJob
                                ) // Wait for both animations to complete

                            }
                            // Step 2: Swap card position (AFTER animation)
                            onMoveToBack()

                            // Step 3: Reset animations
                            yOffset.snapTo(0f)
                            rotation.snapTo(0f)
                            scale.snapTo(priority)
                        }
                    }
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Box {
            BankCardBackground(baseColor = baseColor)
            BankCardNumber(cardNumber = cardNumber)
            // Positioned to corner top left
            SpaceWrapper(
                modifier = Modifier.align(Alignment.TopStart),
                space = 32.dp,
                top = true,
                left = true
            ) {
                BankCardLabelAndText(label = "card holder", text = cardHolder)
            }
            // Positioned to corner bottom left
            SpaceWrapper(
                modifier = Modifier.align(Alignment.BottomStart),
                space = 32.dp,
                bottom = true,
                left = true
            ) {
                Row {
                    BankCardLabelAndText(label = "expires", text = expires)
                    Spacer(modifier = Modifier.width(16.dp))
                    BankCardLabelAndText(label = "cvv", text = cvv)
                }
            }
            // Positioned to corner bottom right
            SpaceWrapper(
                modifier = Modifier.align(Alignment.BottomEnd),
                space = 32.dp,
                bottom = true,
                right = true
            ) {
                if (isText) {
                    Text(
                        text = brand, style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.W500,
                            fontStyle = FontStyle.Italic,
                            fontSize = 22.sp,
                            letterSpacing = 1.sp,
                            color = Color.White
                        )
                    )
                } else {
                    Image(
                        painterResource(id = image),
                        contentDescription = "Brand",
                        modifier = Modifier
                            .width(imageWidth)
                            .height(imageHeight)
                            .offset(x = imageXOffset, y = imageYOffset),
                        contentScale = ContentScale.FillBounds,
                    )
                }

            }
        }
    }
}





