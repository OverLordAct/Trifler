package com.meshdesh.trifler.common.auth

import com.meshdesh.trifler.common.auth.repository.TokenRepository
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository
) : Authenticator {

    companion object {
        const val AUTHENTICATION = "accesstoken"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = tokenRepository.provideAccessToken()

        synchronized(this) {
            val newToken = tokenRepository.provideAccessToken()

            if (newToken != token)
                return newToken?.let { it1 ->
                    response.request
                        .newBuilder()
                        .header(AUTHENTICATION, it1)
                        .build()
                }

            val updatedToken = tokenRepository.refreshToken() ?: return null

            return response.request
                .newBuilder()
                .header(AUTHENTICATION, updatedToken)
                .build()
        }
    }
}
