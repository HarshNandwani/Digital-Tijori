package com.harshnandwani.digitaltijori.presentation.credential.add_edit

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.presentation.common_components.BottomSheetLayout
import com.harshnandwani.digitaltijori.presentation.common_components.InputTextField
import com.harshnandwani.digitaltijori.presentation.common_components.RoundedOutlineButton
import com.harshnandwani.digitaltijori.presentation.company.CompaniesList
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.util.CredentialEvent
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.util.CredentialSubmitResultEvent
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditCredentialScreen(viewModel: AddEditCredentialViewModel, onDone: () -> Unit) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val credential = uiState.credential.value
    val context = LocalContext.current
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if(passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility

    BottomSheetLayout(
        sheetContent = {
            CompaniesList(
                titleText = "Select entity",
                companies = uiState.allEntities,
                onSelect = {
                    viewModel.onEvent(CredentialEvent.SelectEntity(it))
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        },
        sheetState = bottomSheetState
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 36.dp, vertical = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    if (uiState.mode == Parameters.VAL_MODE_ADD && !credential.isLinkedToBank) {
                        focusManager.clearFocus()
                        coroutineScope.launch { bottomSheetState.show() }
                    }
                }
            ) {
                Image(
                    painter = painterResource(id = uiState.selectedEntity?.iconResId ?: R.drawable.ic_default_company),
                    contentDescription = "Entity Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = uiState.selectedEntity?.name ?: "Select entity")
            }
            Spacer(modifier = Modifier.size(16.dp))
            InputTextField(
                label = "Username",
                value = credential.username,
                onValueChange = {
                    viewModel.onEvent(CredentialEvent.EnteredUsername(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            InputTextField(
                label = "Password",
                value = credential.password,
                onValueChange = {
                    viewModel.onEvent(CredentialEvent.EnteredPassword(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = icon, contentDescription = "Visibility Icon")
                    }
                },
                visualTransformation = if(passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
            )

            InputTextField(
                label = "Alias (optional)",
                value = credential.alias ?: "",
                onValueChange = {
                    viewModel.onEvent(CredentialEvent.EnteredAlias(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.size(32.dp))

            RoundedOutlineButton(
                onClick = { viewModel.onEvent(CredentialEvent.CredentialSubmit) },
                text = "Save credentials"
            )

        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CredentialSubmitResultEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is CredentialSubmitResultEvent.CredentialSaved -> {
                    Toast.makeText(context, "Credentials saved!", Toast.LENGTH_SHORT).show()
                    onDone()
                }

            }
        }
    }

}
