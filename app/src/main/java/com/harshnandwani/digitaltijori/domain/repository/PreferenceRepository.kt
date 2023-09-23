package com.harshnandwani.digitaltijori.domain.repository

interface PreferenceRepository {

    suspend fun setAuthenticatedTimestamp(time: Long)

    suspend fun getLastAuthenticatedTimestamp(): Long

    suspend fun setShowAboutApp(show: Boolean)

    suspend fun getShouldShowAboutApp(): Boolean

    suspend fun isAppOpenedFirstTime(): Boolean

    suspend fun setAppOpened()
}
