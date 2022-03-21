package com.harshnandwani.digitaltijori.presentation.credential.detailed_view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedCredentialActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: DetailedCredentialViewModel = hiltViewModel()

            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {
                    DetailedCredentialScreen(viewModel)
                }
            }
        }
    }
}
