package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util.BankAccountEvent
import com.harshnandwani.digitaltijori.presentation.common_components.TopAppBarScaffold
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import com.harshnandwani.digitaltijori.presentation.util.serializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditBankAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: AddEditBankAccountViewModel = hiltViewModel()

            val mode = intent.getStringExtra(Parameters.KEY_MODE)
            if (mode == Parameters.VAL_MODE_EDIT) {
                val bank = intent.serializable<Company>(Parameters.KEY_BANK)
                val account = intent.serializable<BankAccount>(Parameters.KEY_BANK_ACCOUNT)
                viewModel.onEvent(BankAccountEvent.ChangeToEditMode(bank, account))
            }

            DigitalTijoriTheme {
                TopAppBarScaffold(
                    title = "Provide account details",
                    onBack = { onBackPressedDispatcher.onBackPressed() }
                ) {
                    AddEditBankAccountScreen(viewModel, onDone = { finish() })
                }
            }
        }
    }
}
