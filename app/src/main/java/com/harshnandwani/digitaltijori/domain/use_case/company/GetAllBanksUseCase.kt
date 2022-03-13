package com.harshnandwani.digitaltijori.domain.use_case.company

import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow

class GetAllBanksUseCase(
    private val repository: CompanyRepository
) {
    operator fun invoke(): Flow<List<Company>> {
        return repository.getAll()
    }
}
