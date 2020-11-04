package com.meshdesh.trifler.splash.viewmodel

import androidx.lifecycle.MutableLiveData

interface SplashActivityViewModel {
    val accountStatus: MutableLiveData<AccountStatus>

    sealed class AccountStatus {
        data class Authenticated(val username: String) : AccountStatus()

        data class Unauthenticated(val isNew: Boolean) : AccountStatus()
    }
}