package com.harshnandwani.digitaltijori.data.util

import com.harshnandwani.digitaltijori.domain.model.Company

object DummyCompanies {

    val bank = Company(
        companyId = 1,
        name = "Test Bank",
        isABank = true,
        issuesCards = false,
        hasCredentials = false,
        iconResId = -5,
        logoResId = -10
    )

    val bankIssuesCards = Company(
        companyId = 2,
        name = "Test Bank 2",
        isABank = true,
        issuesCards = true,
        hasCredentials = false,
        iconResId = -5,
        logoResId = -10
    )

    val bankIssuesCardsHasCredentials = Company(
        companyId = 3,
        name = "Test Bank 3",
        isABank = true,
        issuesCards = true,
        hasCredentials = true,
        iconResId = -5,
        logoResId = -10
    )

    val cardIssuer = Company(
        companyId = 4,
        name = "Test card issuer",
        isABank = false,
        issuesCards = true,
        hasCredentials = false,
        iconResId = -5,
        logoResId = -10
    )

    val entity = Company(
        companyId = 5,
        name = "Test entity",
        isABank = false,
        issuesCards = false,
        hasCredentials = true,
        iconResId = -5,
        logoResId = -10
    )

}
