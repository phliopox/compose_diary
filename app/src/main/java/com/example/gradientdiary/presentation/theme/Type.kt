package com.example.gradientdiary.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle



fun getTypography (fontFamily: FontFamily) : Typography {
    return Typography(
        displayLarge = TextStyle(
            fontFamily = fontFamily,
            fontSize = 60.sp,
        ),
        displayMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = fontFamily,
            fontSize = 30.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = fontFamily,
            fontSize = 18.sp
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontSize = 20.sp
        ),
        titleMedium = TextStyle(
            fontFamily = fontFamily,
            fontSize = 18.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = fontFamily,
            fontSize = 15.sp
        ),
        bodySmall = TextStyle(
            fontFamily = fontFamily,
            fontSize = 14.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = fontFamily,
            fontSize = 15.sp
        )
    )
}

/* Other default text styles to override
titleLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
),
labelSmall = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)
*/
