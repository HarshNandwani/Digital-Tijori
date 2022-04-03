package com.harshnandwani.digitaltijori.domain.repository

interface PreferenceRepository {

    suspend fun setAuthenticatedTimestamp(time: Long)

    suspend fun getLastAuthenticatedTimestamp(): Long

}
