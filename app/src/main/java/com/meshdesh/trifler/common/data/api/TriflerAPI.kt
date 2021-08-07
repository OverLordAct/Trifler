package com.meshdesh.trifler.common.data.api

import com.meshdesh.trifler.category.data.entity.AddCategoryRequestBody
import com.meshdesh.trifler.common.data.entity.GenericErrorResponse
import com.meshdesh.trifler.common.data.entity.GenericSuccessResponse
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.login.data.entity.LoginRequest
import com.meshdesh.trifler.login.data.entity.LoginResponse
import com.meshdesh.trifler.signup.data.entity.SignupRequest
import com.meshdesh.trifler.signup.data.entity.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TriflerAPI {

    interface UnauthenticatedAPI {
        @POST("auth/signin")
        suspend fun login(@Body loginRequest: LoginRequest): Result<LoginResponse.Success, GenericErrorResponse>

        @POST("auth/signup")
        suspend fun signup(@Body signupRequest: SignupRequest): Result<SignupResponse.Success, GenericErrorResponse>
    }

    @POST("/api/user/category/add-category")
    suspend fun addCategory(@Body categoryRequest: AddCategoryRequestBody): Result<GenericSuccessResponse, GenericErrorResponse>
}