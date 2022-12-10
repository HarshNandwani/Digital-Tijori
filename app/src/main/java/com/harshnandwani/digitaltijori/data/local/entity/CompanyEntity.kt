package com.harshnandwani.digitaltijori.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.harshnandwani.digitaltijori.domain.model.Company

@Entity(tableName = "Company")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true)
    val companyId: Int,
    val name: String,
    val isABank: Boolean,
    val issuesCards: Boolean,
    val hasCredentials: Boolean,
    val iconResId: Int = -1,
    val logoResId: Int = -1
) {
    companion object {
        fun toEntity(company: Company) = CompanyEntity(
            company.companyId,
            company.name,
            company.isABank,
            company.issuesCards,
            company.hasCredentials,
            company.iconResId,
            company.logoResId
        )
    }

    fun toDomain() =
        Company(companyId, name, isABank, issuesCards, hasCredentials, iconResId, logoResId)
}
