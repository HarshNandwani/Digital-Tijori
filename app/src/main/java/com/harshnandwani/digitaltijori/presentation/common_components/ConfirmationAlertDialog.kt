package com.harshnandwani.digitaltijori.presentation.common_components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ConfirmationAlertDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    title: String,
    text: String,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = { Text(text = text) },
            confirmButton = confirmButton,
            dismissButton = dismissButton,
            modifier = modifier
        )
    }
}
