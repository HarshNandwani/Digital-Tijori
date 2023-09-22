package com.harshnandwani.digitaltijori.domain.model

data class AllData(
    val bankAccounts: List<BankAccount>,
    val cards: List<Card>,
    val credential: List<Credential>
)
