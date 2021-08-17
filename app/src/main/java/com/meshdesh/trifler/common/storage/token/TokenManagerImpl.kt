package com.meshdesh.trifler.common.storage.token

import com.meshdesh.trifler.common.storage.KEY
import com.meshdesh.trifler.common.storage.LocalStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManagerImpl @Inject constructor(
    private val storage: LocalStorage
) : TokenManager {
    override fun getAccessToken(): String? {
        return storage[KEY.ACCESS_TOKEN]
    }

    override fun getRefreshToken(): String? {
        return storage[KEY.REFRESH_TOKEN]
    }

    override fun setAccessToken(accessToken: String) {
        storage[KEY.ACCESS_TOKEN] = accessToken
    }

    override fun setRefreshToken(refreshToken: String) {
        storage[KEY.REFRESH_TOKEN] = refreshToken
    }

    override fun deleteAccessToken() {
        storage.delete(KEY.ACCESS_TOKEN)
    }

    override fun deleteRefreshToken() {
        storage.delete(KEY.REFRESH_TOKEN)
    }
}