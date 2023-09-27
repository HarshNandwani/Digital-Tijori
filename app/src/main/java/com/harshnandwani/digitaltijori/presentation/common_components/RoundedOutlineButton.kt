package com.harshnandwani.digitaltijori.presentation.common_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoundedOutlineButton(
    onClick: () -> Unit,
    text: String
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.secondary),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}
