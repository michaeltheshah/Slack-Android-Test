package com.slack.exercise.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.slack.exercise.R

val latoBold = Font(
        R.font.lato_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
)

val latoRegular = Font(
        R.font.lato_bold,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
)

val latoFontFamily = FontFamily(latoRegular, latoBold)