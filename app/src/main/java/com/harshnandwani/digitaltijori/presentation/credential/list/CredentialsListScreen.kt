package com.harshnandwani.digitaltijori.presentation.credential.list

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harshnandwani.digitaltijori.presentation.common_components.Swipeable
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.AddEditCredentialActivity
import com.harshnandwani.digitaltijori.presentation.credential.detailed_view.DetailedCredentialActivity
import com.harshnandwani.digitaltijori.presentation.credential.list.components.SingleCredentialItem
import com.harshnandwani.digitaltijori.presentation.home.HomeViewModel
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CredentialsListScreen(viewModel: HomeViewModel) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (uiState.credentials.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "",
                modifier = Modifier.alpha(0.1f).fillMaxSize(0.5f)
            )
        }
        return
    }

    LazyColumn {
        uiState.filteredCredentials.forEach { credential ->
            item {
                Swipeable(
                    swipeToLeftEnabled = true,
                    rightIcon = Icons.Default.ContentCopy,
                    leftSwipeAction = {
                        coroutineScope.launch {
                            val data = coroutineScope.async(Dispatchers.IO) {
                                viewModel.getCredential(credential.credentialId)
                            }
                            withContext(Dispatchers.Main) {
                                val credentialToCopy = data.await()
                                if (credentialToCopy == null) {
                                    Toast.makeText(context, "Credential is null", Toast.LENGTH_SHORT).show()
                                    return@withContext
                                }
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip: ClipData = ClipData.newPlainText("Username", credentialToCopy.username)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Username Copied", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    swipeToRightEnabled = true,
                    leftIcon = Icons.Default.Edit,
                    rightSwipeAction = {
                        coroutineScope.launch {
                            val data = coroutineScope.async(Dispatchers.IO) {
                                viewModel.getCredential(credential.credentialId)
                            }
                            withContext(Dispatchers.Main) {
                                val credentialToEdit = data.await()
                                if (credentialToEdit == null) {
                                    Toast.makeText(context, "Credential is null", Toast.LENGTH_SHORT).show()
                                    return@withContext
                                }
                                Intent(context, AddEditCredentialActivity::class.java).apply {
                                    putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_EDIT)
                                    putExtra(Parameters.KEY_ENTITY, credential.company)
                                    putExtra(Parameters.KEY_Credential, credentialToEdit)
                                    context.startActivity(this)
                                }
                            }
                        }
                    }

                ) {
                    SingleCredentialItem(
                        linkedEntity = credential.company,
                        credential = credential,
                        onClick = {
                            Intent(context, DetailedCredentialActivity::class.java).apply {
                                putExtra(Parameters.KEY_ENTITY, credential.company)
                                putExtra(Parameters.KEY_Credential, credential)
                                context.startActivity(this)
                            }
                        }
                    )
                }
            }
        }
    }

}
