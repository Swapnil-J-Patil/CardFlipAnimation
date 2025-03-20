package com.swapnil.cardflipanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.swapnil.cardflipanimation.presentation.BankCardUi
import com.swapnil.cardflipanimation.presentation.ui.theme.CardFlipAnimationTheme
import com.swapnil.cardflipanimation.presentation.ui.theme.colorGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardFlipAnimationTheme {
                val priorities = remember { mutableStateListOf(1f, 0.95f, 0.9f, 0.85f) }
                val colors = remember {
                    mutableStateListOf(
                        Color(0xFFFF9800), // Bright Yellow-Orange
                        Color(0xFF06740B),  // Bold Orange-Red
                        Color(0xFF921942), // Rich Pink
                        Color(0xFF035FAF), // Vibrant Blue
                    )
                }
                val cardNumber = remember {
                    mutableStateListOf(
                        "1234567890123456",
                        "1234567890126653",
                        "1234567890122257",
                        "1234567890121145",
                    )
                }
                val cardHolder = remember {
                    mutableStateListOf(
                        "Swapnil Patil",
                        "Vaibhav Mane",
                        "Sanket Mhatre",
                        "Ganesh Shinde",
                    )
                }
                val cardExpiry = remember {
                    mutableStateListOf(
                        "01/29",
                        "05/27",
                        "03/28",
                        "02/26",
                    )
                }
                val cvv = remember {
                    mutableStateListOf(
                        "445",
                        "365",
                        "895",
                        "624",
                    )
                }
                val images= remember {
                    mutableStateListOf(
                        R.drawable.visa,
                        R.drawable.mastercard,
                        R.drawable.visa,
                        R.drawable.mastercard
                    )
                }


                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    for (i in 3 downTo 0) {
                        BankCardUi(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 30.dp),
                            baseColor = colors[i],
                            cardNumber = cardNumber[i],
                            cardHolder = cardHolder[i],
                            expires = cardExpiry[i],
                            cvv = cvv[i],
                            brand = "VISA",
                            color = colors[i],  // Use dynamic colors
                            priority = priorities[i],
                            index = i, // Pass index to adjust Y-offset correctly
                            onMoveToBack = {
                                // Rotate list
                                priorities.add(priorities.removeAt(0))
                                colors.add(colors.removeAt(0))
                                cardNumber.add(cardNumber.removeAt(0))
                                cardHolder.add(cardHolder.removeAt(0))
                                cardExpiry.add(cardExpiry.removeAt(0))
                                cvv.add(cvv.removeAt(0))
                                images.add(images.removeAt(0))
                            },
                            image = images[i],

                        )
                    }
                }

            }
        }
    }
}

