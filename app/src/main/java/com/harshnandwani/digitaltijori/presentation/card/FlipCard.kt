package com.harshnandwani.digitaltijori.presentation.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun FlipCard(
    backVisible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {

    Card(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (backVisible) {
                back()
            } else {
                front()
            }
        }
    }
}
