package com.meshdesh.trifler.login.data.repository

import com.meshdesh.trifler.common.data.api.TriflerAPI
import com.meshdesh.trifler.common.data.entity.GenericErrorResponse
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.login.data.entity.LoginRequest
import com.meshdesh.trifler.login.data.entity.LoginResponse
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val client: TriflerAPI.UnauthenticatedAPI
) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<LoginResponse.Success, GenericErrorResponse> {
        return client.login(loginRequest)
    }
}