package com.harshnandwani.digitaltijori.presentation.common_components

import android.app.Activity
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun TopAppBarWithBackButton(title: String) {
    val activity = LocalContext.current as Activity
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { activity.onBackPressed() }) {
                Icon(Icons.Default.ArrowBack,"Back")
            }
        }
    )
}
