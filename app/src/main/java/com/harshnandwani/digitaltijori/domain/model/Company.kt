package com.harshnandwani.digitaltijori.domain.model

import java.io.Serializable

data class Company(
    val companyId: Int,
    val name: String,
    val isABank: Boolean,
    val issuesCards: Boolean,
    val hasCredentials: Boolean,
    val iconResId: Int = -1,
    val logoResId: Int = -1
) : Serializable
