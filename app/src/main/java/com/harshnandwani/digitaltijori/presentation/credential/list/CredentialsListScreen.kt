package com.harshnandwani.digitaltijori.presentation.credential.list

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.harshnandwani.digitaltijori.presentation.bank_account.list.components.Swipeable
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.AddEditCredentialActivity
import com.harshnandwani.digitaltijori.presentation.credential.list.components.SingleCredentialItem
import com.harshnandwani.digitaltijori.presentation.home.HomeViewModel
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalMaterialApi
@Composable
fun CredentialsListScreen(viewModel: HomeViewModel) {

    val state = viewModel.state.value
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        for((entity, credential) in state.credentials) {
            item {
                Swipeable(
                    swipeToLeftEnabled = true,
                    rightColor = Color.Green,
                    rightIcon = Icons.Default.ContentCopy,
                    leftSwipeAction = {
                        coroutineScope.launch {
                            val data = coroutineScope.async(Dispatchers.IO) {
                                viewModel.getCredential(credential.id)
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
                    leftColor = Color.Magenta,
                    leftIcon = Icons.Default.Edit,
                    rightSwipeAction = {
                        coroutineScope.launch {
                            val data = coroutineScope.async(Dispatchers.IO) {
                                viewModel.getCredential(credential.id)
                            }
                            withContext(Dispatchers.Main) {
                                val credentialToEdit = data.await()
                                if (credentialToEdit == null) {
                                    Toast.makeText(context, "Credential is null", Toast.LENGTH_SHORT).show()
                                    return@withContext
                                }
                                Intent(context, AddEditCredentialActivity::class.java).apply {
                                    putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_EDIT)
                                    putExtra(Parameters.KEY_ENTITY, entity)
                                    putExtra(Parameters.KEY_Credential, credentialToEdit)
                                    ContextCompat.startActivity(context, this, null)
                                }
                            }
                        }
                    }

                ) {
                    SingleCredentialItem(
                        linkedEntity = entity,
                        credential = credential,
                        onClick = {  }
                    )
                }
            }
        }
    }

}
