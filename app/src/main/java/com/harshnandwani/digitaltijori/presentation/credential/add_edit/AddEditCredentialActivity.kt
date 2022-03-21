package com.harshnandwani.digitaltijori.presentation.credential.add_edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.util.CredentialEvent
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class AddEditCredentialActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: AddEditCredentialViewModel = hiltViewModel()

            val mode = intent.getStringExtra(Parameters.KEY_MODE)
            if (mode == Parameters.VAL_MODE_EDIT) {
                val entity = intent.getSerializableExtra(Parameters.KEY_ENTITY) as Company
                val credential = intent.getSerializableExtra(Parameters.KEY_Credential) as Credential
                viewModel.onEvent(CredentialEvent.ChangeToEditMode(entity, credential))
            }

            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AddEditCredentialScreen(viewModel)
                }
            }
        }
    }
}
