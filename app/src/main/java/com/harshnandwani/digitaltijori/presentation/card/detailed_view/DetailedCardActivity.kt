package com.harshnandwani.digitaltijori.presentation.card.detailed_view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.use_case.card.DeleteCardUseCase
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
class DetailedCardActivity : ComponentActivity() {

    @Inject
    lateinit var deleteCardUseCase: DeleteCardUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val issuer = intent.serializable<Company>(Parameters.KEY_ISSUER)
            val card = intent.serializable<Card>(Parameters.KEY_CARD)

            DigitalTijoriTheme {
                TopAppBarScaffold(title = "Card details") {
                    DetailedCard(
                        titleText = "${issuer.name} ${card.cardType.name} Details",
                        issuer = issuer,
                        card = card,
                        onDeleteAction = {
                            lifecycleScope.launch {
                                deleteCardUseCase(card)
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(this@DetailedCardActivity, "Card deleted", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                        },
                        modifier = Modifier.padding(24.dp),
                        onDone = { finish() }
                    )
                }
            }

        }
    }
}
