package com.harshnandwani.digitaltijori.presentation.company

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.harshnandwani.digitaltijori.domain.model.Company

@Composable
fun CompaniesList(
    companies: List<Company>
) {
    LazyColumn {
        items(companies) {  company ->
            SingleCompany(company = company)
        }
    }
}
