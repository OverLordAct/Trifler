package com.meshdesh.trifler.sigin.data.repository

import com.meshdesh.trifler.common.data.api.TriflerAPI
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.sigin.data.entity.LoginRequest
import com.meshdesh.trifler.sigin.data.entity.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val client: TriflerAPI
) {
    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse.Success, LoginResponse.Failure> {
        return client.login(loginRequest)
    }
}