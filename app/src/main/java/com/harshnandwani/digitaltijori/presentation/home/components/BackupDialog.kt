package com.harshnandwani.digitaltijori.presentation.home.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.presentation.common_components.InputTextField

@ExperimentalComposeUiApi
@Composable
fun BackupDialog(
    isVisible: Boolean,
    onDismissRequest: (Boolean, String) -> Unit
) {
    if (!isVisible) {
        return
    }

    var key by remember { mutableStateOf("") }
    val context = LocalContext.current

    var showMore by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismissRequest(false, "") },
        title = { Text(text = "Create an encrypted backup") },
        text = {
            Column {
                Text(text = "Your backup will be encrypted and secure!")
                Text(
                    text = "Know more",
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable { showMore = !showMore }
                )

                if (showMore)
                    Text(text = stringResource(id = R.string.backup_description))

                InputTextField(
                    label = "Enter secret key",
                    value = key,
                    onValueChange = { key = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go)
                )

                Text(text = "You must remember this key for restoring your backup.")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (key.length < 8) {
                    Toast.makeText(
                        context,
                        "Your key should contain at least 8 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@TextButton
                }
                onDismissRequest(true, key)
            }) {
                Text(text = "Create backup")
            }
        }
    )
}
