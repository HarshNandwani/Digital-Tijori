package com.harshnandwani.digitaltijori.presentation.card.add_edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditCardActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: AddEditCardViewModel = hiltViewModel()

            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AddEditCardScreen(viewModel)
                }
            }
        }
    }
}
