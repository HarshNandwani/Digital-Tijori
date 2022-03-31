package com.harshnandwani.digitaltijori.domain.repository

interface AuthRepository {

    suspend fun setAuthenticatedTimestamp(time: Long)

    suspend fun getLastAuthenticatedTimestamp(): Long

}
