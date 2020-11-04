package com.meshdesh.trifler.common.account

import com.meshdesh.trifler.common.data.Credentials
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountManager @Inject constructor(
    private val localStorage: LocalStorage
) {
    val isLoggedIn: Boolean
        get() = ((localStorage.get<String>(KEY.TOKEN) != null && localStorage.get<Credentials>(KEY.CREDENTIALS) != null))

    val userName: String?
        get() {
            return if (isLoggedIn) localStorage.get<Credentials>(KEY.CREDENTIALS)?.username else null
        }

    fun login(username: String, password: String) {
        localStorage.set(
            KEY.CREDENTIALS, Credentials(
                username, password
            )
        )
    }

    fun logout() {
        localStorage.delete(KEY.CREDENTIALS)
        localStorage.delete(KEY.TOKEN)
    }
}