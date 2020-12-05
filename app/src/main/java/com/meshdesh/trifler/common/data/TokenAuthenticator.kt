package com.meshdesh.trifler.common.data

import com.meshdesh.trifler.common.account.AccountManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val accountManager: AccountManager
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // TODO Add Token Refresh service
        val updatedToken = "ABC"

        return updatedToken?.let {
            response.request().newBuilder()
                .header("Authorization", it)
                .build()
        }
    }
}