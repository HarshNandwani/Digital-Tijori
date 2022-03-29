package com.harshnandwani.digitaltijori.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.harshnandwani.digitaltijori.presentation.home.HomeActivity
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.DigitalTijoriDataStore
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.size(56.dp))
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "",
                            Modifier.fillMaxSize(0.2f),
                            tint = MaterialTheme.colors.secondaryVariant
                        )
                    }
                }
            }
        }

        val dataStore = DigitalTijoriDataStore(this)

        val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    finish()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    lifecycleScope.launch {
                        dataStore.setAuthenticatedTimestamp(System.currentTimeMillis())
                    }
                    showAppContent()
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

        lifecycleScope.launch {
            val lastAuthenticated = dataStore.getLastAuthenticatedTimestamp()
            val timeNow = System.currentTimeMillis()
            when {
                // For the first ever app launch don't ask for authentication
                // and set timeStamp such that from next launch regular logic applies
                lastAuthenticated == -1L -> {
                    dataStore.setAuthenticatedTimestamp(timeNow - Parameters.AUTH_GRACE)
                    showAppContent()
                }
                timeNow - lastAuthenticated > Parameters.AUTH_GRACE -> {
                    biometricPrompt.authenticate(promptInfo)
                }
                else -> {
                    showAppContent()
                }
            }
        }

    }

    private fun showAppContent() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}
