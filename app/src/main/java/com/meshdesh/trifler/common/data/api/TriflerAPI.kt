package com.meshdesh.trifler.common.data.api

import com.meshdesh.trifler.category.data.entity.AddCategoryRequestBody
import com.meshdesh.trifler.common.data.entity.GenericErrorResponse
import com.meshdesh.trifler.common.data.entity.GenericSuccessResponse
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.sigin.data.entity.SigninRequest
import com.meshdesh.trifler.sigin.data.entity.SigninResponse
import com.meshdesh.trifler.signup.data.entity.SignupRequest
import com.meshdesh.trifler.signup.data.entity.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TriflerAPI {

    interface UnauthenticatedAPI {
        @POST("/api/user/auth/signin")
        suspend fun signin(@Body signinRequest: SigninRequest): Result<SigninResponse.Success, SigninResponse.Failure>

        @POST("/api/user/auth/signup")
        suspend fun signup(@Body signupRequest: SignupRequest): Result<SignupResponse.Success, SignupResponse.Failure>
    }

    @POST("/api/user/category/add-category")
    suspend fun addCategory(@Body categoryRequest: AddCategoryRequestBody): Result<GenericSuccessResponse, GenericErrorResponse>
}