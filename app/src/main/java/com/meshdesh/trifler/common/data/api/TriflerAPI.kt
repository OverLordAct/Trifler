package com.meshdesh.trifler.common.data.api

import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.common.data.tag.AuthenticatedTag
import com.meshdesh.trifler.login.data.entity.LoginRequest
import com.meshdesh.trifler.login.data.entity.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TriflerAPI {
    @AuthenticatedTag
    @POST("/api/user/auth/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Result<LoginResponse.Success, LoginResponse.Failure>
}