package com.meshdesh.trifler.splash.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meshdesh.trifler.common.feature.FeatureManager
import com.meshdesh.trifler.common.storage.account.AccountManager

class SplashActivityViewModelImpl @ViewModelInject constructor(
    private val accountManager: AccountManager,
    private val featureManager: FeatureManager
) : ViewModel(), SplashActivityViewModel {
    override val accountStatus: MutableLiveData<SplashActivityViewModel.AccountStatus> =
        MutableLiveData()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        if (accountManager.isLoggedIn) {
            accountStatus.value = SplashActivityViewModel.AccountStatus.Authenticated(
                accountManager.userName ?: ""
            )
        } else {
            if (featureManager.isFirstTime) {
                featureManager.setFirstTimeDone()
                accountStatus.value = SplashActivityViewModel.AccountStatus.Unauthenticated(true)
            } else {
                accountStatus.value = SplashActivityViewModel.AccountStatus.Unauthenticated(false)
            }
        }
    }
}