package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountEvent
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountEventResult
import com.harshnandwani.digitaltijori.presentation.common_components.TopAppBarWithBackButton
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailedBankAccountActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
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
                    Scaffold(
                        topBar = { TopAppBarWithBackButton(title = "Account details") }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            DetailedBankAccountScreen(viewModel)
                        }
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
    }
}
