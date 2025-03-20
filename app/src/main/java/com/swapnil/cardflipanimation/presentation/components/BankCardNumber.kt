package com.swapnil.cardflipanimation.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BankCardNumber(cardNumber: String) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween, // Space out the children evenly
        verticalAlignment = Alignment.CenterVertically // Center the children vertically
    ) {
        // Draw the first three groups of dots
        repeat(3) {
            BankCardDotGroup()
        }

        // Display the last four digits
        Text(
            text = cardNumber.takeLast(4),
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 1.sp,
                color = Color.White
            )
        )
    }
}