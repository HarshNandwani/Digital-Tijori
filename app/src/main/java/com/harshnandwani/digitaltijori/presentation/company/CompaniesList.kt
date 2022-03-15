package com.harshnandwani.digitaltijori.presentation.company

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.domain.model.Company

@Composable
fun CompaniesList(
    titleText: String,
    companies: List<Company>,
    onSelect: (Company) -> Unit
) {
    Column {
        Text(
            text = titleText,
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.defaultMinSize(1.dp)  //Added this coz there's a bug in ModalBottomSheet
        ) {
            items(companies) { company ->
                SingleCompany(
                    company = company,
                    onSelect = onSelect
                )
            }
        }
    }
}
