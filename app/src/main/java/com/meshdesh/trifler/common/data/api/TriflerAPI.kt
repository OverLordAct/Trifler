package com.meshdesh.trifler.common.data.api

import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.common.data.tag.NoAuthenticationTag
import com.meshdesh.trifler.sigin.data.entity.SigninRequest
import com.meshdesh.trifler.sigin.data.entity.SigninResponse
import com.meshdesh.trifler.signup.data.entity.SignupRequest
import com.meshdesh.trifler.signup.data.entity.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TriflerAPI {
    @NoAuthenticationTag
    @POST("/api/user/auth/signin")
    suspend fun signin(@Body signinRequest: SigninRequest): Result<SigninResponse.Success, SigninResponse.Failure>

    @NoAuthenticationTag
    @POST("/api/user/auth/signup")
    suspend fun signup(@Body signupRequest: SignupRequest): Result<SignupResponse.Success, SignupResponse.Failure>
}