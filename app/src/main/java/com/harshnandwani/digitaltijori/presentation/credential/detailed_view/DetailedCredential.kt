package com.harshnandwani.digitaltijori.presentation.credential.detailed_view

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.presentation.common_components.ConfirmationAlertDialog
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.AddEditCredentialActivity
import com.harshnandwani.digitaltijori.presentation.util.Parameters

@Composable
fun DetailedCredential(
    entity: Company,
    credential: Credential,
    onDeleteAction: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    var passwordVisibility by remember { mutableStateOf(true) }
    val icon = if (passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        elevation = 16.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(24.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = entity.iconResId),
                    contentDescription = "Entity Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = entity.name,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.weight(weight = 1f)
                )
                Spacer(modifier = Modifier.size(32.dp))
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Icon",
                    modifier = Modifier.clickable {
                        Intent(context, AddEditCredentialActivity::class.java).apply {
                            putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_EDIT)
                            putExtra(Parameters.KEY_ENTITY, entity)
                            putExtra(Parameters.KEY_Credential, credential)
                            ContextCompat.startActivity(context, this, null)
                        }
                        if (context is DetailedCredentialActivity)
                            context.finish()
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Icon",
                     modifier = Modifier.clickable {
                        showDialog = true
                    }
                )
            }
            Spacer(modifier = Modifier.size(24.dp))
            Row {
                Column {
                    Text(text = "Username", style = MaterialTheme.typography.h2)
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(text = "Password", style = MaterialTheme.typography.h2)
                    Spacer(modifier = Modifier.size(12.dp))
                }
                Spacer(modifier = Modifier.size(32.dp))
                Column {
                    Text(text = credential.username)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (passwordVisibility) "*".repeat(credential.password.length) else credential.password,
                            modifier = Modifier.weight(weight = 1f)
                        )
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "Password visibility icon"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                }
            }
        }

        ConfirmationAlertDialog(
            visible = showDialog,
            onDismiss = { showDialog = false },
            title = "You are deleting ${entity.name} credentials of ${credential.username}",
            text = "This action cannot be undone, do you want to proceed?",
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteAction()
                        showDialog = false
                    }
                ) {
                    Text(text = "Yes, delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )

    }

}
