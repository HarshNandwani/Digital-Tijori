package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.dao.CredentialDao
import com.harshnandwani.digitaltijori.data.local.entity.CredentialEntity
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import com.harshnandwani.digitaltijori.domain.repository.CompanyRepository
import com.harshnandwani.digitaltijori.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.*

class CredentialRepositoryImpl(
    private val dao: CredentialDao,
    private val companyRepository: CompanyRepository,
    private val accountRepository: BankAccountRepository
) : CredentialRepository {

    override suspend fun add(credential: Credential) {
        dao.add(CredentialEntity.toEntity(credential))
    }

    override suspend fun get(id: Int): Credential? {
        val credentialEntity = dao.get(id) ?: return null
        return mapEntityToDomain(credentialEntity)
    }

    override fun getAll(): Flow<List<Credential>> {
        return dao.getAllCredentialsWithEntityDetails().transform {
            val result = it.flatMap { entry ->
                entry.value.map { credentialEntity -> mapEntityToDomain(credentialEntity) }
            }
            emit(result.filterNotNull())
        }
    }

    override fun getCredentialsLinkedToAccount(bankAccountId: Int): Flow<List<Credential>> {
        return dao.getCredentialsLinkedToAccount(bankAccountId).map {
            it.mapNotNull { credentialEntity -> mapEntityToDomain(credentialEntity) }
        }
    }

    override suspend fun update(credential: Credential) {
        dao.update(CredentialEntity.toEntity(credential))
    }

    override suspend fun delete(credential: Credential) {
        dao.delete(CredentialEntity.toEntity(credential))
    }

    override suspend fun dataExists(): Boolean {
        return dao.dataExists()
    }

    private suspend fun mapEntityToDomain(credentialEntity: CredentialEntity): Credential? {
        credentialEntity.bankAccountId?.let {
            val linkedAccount = accountRepository.get(it) ?: return@let
            return credentialEntity.toDomain(linkedAccount, linkedAccount.linkedCompany)
        }
        credentialEntity.companyId?.let {
            val linkedCompany = companyRepository.get(it) ?: return null
            return credentialEntity.toDomain(null, linkedCompany)
        } ?: return null
    }
}
