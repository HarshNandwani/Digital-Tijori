package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountEvent
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedBankAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: DetailedBankAccountViewModel = hiltViewModel()
            val bank = intent.getSerializableExtra(Parameters.KEY_BANK) as Company
            val account = intent.getSerializableExtra(Parameters.KEY_BANK_ACCOUNT) as BankAccount

            viewModel.onEvent(DetailedBankAccountEvent.LoadBank(bank))
            viewModel.onEvent(DetailedBankAccountEvent.LoadAccount(account))

            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {
                    DetailedBankAccountScreen(viewModel)
                }
            }
        }
    }
}
