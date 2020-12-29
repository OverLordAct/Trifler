package com.meshdesh.trifler.common.auth

import com.meshdesh.trifler.common.auth.repository.TokenRepository
import com.meshdesh.trifler.common.data.tag.AuthenticatedTag
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Invocation
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Authenticator that attempts to refresh the client's access token.
 * In the event that a refresh fails and a new token can't be issued an error
 * is delivered to the caller. This authenticator blocks all requests while a token
 * refresh is being performed. In-flight requests that fail with a 401 are
 * automatically retried.
 */
@Singleton
class AccessTokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // We need to have a token in order to refresh it.
        val token = tokenRepository.provideAccessToken()

        synchronized(this) {
            val newToken = tokenRepository.provideAccessToken()

            // TODO Set Header Constants
            // Check if the request made was previously made as an authenticated request.
            if (!checkAuthenticatedTag(response)) {
                if (newToken != token)
                    return newToken?.let { it1 ->
                        response.request
                            .newBuilder()
                            .header("Authorization", it1)
                            .build()
                    }

                val updatedToken = tokenRepository.refreshToken() ?: return null

                return response.request
                    .newBuilder()
                    .header("Authorization", updatedToken)
                    .build()
            }
        }
        return null
    }

    private fun checkAuthenticatedTag(response: Response): Boolean {
        response.request.tag(Invocation::class.java)?.let {
            it.method().getAnnotation(AuthenticatedTag::class.java)?.let {
                return true
            }
        }
        return false
    }
}
