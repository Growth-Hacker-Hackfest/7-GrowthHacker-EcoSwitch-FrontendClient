package com.example.ecoswitch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ecoswitch.R

val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 57.sp,
        lineHeight = 64.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(600)
        ),
    displayMedium = TextStyle(
        fontSize = 45.sp,
        lineHeight = 52.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(600)
        ),
    displaySmall = TextStyle(
        fontSize = 36.sp,
        lineHeight = 44.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(600)
        ),
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(600)
        ),
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(600)
        ),
    headlineSmall = TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(600)
        ),
    titleLarge = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(600)
        ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(600)
        ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(600)
        ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(500)
        ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(500),
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontSize = 11.sp,
        lineHeight = 16.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(500),
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(400)
        ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(400)
        ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        fontWeight = FontWeight(400)
        )
)