package com.harshnandwani.digitaltijori.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
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
import com.harshnandwani.digitaltijori.domain.use_case.auth.SetAuthenticatedUseCase
import com.harshnandwani.digitaltijori.domain.use_case.auth.ShouldAuthenticateUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.app.KeyguardManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private lateinit var biometricPrompt: BiometricPrompt

    @Inject
    lateinit var shouldAuthenticate: ShouldAuthenticateUseCase

    @Inject
    lateinit var setAuthenticated: SetAuthenticatedUseCase

    private var shouldShowDialog = mutableStateOf(false)
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

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

                        if(shouldShowDialog.value) {
                            AlertDialog(
                                onDismissRequest = {  },
                                title = { Text(text = "No screen lock set") },
                                text = { Text(text = "To continue using Digital Tijori, goto your phone settings and set a screen lock which can be PIN/Pattern/Password/Fingerprint") },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            shouldShowDialog.value = false
                                            finish()
                                        }
                                    ) {
                                        Text(text = "Close app")
                                    }
                                }
                            )
                        }

                    }
                }
            }
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    showAppContent()
                } else {
                    Toast.makeText(this@MainActivity, "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }


    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            if (shouldAuthenticate()) {
                showAuthentication()
            } else {
                showAppContent()
            }
        }
    }

    private fun showAuthentication() {
        biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    when (errorCode) {
                        BiometricPrompt.ERROR_NO_BIOMETRICS, BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL -> {
                            shouldShowDialog.value = true
                        }
                        BiometricPrompt.ERROR_HW_NOT_PRESENT -> {
                            // Despite adding setDeviceCredentialAllowed(true) some devices (like xiaomi 9a) are returning BIOMETRIC_ERROR_HW_NOT_PRESENT
                            val km = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
                            if (km.isDeviceSecure) {
                                val deviceLockIntent = km.createConfirmDeviceCredentialIntent(title,"Authenticate to open Digital Tijori")
                                resultLauncher.launch(deviceLockIntent)
                            }
                        }
                        else -> finish()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    lifecycleScope.launch {
                        setAuthenticated()
                    }
                    showAppContent()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    finish()
                }
            }
        )

        val promptInfoBuilder = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock Digital Tijori")
            .setSubtitle("Authenticate yourself")
        if (Build.VERSION.SDK_INT >= 30) {
            promptInfoBuilder.setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        } else {
            promptInfoBuilder.setDeviceCredentialAllowed(true)
        }
        biometricPrompt.authenticate(promptInfoBuilder.build())

    }

    private fun showAppContent() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}
