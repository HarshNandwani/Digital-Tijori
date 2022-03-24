package com.harshnandwani.digitaltijori.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Company(
    @PrimaryKey(autoGenerate = true)
    val companyId: Int,
    val name: String,
    val isABank: Boolean,
    val issuesCards: Boolean,
    val hasCredentials: Boolean,
    val iconResId: Int = -1,
    val logoResId: Int = -1
) : Serializable
