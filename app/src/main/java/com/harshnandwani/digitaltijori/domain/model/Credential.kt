package com.harshnandwani.digitaltijori.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = BankAccount::class,
            parentColumns = ["id"],
            childColumns = ["bankAccountId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = Company::class,
            parentColumns = ["id"],
            childColumns = ["companyId"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Credential(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
    val isLinkedToBank : Boolean,
    val bankAccountId : Int?,
    val companyId: Int?
)
