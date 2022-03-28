package com.harshnandwani.digitaltijori.presentation.credential.detailed_view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.presentation.common_components.TopAppBarWithBackButton
import com.harshnandwani.digitaltijori.presentation.credential.detailed_view.util.DetailedCredentialEvent
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedCredentialActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
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
                    Scaffold(
                        topBar = { TopAppBarWithBackButton(title = "Account details") }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            DetailedCredential(
                                entity = viewModel.state.value.entity,
                                credential = viewModel.state.value.credential,
                                onDeleteAction = {
                                    viewModel.onEvent(DetailedCredentialEvent.DeleteCredential)
                                    Toast.makeText(this@DetailedCredentialActivity, "Credential deleted!", Toast.LENGTH_SHORT).show()
                                    finish()
                                },
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
