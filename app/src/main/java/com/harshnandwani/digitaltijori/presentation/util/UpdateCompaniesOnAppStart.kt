package com.harshnandwani.digitaltijori.presentation.util

import android.content.res.TypedArray
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.repository.CompanyRepository
import com.harshnandwani.digitaltijori.domain.use_case.company.ConvertJsonToCompaniesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/*
* As app doesn't connect to internet, we need to pre-populate the database
* with initial set of companies.
*
* This adds companies to db which are not already present
* and updates icon and logo res ids for all companies in the db
*
* */

class UpdateCompaniesOnAppStart(
    private val companiesJson: String,
    private val companyIconResIds: TypedArray,
    private val companyLogoResIds: TypedArray,
    private val companyRepository: CompanyRepository
) {

    fun execute() {
        val dbInitializeScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        dbInitializeScope.launch {

            val convertJsonToCompanies = ConvertJsonToCompaniesUseCase()
            val companiesFromJson = convertJsonToCompanies(companiesJson)
            val companiesInDb = companyRepository.getAll()

            companiesFromJson.forEachIndexed { index, companyFromJson ->
                val companyWithResIds = companyFromJson.copy(
                    iconResId = companyIconResIds.getResourceId(index, -1),
                    logoResId = companyLogoResIds.getResourceId(index, -1)
                )

                if (index < companiesInDb.size) {

                    // This will be true 99.99% times
                    if (companyWithResIds.name == companiesInDb[index].name) {
                        val finalCompany = companyWithResIds.copy(
                            companyId = companiesInDb[index].companyId
                        )
                        companyRepository.update(finalCompany)
                        return@forEachIndexed
                    }

                    // Just in case the companies we not added sequentially and their ids are not sequential.
                    val companyInDb = companiesInDb.find { it.name == companyWithResIds.name }
                    companyInDb?.let {
                        val finalCompany = companyWithResIds.copy(companyId = it.companyId)
                        companyRepository.update(finalCompany)
                    } ?: addNewCompany(companyWithResIds)
                } else {
                    addNewCompany(companyWithResIds)
                }

            }
        }
    }

    private suspend fun addNewCompany(company: Company) {
        companyRepository.add(company)
    }
}
