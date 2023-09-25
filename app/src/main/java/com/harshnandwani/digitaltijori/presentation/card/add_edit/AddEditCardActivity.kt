package com.harshnandwani.digitaltijori.presentation.card.add_edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardEvent
import com.harshnandwani.digitaltijori.presentation.common_components.TopAppBarScaffold
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import com.harshnandwani.digitaltijori.presentation.util.serializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditCardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: AddEditCardViewModel = hiltViewModel()
            var eventSent by remember { mutableStateOf(false) }
            if (!eventSent) {
                val mode = intent.getStringExtra(Parameters.KEY_MODE)
                if (mode == Parameters.VAL_MODE_ADD) {
                    if (intent.getBooleanExtra(Parameters.KEY_IS_LINKED_TO_ACCOUNT, false)) {
                        val issuer = intent.serializable<Company>(Parameters.KEY_ISSUER)
                        val bankAccount = intent.serializable<BankAccount>(Parameters.KEY_BANK_ACCOUNT)
                        viewModel.onEvent(CardEvent.SelectIssuer(issuer))
                        viewModel.onEvent(CardEvent.LinkToAccount(bankAccount))
                    }
                } else {
                    val issuer = intent.serializable<Company>(Parameters.KEY_ISSUER)
                    val card = intent.serializable<Card>(Parameters.KEY_CARD)
                    viewModel.onEvent(CardEvent.ChangeToEditMode(issuer, card))
                }
                eventSent = true  //fixme: setContent is being refreshed repeatedly this is a temporary work around
            }

            DigitalTijoriTheme {
                TopAppBarScaffold(title = "Provide card details") {
                    AddEditCardScreen(viewModel)
                }
            }
        }
    }
}
