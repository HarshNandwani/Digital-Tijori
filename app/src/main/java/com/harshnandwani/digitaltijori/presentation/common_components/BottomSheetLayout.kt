package com.harshnandwani.digitaltijori.presentation.common_components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    sheetContent: @Composable ColumnScope.() -> Unit,
    sheetState: ModalBottomSheetState,
    layoutContent: @Composable () -> Unit
) {

    val surfacePrimary: Color =
        if (isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.surface

    val customScrimColor: Color =
        if (isSystemInDarkTheme()) MaterialTheme.colors.primary.copy(alpha = 0.6f) else ModalBottomSheetDefaults.scrimColor

    ModalBottomSheetLayout(
        sheetContent = sheetContent,
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        sheetBackgroundColor = surfacePrimary,
        scrimColor = customScrimColor
    ) {
        layoutContent()
    }
}
