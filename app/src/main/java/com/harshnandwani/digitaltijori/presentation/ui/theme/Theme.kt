package com.harshnandwani.digitaltijori.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Arsenic,
    primaryVariant = Arsenic,
    onPrimary = SlateGray,
    secondary = Jade,
    secondaryVariant = Jade,
    background = KindaBlack
)

private val LightColorPalette = lightColors(
    primary = RoyalBlue,
    primaryVariant = RoyalBlue,
    secondary = RoyalBlue,
    secondaryVariant = RoyalBlue,
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DigitalTijoriTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}