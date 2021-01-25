package com.meshdesh.trifler.common.auth.repository

import com.meshdesh.trifler.common.data.api.TokenAPI
import com.meshdesh.trifler.common.storage.token.TokenManager
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val api: Lazy<TokenAPI>,
    private val tokenManager: TokenManager
) : TokenRepository {
    override fun provideAccessToken(): String? {
        return tokenManager.getAccessToken()
    }

    override fun provideRefreshToken(): String? {
        return tokenManager.getRefreshToken()
    }

    override fun refreshToken(): String? {
        val call = tokenManager.getRefreshToken()?.let { api.get().refreshToken(it) }
        return try {
            val result = call?.execute()?.body()

            result?.accessToken?.let {
                tokenManager.setAccessToken(it)
            }
            result?.refreshToken?.let {
                tokenManager.setRefreshToken(it)
            }

            result?.accessToken
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}