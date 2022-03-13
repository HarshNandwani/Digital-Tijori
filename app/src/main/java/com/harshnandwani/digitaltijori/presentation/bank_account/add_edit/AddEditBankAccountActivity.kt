package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme

class AddEditBankAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}
