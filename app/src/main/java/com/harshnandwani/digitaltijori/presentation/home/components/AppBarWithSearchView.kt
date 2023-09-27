package com.harshnandwani.digitaltijori.presentation.home.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun AppBarWithSearchView(
    titleText: String,
    actions: @Composable RowScope.() -> Unit = {},
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchIconClick: () -> Unit = {},
    onSearchImeClicked: (String) -> Unit,
    onCloseClicked: () -> Unit = {}
) {
    var isSearchOpen by remember { mutableStateOf(false) }
    if(isSearchOpen){
        SearchAppBar(
            text = searchText,
            onTextChange = onSearchTextChange,
            onCloseClicked = {
                isSearchOpen = false
                onCloseClicked()
            },
            onSearchImeClicked = {
                isSearchOpen = false
                onSearchImeClicked(it)
            }
        )
    }else{
        AppBar(
            title = titleText,
            actions = actions,
            onSearchIconClicked = {
                isSearchOpen = true
                onSearchIconClick()
            }
        )
    }
}


@Composable
fun AppBar(
    title: String,
    actions: @Composable RowScope.() -> Unit,
    onSearchIconClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = { onSearchIconClicked() }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            }
            actions()
        }
    )
}


@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchImeClicked: (String) -> Unit
) {

    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation
    ) {
        TextField(
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = {
                Text(
                    text = "Search here...",
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = { onSearchImeClicked(text) },
                    modifier = Modifier.alpha(ContentAlpha.medium)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchImeClicked(text)
                }
            )
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}
