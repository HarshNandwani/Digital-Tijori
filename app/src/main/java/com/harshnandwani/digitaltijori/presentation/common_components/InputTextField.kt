package com.harshnandwani.digitaltijori.presentation.common_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current

    Column {
        Spacer(modifier = Modifier.size(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(text = label)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            ),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { /*TODO: Hide keyboard*/ }
            ),
            modifier = modifier
        )
    }
}
