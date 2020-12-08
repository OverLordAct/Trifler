package com.meshdesh.trifler.common.account

import com.meshdesh.trifler.common.data.Credentials
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountManager @Inject constructor(
    private val localStorage: LocalStorage
) {
    val isLoggedIn: Boolean
        get() = ((localStorage.get<String>(KEY.TOKEN) != null && localStorage.get<Credentials>(KEY.USERNAME) != null))

    val userName: String?
        get() {
            return if (isLoggedIn) localStorage[KEY.USERNAME] else null
        }

    val token: String?
        get() {
            return if (isLoggedIn) localStorage[KEY.TOKEN] else null
        }

    fun login(username: String, email: String, token: String) {
        // TODO Add token
        localStorage[KEY.USERNAME] = username
        localStorage[KEY.EMAIL] = email
    }

    fun logout() {
        localStorage.delete(KEY.USERNAME)
        localStorage.delete(KEY.TOKEN)
    }
}