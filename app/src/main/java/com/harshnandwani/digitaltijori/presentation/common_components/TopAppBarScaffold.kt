package com.harshnandwani.digitaltijori.presentation.common_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TopAppBarScaffold(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { TopAppBarWithBackButton(title = title) }
    ) { paddingValues ->
        Box(
            modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()
        }
    }
}
