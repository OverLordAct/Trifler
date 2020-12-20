package com.meshdesh.trifler.common.auth.repository

import com.meshdesh.trifler.common.data.api.TokenAPI
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.common.storage.token.TokenManager
import dagger.Lazy
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.runBlocking
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

    // TODO Resolve scope
    override fun refreshToken(): String? {
        return runBlocking(IO) {
            when (val newToken =
                tokenManager.getRefreshToken()?.let { api.get().refreshToken(it) }) {
                is Result.Success -> {
                    val accessToken = newToken.data?.accessToken
                    val refreshToken = newToken.data?.refreshToken

                    if (accessToken != null) {
                        tokenManager.setAccessToken(accessToken)
                    }

                    if (refreshToken != null) {
                        tokenManager.setRefreshToken(refreshToken)
                    }
                    return@runBlocking accessToken
                }
                // TODO Handle error correctly
                is Result.NetworkError -> {
                    return@runBlocking null
                }
                is Result.ServerError -> {
                    return@runBlocking null
                }
                is Result.UnknownError -> {
                    return@runBlocking null
                }
                else -> return@runBlocking null
            }
        }
    }
}