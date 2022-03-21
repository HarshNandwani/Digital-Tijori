package com.harshnandwani.digitaltijori.presentation.credential.detailed_view

import android.content.Intent
import android.widget.Toast
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
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.AddEditCredentialActivity
import com.harshnandwani.digitaltijori.presentation.credential.detailed_view.util.DetailedCredentialEvent
import com.harshnandwani.digitaltijori.presentation.util.Parameters

@ExperimentalMaterialApi
@Composable
fun DetailedCredentialScreen(viewModel: DetailedCredentialViewModel) {

    val state = viewModel.state.value
    val context = LocalContext.current

    var passwordVisibility by remember { mutableStateOf(true) }
    val icon = if (passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = 16.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(24.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = state.entity.iconResId),
                    contentDescription = "Entity Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = state.entity.name,
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
                            putExtra(Parameters.KEY_ENTITY, state.entity)
                            putExtra(Parameters.KEY_Credential, state.credential)
                            ContextCompat.startActivity(context, this, null)
                        }
                        (context as DetailedCredentialActivity).finish()
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Icon",
                    tint = Color.Red,
                    modifier = Modifier.clickable {
                        viewModel.onEvent(DetailedCredentialEvent.DeleteCredential)
                        Toast.makeText(context, "Credential deleted!", Toast.LENGTH_SHORT).show()
                        (context as DetailedCredentialActivity).finish()
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
                    Text(text = state.credential.username)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (passwordVisibility) "*".repeat(state.credential.password.length) else state.credential.password,
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
    }

}
