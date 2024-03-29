package com.harshnandwani.digitaltijori.presentation.home

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.AddEditBankAccountActivity
import com.harshnandwani.digitaltijori.presentation.card.add_edit.AddEditCardActivity
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.AddEditCredentialActivity
import com.harshnandwani.digitaltijori.presentation.home.components.AboutAppDialog
import com.harshnandwani.digitaltijori.presentation.home.components.AppBarWithSearchView
import com.harshnandwani.digitaltijori.presentation.home.components.BackupDialog
import com.harshnandwani.digitaltijori.presentation.home.components.BottomHomeBar
import com.harshnandwani.digitaltijori.presentation.home.components.HomeNavGraph
import com.harshnandwani.digitaltijori.presentation.home.util.BackupStatus
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreenEvent
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreens
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import java.io.File

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBarWithSearchView(
                titleText = uiState.currentPage,
                searchText = uiState.searchText,
                onSearchTextChange = {
                    viewModel.onEvent(HomeScreenEvent.OnSearchTextChanged(it))
                },
                onSearchImeClicked = {
                    viewModel.onEvent(HomeScreenEvent.OnSearchDone)
                },
                onCloseClicked = {
                    viewModel.onEvent(HomeScreenEvent.OnSearchDone)
                },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                viewModel.onEvent(HomeScreenEvent.ShowBackupToggle(true))
                                showMenu = false
                            }
                        ) {
                            Text(text = "Backup")
                        }
                        DropdownMenuItem(
                            onClick = {
                                showMenu = false
                                viewModel.onEvent(HomeScreenEvent.ShowAboutAppToggle(true))
                            }
                        ) {
                            Text(text = "About app")
                        }
                        DropdownMenuItem(
                            onClick = {
                                showMenu = false
                                val addresses = arrayOf(context.resources.getString(R.string.feedback_email))
                                Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:")
                                    putExtra(Intent.EXTRA_EMAIL, addresses)
                                    putExtra(Intent.EXTRA_SUBJECT, "Digital Tijori app feedback")
                                    context.startActivity(this)
                                }
                            }
                        ) {
                            Text(text = "Send feedback")
                        }
                    }
                }
            )         
        },
        bottomBar = { BottomHomeBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (navController.currentBackStackEntry?.destination?.route) {
                        HomeScreens.BankAccountsList.route -> {
                            Intent(context, AddEditBankAccountActivity::class.java).apply {
                                putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_ADD)
                                context.startActivity(this)
                            }
                        }
                        HomeScreens.CardsList.route -> {
                            Intent(context, AddEditCardActivity::class.java).apply {
                                putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_ADD)
                                context.startActivity(this)
                            }
                        }
                        HomeScreens.CredentialsList.route -> {
                            Intent(context, AddEditCredentialActivity::class.java).apply {
                                putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_ADD)
                                context.startActivity(this)
                            }
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeNavGraph(viewModel, navController)
        }
    }


    AboutAppDialog(
        isVisible = uiState.showAboutApp,
        onDismissRequest = { doNotShowAgain ->
            viewModel.onEvent(HomeScreenEvent.ShowAboutAppToggle(false))
            if (doNotShowAgain)
                viewModel.onEvent(HomeScreenEvent.DoNotShowAboutAppAgain)
        }
    )

    val shareBackup = fun() {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            putExtra(Intent.EXTRA_SUBJECT, "Digital Tijori encrypted backup file")
            val backupFileURI = FileProvider.getUriForFile(
                context,
                context.packageName + ".fileprovider",
                File("${context.filesDir}/backups/digital_tijori_encrypted_backup.txt")
            )
            putExtra(Intent.EXTRA_STREAM, backupFileURI)
            context.startActivity(Intent.createChooser(this, "Save your encrypted backup file"))
        }
    }

    if (uiState.backupStatus == BackupStatus.NO_DATA) {
        Toast.makeText(context, "No data to backup", Toast.LENGTH_SHORT).show()
        viewModel.onEvent(HomeScreenEvent.BackupCancelled)
    }

    BackupDialog(
        isVisible = uiState.showBackup,
        backupStatus = uiState.backupStatus,
        onDismissRequest = { shouldDismiss, shouldCreateBackup, key ->
            if (shouldDismiss)
                viewModel.onEvent(HomeScreenEvent.BackupCancelled)
            if (shouldCreateBackup) {
                viewModel.onEvent(HomeScreenEvent.CreateBackup(key))
            }
        },
        shareBackup = {
            shareBackup()
            viewModel.onEvent(HomeScreenEvent.BackupCancelled)
        }
    )
}
