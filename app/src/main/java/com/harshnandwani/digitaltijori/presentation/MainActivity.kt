package com.harshnandwani.digitaltijori.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.harshnandwani.digitaltijori.presentation.home.HomeActivity
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }

        val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    finish()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    finish()
                }
            }
        )

        val promptInfo: BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock Digital Tijori")
            .setSubtitle("Authenticate using your biometric credential")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)

    }
}
