package com.harshnandwani.digitaltijori.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalTijoriTheme {
                HomeScreen(hiltViewModel())
            }
        }
    }
}
