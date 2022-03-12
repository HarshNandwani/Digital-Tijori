package com.harshnandwani.digitaltijori.domain.model

data class Company(
    val id: Int,
    val name: String,
    val isABank: Boolean,
    val issuesCards: Boolean,
    val hasCredentials: Boolean
)
