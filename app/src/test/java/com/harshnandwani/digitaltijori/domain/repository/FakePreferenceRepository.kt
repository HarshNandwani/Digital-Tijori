package com.harshnandwani.digitaltijori.domain.repository

class FakePreferenceRepository : PreferenceRepository {

    private var authTimestamp = -1L
    private var showAboutApp = true
    private var openedFirstTime = true

    override suspend fun setAuthenticatedTimestamp(time: Long) {
        authTimestamp = time
    }

    override suspend fun getLastAuthenticatedTimestamp(): Long {
        return authTimestamp
    }

    override suspend fun setShowAboutApp(show: Boolean) {
        showAboutApp = show
    }

    override suspend fun getShouldShowAboutApp(): Boolean {
        return showAboutApp
    }

    override suspend fun isAppOpenedFirstTime(): Boolean {
        return openedFirstTime
    }

    override suspend fun setAppOpened() {
        openedFirstTime = false
    }
}
