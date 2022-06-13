package com.notarmaso.beeritupcompose.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color



private val ColorPalette = darkColors(
    primary =  Color(0xFF494646),
    onPrimary = Color(0xFFC7F0BD),
    background = Color(0xFF388659),
    onBackground = Color(0xFFC7F0BD),
    secondary = Color(0xFFFFD1C2),
    surface = Color(0xFFC7F0BD)
    //primaryVariant = Purple700,
    //secondary = Teal200
)



@Composable
fun BeerItUpTheme(
    content: @Composable () -> Unit
) {
    val colors = ColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}