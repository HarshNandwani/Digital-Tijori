package com.harshnandwani.digitaltijori.presentation.credential.detailed_view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.use_case.credential.DeleteCredentialUseCase
import com.harshnandwani.digitaltijori.presentation.common_components.TopAppBarScaffold
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import com.harshnandwani.digitaltijori.presentation.util.serializable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class DetailedCredentialActivity : ComponentActivity() {

    @Inject
    lateinit var deleteCredentialUseCase: DeleteCredentialUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val entity = intent.serializable<Company>(Parameters.KEY_ENTITY)
            val credential = intent.serializable<Credential>(Parameters.KEY_Credential)

            DigitalTijoriTheme {
                TopAppBarScaffold(title = "Credential details") {
                    DetailedCredential(
                        entity = entity,
                        credential = credential,
                        onDeleteAction = {
                            lifecycleScope.launch {
                                deleteCredentialUseCase(credential)
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(this@DetailedCredentialActivity, "Credential deleted!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                        },
                        modifier = Modifier.padding(16.dp),
                        onDone = { finish() }
                    )
                }
            }

        }
    }
}
