package com.meshdesh.trifler.common.auth

import com.meshdesh.trifler.common.auth.repository.TokenRepository
import com.meshdesh.trifler.common.data.tag.NoAuthenticationTag
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Invocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository
) : Authenticator {

    companion object {
        private const val AUTHENTICATION = "Authorization"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = tokenRepository.provideAccessToken()

        synchronized(this) {
            val newToken = tokenRepository.provideAccessToken()

            if (isAuthenticatedRequest(response)) {
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
        return null
    }

    private fun isAuthenticatedRequest(response: Response): Boolean {
        response.request.tag(Invocation::class.java)?.let {
            it.method().getAnnotation(NoAuthenticationTag::class.java)?.let {
                return false
            }
        }
        return true
    }
}
