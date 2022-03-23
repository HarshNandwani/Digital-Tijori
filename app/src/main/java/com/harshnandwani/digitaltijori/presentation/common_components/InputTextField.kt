package com.harshnandwani.digitaltijori.presentation.common_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun InputTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    trailingIcon: @Composable () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        Spacer(modifier = Modifier.size(12.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(text = label)
            },
            placeholder = { placeholder?.let { Text(it) } },
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { keyboardController?.hide() }
            ),
            modifier = modifier,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation
        )
    }
}
