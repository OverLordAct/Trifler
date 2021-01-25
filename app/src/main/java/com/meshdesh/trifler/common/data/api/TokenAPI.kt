package com.meshdesh.trifler.common.data.api

import com.meshdesh.trifler.common.auth.entity.TokenResponse
import retrofit2.Call
import retrofit2.http.POST

interface TokenAPI {
    @POST("/api/user/auth/refresh-token")
    fun refreshToken(refreshToken: String): Call<TokenResponse>
}