package com.harshnandwani.digitaltijori.presentation.common_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundedFilledButton(
    onClick: () -> Unit,
    text: String,
    cornerSize: Dp = 4.dp
) {
    Button(
        onClick = onClick,
        elevation = null,
        shape = RoundedCornerShape(cornerSize),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}
