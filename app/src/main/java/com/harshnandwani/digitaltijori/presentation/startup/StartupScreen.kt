package com.harshnandwani.digitaltijori.presentation.startup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StartupScreen(
    viewModel: StartupViewModel,
    pickBackupFile: () -> Unit,
    promptForAuth: () -> Unit,
    nextAction: () -> Unit
) {

    val state = viewModel.state.value

    if (state.restoreEligible == false && state.authSuccessful == true) {
        nextAction()
        return
    }

    Column {

        if (state.restoreEligible == null || state.shouldAuthenticate == null) {
            AppIconBackground()
            CircularProgressIndicator()
            return
        }

        if (state.restoreEligible == true) {
            RestoreScreen(viewModel, pickBackupFile, nextAction)
        } else if (state.shouldAuthenticate == true) {
            AppIconBackground()
            AuthScreen(viewModel, promptForAuth)
        } else if (state.restoreEligible == false && state.authSuccessful == true) {
            nextAction()
        }

    }
}

@Composable
fun AppIconBackground() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(56.dp))
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "",
            Modifier.fillMaxSize(0.2f),
            tint = MaterialTheme.colors.secondaryVariant
        )
    }
}
