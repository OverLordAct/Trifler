package com.meshdesh.trifler.common.storage.token

interface TokenManager {
    fun getAccessToken(): String?

    fun getRefreshToken(): String?

    fun setAccessToken(accessToken: String)

    fun setRefreshToken(refreshToken: String)

    fun deleteAccessToken()

    fun deleteRefreshToken()
}