package com.meshdesh.trifler.common.data.api

import com.meshdesh.trifler.common.auth.entity.TokenResponse
import com.meshdesh.trifler.common.data.entity.GenericErrorResponse
import com.meshdesh.trifler.common.data.entity.Result
import retrofit2.http.POST

interface TokenAPI {
    @POST("/api/user/auth/refresh-token")
    suspend fun refreshToken(refreshToken: String): Result<TokenResponse, GenericErrorResponse>
}