package com.meshdesh.trifler.common.feature

import com.meshdesh.trifler.common.account.KEY
import com.meshdesh.trifler.common.account.LocalStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureManager @Inject constructor(
    private val localStorage: LocalStorage
) {

    val isNew: Boolean
        get() = localStorage.get(KEY.ONBOARDING, false) ?: true

    fun setIsNew() {
        localStorage.set(KEY.ONBOARDING, false)
    }
}