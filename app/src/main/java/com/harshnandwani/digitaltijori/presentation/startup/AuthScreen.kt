package com.harshnandwani.digitaltijori.presentation.startup

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import kotlin.system.exitProcess

@Composable
fun AuthScreen(authAvailable: Boolean, promptForAuth: () -> Unit) {

    promptForAuth()

    if (authAvailable) {
        return
    }

    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "No screen lock set") },
        text = { Text(text = "To continue using Digital Tijori, goto your phone settings and set a screen lock which can be PIN/Pattern/Password/Fingerprint") },
        confirmButton = {
            TextButton(onClick = { exitProcess(-1) }) {
                Text(text = "Close app")
            }
        }
    )

}
