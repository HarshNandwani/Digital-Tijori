package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.DigitalTijoriDataStore
import com.harshnandwani.digitaltijori.domain.repository.AuthRepository

class AuthRepositoryImpl(private val dataStore: DigitalTijoriDataStore) : AuthRepository {

    override suspend fun setAuthenticatedTimestamp(time: Long) {
        dataStore.setAuthenticatedTimestamp(time)
    }

    override suspend fun getLastAuthenticatedTimestamp(): Long {
        return dataStore.getLastAuthenticatedTimestamp()
    }

}
