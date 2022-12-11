package com.harshnandwani.digitaltijori.presentation.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.domain.util.CardType
import com.harshnandwani.digitaltijori.domain.util.ColorScheme

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

    val emptyCard = Card(
        0,
        false,
        null,
        emptyCompany,
        "",
        14,
        1,
        "",
        "",
        null,
        CardNetwork.Unknown,
        "",
        ColorScheme.DEFAULT,
        null,
        CardType.None
    )

    val emptyCredential = Credential(
        username = "",
        password = "",
        isLinkedToBank = false,
        bankAccount = null,
        company = emptyCompany,
        alias = null
    )
}
