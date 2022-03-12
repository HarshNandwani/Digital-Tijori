package com.harshnandwani.digitaltijori.domain.model

data class Credential(
    val id: Int,
    val username: String,
    val password: String,
    val companyId: Int
)
