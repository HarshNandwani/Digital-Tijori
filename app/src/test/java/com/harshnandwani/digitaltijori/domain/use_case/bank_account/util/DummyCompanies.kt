package com.harshnandwani.digitaltijori.domain.use_case.bank_account.util

import com.harshnandwani.digitaltijori.domain.model.Company

object DummyCompanies {

    val invalidCompany = Company(
        companyId = 0,
        name = "Invalid company",
        isABank = false,
        issuesCards = false,
        hasCredentials = false,
        iconResId = -1,
        logoResId = -555
    )

    val bank = Company(
        companyId = 1,
        name = "Test Bank",
        isABank = true,
        issuesCards = false,
        hasCredentials = false,
        iconResId = -5,
        logoResId = -10
    )
}
