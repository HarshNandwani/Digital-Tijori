package com.harshnandwani.digitaltijori.presentation.credential.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.harshnandwani.digitaltijori.presentation.credential.list.components.SingleCredentialItem
import com.harshnandwani.digitaltijori.presentation.home.HomeViewModel

@Composable
fun CredentialsListScreen(viewModel: HomeViewModel) {

    val state = viewModel.state.value

    LazyColumn {
        for((entity, credential) in state.credentials) {
            item {
                SingleCredentialItem(
                    linkedEntity = entity,
                    credential = credential,
                    onClick = {  }
                )
            }
        }
    }

}
