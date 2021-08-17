package com.meshdesh.trifler.common.feature

import com.meshdesh.trifler.common.storage.KEY
import com.meshdesh.trifler.common.storage.LocalStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureManager @Inject constructor(
    private val localStorage: LocalStorage
) {

    val isFirstTime: Boolean
        get() = localStorage[KEY.ONBOARDING] ?: true

    fun setFirstTimeDone() {
        localStorage[KEY.ONBOARDING] = false
    }
}