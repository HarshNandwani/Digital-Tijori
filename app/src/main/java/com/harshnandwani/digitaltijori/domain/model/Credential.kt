package com.harshnandwani.digitaltijori.domain.model

import java.io.Serializable

data class Credential(
    val credentialId: Int = 0,
    val username: String,
    val password: String,
    val isLinkedToBank: Boolean,
    val bankAccount: BankAccount?,
    val company: Company,
    val alias: String?
) : Serializable
