package com.harshnandwani.digitaltijori.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = EerieBlack,
    primaryVariant = EerieBlack,
    onPrimary = LighterSilver,
    secondary = LighterRoyalBlue,
    onSecondary = LighterSilver,
    background = Black,
    onBackground = LighterSilver,
    error = LighterErrorRed
)

private val LightColorPalette = lightColors(
    primary = RoyalBlue,
    primaryVariant = RoyalBlue,
    secondary = RoyalBlue,
    background = White,
    onBackground = Black,
    error = ErrorRed
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
