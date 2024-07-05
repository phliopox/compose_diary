package com.example.gradientdiary.presentation.theme

import com.example.gradientdiary.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle

//https://techblog.lycorp.co.jp/ko/how-to-develop-font-customizing-function-in-android-app
// Set of Material typography styles to start with
private val regular = FontFamily(Font(R.font.restart, FontWeight.Normal))/*
private val medium = FontFamily(Font(R.font.restart, FontWeight.Medium))
private val extraBold = FontFamily( Font(R.font.restart, FontWeight.ExtraBold))*/

//private val appFontFamily = FontFamily(regular, medium, extraBold)

/*val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)*/
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = regular,
        fontSize = 60.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = regular,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = regular,
        fontSize = 30.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = regular,
        fontSize = 18.sp
    ),
    titleLarge = TextStyle(
        fontFamily = regular,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = regular,
        fontSize = 18.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = regular,
        fontSize = 15.sp
    ),
    bodySmall = TextStyle(
        fontFamily = regular,
        fontSize = 14.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = regular,
        fontSize = 15.sp
    )
)

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
