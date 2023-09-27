package com.harshnandwani.digitaltijori.presentation.startup

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.presentation.common_components.InputTextField
import com.harshnandwani.digitaltijori.presentation.common_components.RoundedFilledButton
import com.harshnandwani.digitaltijori.presentation.startup.util.RestoreStatus
import com.harshnandwani.digitaltijori.presentation.startup.util.StartupEvent
import com.harshnandwani.digitaltijori.presentation.startup.util.StartupState

@Composable
fun RestoreScreen(
    viewModel: StartupViewModel,
    uiState: StartupState,
    pickBackupFile: () -> Unit,
    nextAction: () -> Unit
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var passwordVisibility by remember { mutableStateOf(false) }
    val icon = if(passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility

    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Restore digital tijori backup",
                    fontSize = 18.sp
                )
            },
            backgroundColor = MaterialTheme.colors.primary
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(text = stringResource(id = R.string.restore_backup_description))
            Spacer(modifier = Modifier.size(32.dp))

            Column(
                modifier = Modifier
                    .clickable(enabled = true, onClick = pickBackupFile)
                    .border(
                        BorderStroke(width = 1.dp, color = Color.Gray),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val backupFileSelected = uiState.backupFileUri != null
                val iconId = if (backupFileSelected) R.drawable.ic_file_attached else R.drawable.ic_attach_file
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = "Icon attach file",
                    Modifier.size(48.dp)
                )
                Text(text = uiState.backupFileName ?: "Choose backup file")
            }

            InputTextField(
                label = "Enter your secret key",
                value = uiState.secretKey,
                onValueChange = { viewModel.onEvent(StartupEvent.EnteredSecretKey(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = icon, contentDescription = "Visibility Icon")
                    }
                },
                visualTransformation = if(passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.size(16.dp))
            val restoreStatus = uiState.restoreStatus
            when (restoreStatus) {
                RestoreStatus.NOT_STARTED, RestoreStatus.FAILED -> {
                    if (restoreStatus == RestoreStatus.FAILED)
                        Text(text = uiState.restoreErrorMessage, color = MaterialTheme.colors.error)
                    RoundedFilledButton(
                        onClick = {
                            viewModel.onEvent(StartupEvent.StartRestore)
                            focusManager.clearFocus()
                        },
                        text = "Restore data"
                    )
                }
                RestoreStatus.STARTED -> CircularProgressIndicator()
                else -> {
                    Toast.makeText(context, "Restore success", Toast.LENGTH_SHORT).show()
                    nextAction()
                }
            }

            if (restoreStatus != RestoreStatus.STARTED)
                RoundedFilledButton(
                    onClick = {
                        viewModel.onEvent(StartupEvent.RestoreDoneOrSkipped)
                        nextAction()
                    },
                    text = if (restoreStatus == RestoreStatus.SUCCESS) "Continue" else "Skip restore"
                )
        }
    }
}
