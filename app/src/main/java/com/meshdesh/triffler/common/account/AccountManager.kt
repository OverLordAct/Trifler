package com.meshdesh.triffler.common.account

import com.meshdesh.triffler.common.data.Credentials
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@InstallIn(ApplicationComponent::class)
class AccountManager @Inject constructor(
    private val localStorage: LocalStorage
) {
    val isLoggedIn: Boolean
        get() = localStorage.get(KEY.TOKEN) ?: false

    val userName: String?
        get() {
            return if (isLoggedIn) localStorage.get<Credentials>(KEY.CREDENTIALS)?.username else null
        }

    fun login() {}

    fun logout() {}
}