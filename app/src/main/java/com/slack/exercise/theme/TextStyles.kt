package com.slack.exercise.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

val name = TextStyle(
        color = nameColor,
        fontSize = nameSize,
        fontFamily = latoFontFamily,
        fontWeight = FontWeight.Bold,
        lineHeight = lineHeight,
)

val username = TextStyle(
        color = usernameColor,
        fontSize = nameSize,
        fontFamily = latoFontFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = lineHeight,
)