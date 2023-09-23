package com.harshnandwani.digitaltijori.presentation.startup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.presentation.common_components.InputTextField
import com.harshnandwani.digitaltijori.presentation.common_components.RoundedFilledButton
import com.harshnandwani.digitaltijori.presentation.startup.util.RestoreStatus
import com.harshnandwani.digitaltijori.presentation.startup.util.StartupEvent

@ExperimentalComposeUiApi
@Composable
fun RestoreScreen(viewModel: StartupViewModel, pickBackupFile: () -> Unit, nextAction: () -> Unit) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Restore digital tijori backup",
                    fontSize = 18.sp,
                    //style = MaterialTheme.typography.h1
                )
            }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 36.dp, vertical = 24.dp)
        ) {
            Text(text = stringResource(id = R.string.restore_backup_description))
            Spacer(modifier = Modifier.size(32.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable(enabled = true, onClick = pickBackupFile)
            ) {
                val backupFileSelected = viewModel.state.value.backupFileUri != null
                val iconId = if (backupFileSelected) R.drawable.ic_file_attached else R.drawable.ic_attach_file
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = "Icon attach file",
                    Modifier.size(48.dp)
                )
                Text(text = viewModel.state.value.backupFileName ?: "Choose backup file")
            }

            InputTextField(
                label = "Enter your secret key",
                value = viewModel.state.value.secretKey,
                onValueChange = { viewModel.onEvent(StartupEvent.EnteredSecretKey(it)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.size(16.dp))
            val restoreStatus = viewModel.state.value.restoreStatus
            when (restoreStatus) {
                RestoreStatus.NOT_STARTED, RestoreStatus.FAILED -> {
                    if (restoreStatus == RestoreStatus.FAILED)
                        Text(text = viewModel.state.value.restoreErrorMessage, color = Color.Red)
                    RoundedFilledButton(
                        onClick = { viewModel.onEvent(StartupEvent.StartRestore) },
                        text = "Restore data"
                    )
                }
                RestoreStatus.STARTED -> CircularProgressIndicator()
                else -> Text(text = "Restore success!")
            }

            Spacer(modifier = Modifier.weight(1f))

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
