package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountEvent
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountEventResult
import com.harshnandwani.digitaltijori.presentation.common_components.TopAppBarScaffold
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import com.harshnandwani.digitaltijori.presentation.util.serializable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailedBankAccountActivity : ComponentActivity() {

    private val viewModel: DetailedBankAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val bank = intent.serializable<Company>(Parameters.KEY_BANK)
            val account = intent.serializable<BankAccount>(Parameters.KEY_BANK_ACCOUNT)
            viewModel.onEvent(DetailedBankAccountEvent.LoadBank(bank))
            viewModel.onEvent(DetailedBankAccountEvent.LoadAccount(account))

            DigitalTijoriTheme {
                TopAppBarScaffold(
                    title = "Account details",
                    onBack = { onBackPressedDispatcher.onBackPressed() }
                ) {
                    DetailedBankAccountScreen(viewModel)
                }

                LaunchedEffect(key1 = true) {
                    lifecycleScope.launch {
                        viewModel.eventFlow.collectLatest { eventResult ->
                            var message = ""
                            when (eventResult) {
                                is DetailedBankAccountEventResult.ShowError -> {
                                    message = eventResult.message
                                }
                                is DetailedBankAccountEventResult.BankAccountDeleted -> {
                                    Toast.makeText(this@DetailedBankAccountActivity, "Account deleted", Toast.LENGTH_SHORT).show()
                                    finish()
                                    return@collectLatest
                                }
                                is DetailedBankAccountEventResult.CardDeleted -> {
                                    message = "Card deleted"
                                }
                                is DetailedBankAccountEventResult.CredentialDeleted -> {
                                    message = "Credential deleted"
                                }
                            }
                            Toast.makeText(this@DetailedBankAccountActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.onEvent(DetailedBankAccountEvent.RefreshBankAccount)
    }

}
