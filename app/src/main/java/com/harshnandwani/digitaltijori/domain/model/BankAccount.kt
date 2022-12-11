package com.harshnandwani.digitaltijori.domain.model

import java.io.Serializable

data class BankAccount(
    val bankAccountId: Int = 0,
    val linkedCompany: Company,
    val holderName: String,
    val accountNumber: String,
    val ifsc: String,
    val phoneNumber: String?,
    val alias: String?
) : Serializable
