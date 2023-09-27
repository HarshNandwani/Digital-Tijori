package com.harshnandwani.digitaltijori.presentation.home.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.presentation.common_components.InputTextField
import com.harshnandwani.digitaltijori.presentation.common_components.RoundedFilledButton
import com.harshnandwani.digitaltijori.presentation.home.util.BackupStatus
import com.harshnandwani.digitaltijori.presentation.home.util.BackupStatus.*

@Composable
fun BackupDialog(
    isVisible: Boolean,
    backupStatus: BackupStatus,
    onDismissRequest: (Boolean, Boolean, String) -> Unit,
    shareBackup: () -> Unit
) {
    if (!isVisible) {
        return
    }

    var key by remember { mutableStateOf("") }
    val context = LocalContext.current

    var infoVisible by remember { mutableStateOf(true) }
    var showMore by remember { mutableStateOf(false) }


    fun createBackup() {
        if (key.length < 8) {
            Toast.makeText(
                context,
                "Your key should contain at least 8 characters",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        onDismissRequest(false, true, key)
    }

    AlertDialog(
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        onDismissRequest = { onDismissRequest(true, false, "") },
        title = {
            Text(
                text = when (backupStatus) {
                    NOT_STARTED -> "Create an encrypted backup"
                    STARTED -> "Creating backup"
                    FAILED -> "Failed"
                    COMPLETED -> "Backup file created, please save it"
                    else -> ""
                }
            )
        },
        text = {
            Column {

                if (infoVisible) {
                    Text(text = "Your backup will be encrypted and secure!")
                    if (showMore)
                        Text(text = stringResource(id = R.string.backup_description))
                    Text(
                        text = if (showMore) "show less" else "Know more",
                        fontSize = 12.sp,
                        modifier = Modifier.clickable { showMore = !showMore },
                        color = MaterialTheme.colors.secondary
                    )
                }

                when (backupStatus) {
                    NOT_STARTED -> {
                        InputTextField(
                            label = "Enter secret key",
                            value = key,
                            onValueChange = { key = it },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                            onImeAction = { createBackup() }
                        )

                        Text(text = "You must remember this key for restoring your backup.")
                    }

                    STARTED -> {
                        CircularProgressIndicator()
                        showMore = false
                    }

                    FAILED -> {
                        showMore = false
                        infoVisible = false
                        Text(text = "Backup failed, try later!")
                    }

                    COMPLETED -> {
                        showMore = false
                        infoVisible = false
                        Text(text = stringResource(id = R.string.backup_completed_message))
                    }
                    else -> {}
                }
            }
        },
        confirmButton = {
            if (backupStatus == STARTED || backupStatus == FAILED)
                return@AlertDialog
            RoundedFilledButton(
                onClick = {
                    if (backupStatus == COMPLETED) {
                        shareBackup()
                        return@RoundedFilledButton
                    }
                    createBackup()
                },
                text = if (backupStatus == COMPLETED) "Proceed to save" else "Create Backup",
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    val message = if (backupStatus == COMPLETED) "Backup file not saved" else "Backup cancelled"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    onDismissRequest(true, false, "")
                },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.secondary)
            ) {
                Text(text = "Cancel")
            }
        }
    )
}
