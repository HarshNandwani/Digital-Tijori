package com.harshnandwani.digitaltijori.presentation.company

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.domain.model.Company

@Composable
fun SingleCompany(
    company: Company,
    onSelect: (Company) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                onSelect(company)
            }
            .padding(vertical = 12.dp, horizontal = 32.dp)
            .fillMaxWidth()
    ) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            Image(
                painter = painterResource(id = company.iconResId),
                contentDescription = "",
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = company.name)
    }
}
