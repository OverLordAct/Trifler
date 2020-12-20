package com.meshdesh.trifler.common.storage.account

import com.meshdesh.trifler.common.data.entity.Credentials
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountManager @Inject constructor(
    private val localStorage: LocalStorage
) {
    // TODO Manage loggedIn
    val isLoggedIn: Boolean
        get() = (localStorage.get<Credentials>(KEY.USERNAME) != null)

    val userName: String?
        get() {
            return if (isLoggedIn) localStorage[KEY.USERNAME] else null
        }

    fun login(username: String, email: String) {
        localStorage[KEY.USERNAME] = username
        localStorage[KEY.EMAIL] = email
    }

    fun logout() {
        // TODO Manage token
        localStorage.delete(KEY.USERNAME)
    }
}