package com.meshdesh.trifler.common.storage.account

import com.meshdesh.trifler.common.storage.KEY
import com.meshdesh.trifler.common.storage.LocalStorage
import com.meshdesh.trifler.common.storage.token.TokenManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountManager @Inject constructor(
    private val localStorage: LocalStorage,
    private val tokenManager: TokenManager
) {
    val isLoggedIn: Boolean
        get() {
            return localStorage.get<String>(KEY.USERNAME) != null
        }

    val userId: String?
        get() {
            return if (isLoggedIn) {
                localStorage[KEY.USERNAME]
            } else {
                null
            }
        }

    fun login(username: String, email: String, accessToken: String, refreshToken: String) {
        localStorage[KEY.USERNAME] = username
        localStorage[KEY.PHONE] = email
        tokenManager.setAccessToken(accessToken)
        tokenManager.setRefreshToken(refreshToken)
    }

    fun logout() {
        // TODO Manage token
        localStorage.delete(KEY.USERNAME)
    }
}