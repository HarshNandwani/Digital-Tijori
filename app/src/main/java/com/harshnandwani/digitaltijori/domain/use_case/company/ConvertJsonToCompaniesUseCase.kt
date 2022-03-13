package com.harshnandwani.digitaltijori.domain.use_case.company

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.harshnandwani.digitaltijori.domain.model.Company

class ConvertJsonToCompaniesUseCase {

    operator fun invoke(companiesJson: String): List<Company> {
        val typeToken = object : TypeToken<List<Company>>() {}.type
        return Gson().fromJson(companiesJson, typeToken)
    }

}
