package com.harshnandwani.digitaltijori.presentation.startup

import android.app.KeyguardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.harshnandwani.digitaltijori.presentation.home.HomeActivity
import com.harshnandwani.digitaltijori.presentation.startup.util.StartupEvent
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartupActivity : FragmentActivity() {

    private val viewModel: StartupViewModel by viewModels()

    private lateinit var backupFilePickLauncher: ActivityResultLauncher<Intent>

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var deviceAuthLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalTijoriTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    StartupScreen(viewModel, pickBackupFile, promptForAuth, nextAction)
                }
            }
        }

        backupFilePickLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val uri = result.data?.data
                    uri?.let {
                        viewModel.onEvent(StartupEvent.BackupFileSelected(it))
                    } ?: Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Backup file not selected", Toast.LENGTH_SHORT).show()
                }
            }

        deviceAuthLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    viewModel.onEvent(StartupEvent.AuthenticatedSuccessfully)
                } else {
                    Toast.makeText(
                        this@StartupActivity,
                        "Authentication failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private val pickBackupFile = fun() {
        Intent().apply {
            action = Intent.ACTION_OPEN_DOCUMENT
            type = "text/plain"
            backupFilePickLauncher.launch(this)
        }
    }

    private val nextAction = fun() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private val promptForAuth = fun() {
        biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    when (errorCode) {
                        BiometricPrompt.ERROR_NO_BIOMETRICS, BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL -> {
                            viewModel.onEvent(StartupEvent.AuthenticationUnavailable)
                        }

                        BiometricPrompt.ERROR_HW_NOT_PRESENT -> {
                            // Despite adding setDeviceCredentialAllowed(true) some devices (like xiaomi 9a) are returning BIOMETRIC_ERROR_HW_NOT_PRESENT
                            val km = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
                            if (km.isDeviceSecure) {
                                @Suppress("DEPRECATION")
                                val deviceLockIntent = km.createConfirmDeviceCredentialIntent(
                                    title,
                                    "Authenticate to open Digital Tijori"
                                )
                                deviceAuthLauncher.launch(deviceLockIntent)
                            }
                        }

                        else -> finish()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.onEvent(StartupEvent.AuthenticatedSuccessfully)
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
            promptInfoBuilder.setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
        } else {
            @Suppress("DEPRECATION") // used only for older android versions
            promptInfoBuilder.setDeviceCredentialAllowed(true)
        }
        biometricPrompt.authenticate(promptInfoBuilder.build())

    }

}
