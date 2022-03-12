package com.harshnandwani.digitaltijori.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Company::class,
        parentColumns = ["id"],
        childColumns = ["bankId"],
        onDelete = ForeignKey.RESTRICT
    )]
)
data class BankAccount(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bankId: Int,
    val holderName: String,
    val accountNumber: String,
    val ifsc: String,
    val phoneNumber: String?,
    val alias: String?
)
