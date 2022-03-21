package com.harshnandwani.digitaltijori.presentation.credential.detailed_view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.presentation.credential.detailed_view.util.DetailedCredentialEvent
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedCredentialActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: DetailedCredentialViewModel = hiltViewModel()
            val entity = intent.getSerializableExtra(Parameters.KEY_ENTITY) as Company
            val credential = intent.getSerializableExtra(Parameters.KEY_Credential) as Credential
            viewModel.onEvent(DetailedCredentialEvent.LoadEntity(entity))
            viewModel.onEvent(DetailedCredentialEvent.LoadCredential(credential))

            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {
                    DetailedCredentialScreen(viewModel)
                }
            }
        }
    }
}
