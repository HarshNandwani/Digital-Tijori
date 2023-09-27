package com.harshnandwani.digitaltijori.presentation.common_components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DeleteConfirmationDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    title: String,
    text: String,
    onDelete: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {

    val surfacePrimary: Color =
        if (isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.surface

    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = { Text(text = text) },
            confirmButton = {
                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.secondary)
                ) {
                    Text(text = "Yes, delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onCancel,
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.secondary)
                ) {
                    Text(text = "Cancel")
                }
            },
            modifier = modifier,
            backgroundColor = surfacePrimary
        )
    }
}
