package com.harshnandwani.digitaltijori.presentation.card.add_edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardEvent
import com.harshnandwani.digitaltijori.presentation.common_components.TopAppBarWithBackButton
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class AddEditCardActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: AddEditCardViewModel = hiltViewModel()
            var eventSent by remember { mutableStateOf(false) }
            val mode = intent.getStringExtra(Parameters.KEY_MODE)
            if (mode == Parameters.VAL_MODE_EDIT && !eventSent) {
                val issuer = intent.getSerializableExtra(Parameters.KEY_ISSUER) as Company
                val card = intent.getSerializableExtra(Parameters.KEY_CARD) as Card
                viewModel.onEvent(CardEvent.ChangeToEditMode(issuer, card))
                eventSent = true  //fixme: setContent is being refreshed repeatedly this is a temporary work around
            }

            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = { TopAppBarWithBackButton(title = "Provide card details") }
                    ) {
                        AddEditCardScreen(viewModel)
                    }
                }
            }
        }
    }
}
