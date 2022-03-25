package com.harshnandwani.digitaltijori.presentation.card.detailed_view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.use_case.card.DeleteCardUseCase
import com.harshnandwani.digitaltijori.presentation.ui.theme.DigitalTijoriTheme
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class DetailedCardActivity : ComponentActivity() {

    @Inject
    lateinit var deleteCardUseCase: DeleteCardUseCase

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val issuer = intent.getSerializableExtra(Parameters.KEY_ISSUER) as Company
            val card = intent.getSerializableExtra(Parameters.KEY_CARD) as Card

            DigitalTijoriTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Box(Modifier.fillMaxSize()) {
                        DetailedCard(
                            issuer = issuer,
                            card = card,
                            onDeleteClick = {
                                lifecycleScope.launch {
                                    deleteCardUseCase(card)
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            this@DetailedCardActivity,
                                            "Card deleted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        finish()
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
