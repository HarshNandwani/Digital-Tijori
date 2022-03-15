package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util.BankAccountEvent
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditBankAccountActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class) //TODO: As its Experimental keep looking into changes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: AddEditBankAccountViewModel = hiltViewModel()

            val mode = intent.getStringExtra(Parameters.KEY_MODE)
            if (mode == Parameters.VAL_MODE_EDIT) {
                val bank = intent.getSerializableExtra(Parameters.KEY_BANK) as Company
                val account =
                    intent.getSerializableExtra(Parameters.KEY_BANK_ACCOUNT) as BankAccount
                viewModel.onEvent(BankAccountEvent.ChangeToEditMode(bank, account))

            }

            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AddEditBankAccountScreen(viewModel)
                }
            }
        }
    }
}
