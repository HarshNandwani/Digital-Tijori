package com.harshnandwani.digitaltijori.presentation.common_components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Swipe
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.presentation.ui.theme.DarkGreen
import com.harshnandwani.digitaltijori.presentation.ui.theme.LightGreen
import kotlinx.coroutines.launch
import java.lang.Math.random

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Swipeable(
    swipeToLeftEnabled: Boolean,
    rightIcon: ImageVector? = null,
    leftSwipeAction: () -> Unit = {},
    swipeToRightEnabled: Boolean,
    leftIcon: ImageVector? = null,
    rightSwipeAction: () -> Unit = {},
    content: @Composable () -> Unit
) {

    val leftColor = MaterialTheme.colors.secondary
    val rightColor = if (isSystemInDarkTheme()) DarkGreen else LightGreen

    val coroutineScope = rememberCoroutineScope()
    val dismissState = DismissState(
        initialValue = DismissValue.Default,
        confirmStateChange = { dismissValue ->
            when (dismissValue) {
                DismissValue.DismissedToStart -> {
                    if (swipeToLeftEnabled) {
                        leftSwipeAction()
                    }
                }
                DismissValue.Default -> {

                }
                DismissValue.DismissedToEnd -> {
                    if (swipeToRightEnabled) {
                        rightSwipeAction()
                    }
                }
            }
            false
        }
    )

    //random() coz we need to run it everytime to remove elevation due to dismissState
    LaunchedEffect(key1 = random()) {
        coroutineScope.launch {
            dismissState.reset()
        }
    }

    SwipeToDismiss(
        state = dismissState,
        dismissThresholds = { FractionalThreshold(0.3f) },
        background = {
            val dismissDirection = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    DismissValue.DismissedToStart -> rightColor
                    DismissValue.DismissedToEnd -> leftColor
                    DismissValue.Default -> Color.DarkGray
                }, label = "SwipeAnimation"
            )
            val icon = when (dismissDirection) {
                DismissDirection.StartToEnd -> leftIcon
                DismissDirection.EndToStart -> rightIcon
            }
            val scale = animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f,
                label = "SwipeIconEnlargeAnimation"
            )
            val alignment = when (dismissDirection) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(start = 12.dp, end = 12.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    icon?: Icons.Default.Swipe,
                    contentDescription = "Icon",
                    modifier = Modifier.scale(scale.value)
                )
            }
        },
        dismissContent = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = animateDpAsState(
                    targetValue = if (dismissState.dismissDirection != null) 6.dp else 0.dp,
                    label = "SwipeCardElevationAnimation"
                ).value
            ) {
                content()
            }
        }
    )
}
