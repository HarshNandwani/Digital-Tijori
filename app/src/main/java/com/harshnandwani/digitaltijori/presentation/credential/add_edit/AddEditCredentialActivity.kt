package com.harshnandwani.digitaltijori.presentation.credential.add_edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.presentation.common_components.TopAppBarScaffold
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.util.CredentialEvent
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import com.harshnandwani.digitaltijori.presentation.util.serializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditCredentialActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: AddEditCredentialViewModel = hiltViewModel()

            val mode = intent.getStringExtra(Parameters.KEY_MODE)
            if (mode == Parameters.VAL_MODE_ADD) {
                if (intent.getBooleanExtra(Parameters.KEY_IS_LINKED_TO_ACCOUNT, false)) {
                    val entity = intent.serializable<Company>(Parameters.KEY_ENTITY)
                    val linkedAccount = intent.serializable<BankAccount>(Parameters.KEY_BANK_ACCOUNT)
                    viewModel.onEvent(CredentialEvent.SelectEntity(entity))
                    viewModel.onEvent(CredentialEvent.LinkToAccount(linkedAccount))
                }
            } else {
                val entity = intent.serializable<Company>(Parameters.KEY_ENTITY)
                val credential = intent.serializable<Credential>(Parameters.KEY_Credential)
                viewModel.onEvent(CredentialEvent.ChangeToEditMode(entity, credential))
            }

            DigitalTijoriTheme {
                TopAppBarScaffold(
                    title = "Provide credentials",
                    onBack = { onBackPressedDispatcher.onBackPressed() }
                ) {
                    AddEditCredentialScreen(viewModel, onDone = { finish() })
                }
            }
        }
    }
}
