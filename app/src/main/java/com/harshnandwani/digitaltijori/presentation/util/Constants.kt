package com.harshnandwani.digitaltijori.presentation.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company

object Constants {
    val emptyCompany = Company(
        0,
        "",
        isABank = true,
        issuesCards = true,
        hasCredentials = true
    )

    val emptyBankAccount = BankAccount(
        0,
        emptyCompany,
        "",
        "",
        "",
        "",
        ""
    )
}
