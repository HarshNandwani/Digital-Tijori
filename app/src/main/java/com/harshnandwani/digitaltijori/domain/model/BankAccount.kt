package com.harshnandwani.digitaltijori.domain.model

data class BankAccount(
    val id: Int,
    val bankId: Int,
    val holderName: String,
    val accountNumber: String,
    val ifsc: String,
    val phoneNumber: String?,
    val alias: String?
)
