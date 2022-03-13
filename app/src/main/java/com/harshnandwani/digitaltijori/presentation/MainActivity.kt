package com.harshnandwani.digitaltijori.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.harshnandwani.digitaltijori.presentation.home.HomeActivity
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
        /*
        * This is temporary
        * TODO: Remove this when authentication is implemented
        * */
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
