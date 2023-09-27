package com.harshnandwani.digitaltijori.presentation.common_components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailedCardView(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val primarySurface = if (isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.surface

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 16.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = primarySurface
    ) {
        content()
    }
}
