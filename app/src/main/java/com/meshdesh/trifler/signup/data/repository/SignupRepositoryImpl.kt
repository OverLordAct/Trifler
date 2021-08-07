package com.meshdesh.trifler.signup.data.repository

import com.meshdesh.trifler.common.data.api.TriflerAPI
import com.meshdesh.trifler.common.data.entity.GenericErrorResponse
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.signup.data.entity.SignupRequest
import com.meshdesh.trifler.signup.data.entity.SignupResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignupRepositoryImpl @Inject constructor(
    private val triflerAPI: TriflerAPI.UnauthenticatedAPI
) : SignupRepository {
    override suspend fun signup(signupRequest: SignupRequest): Result<SignupResponse.Success, GenericErrorResponse> {
        return triflerAPI.signup(signupRequest)
    }
}