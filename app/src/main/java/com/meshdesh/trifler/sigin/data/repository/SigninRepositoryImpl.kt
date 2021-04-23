package com.meshdesh.trifler.sigin.data.repository

import com.meshdesh.trifler.common.data.api.TriflerAPI
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.sigin.data.entity.SigninRequest
import com.meshdesh.trifler.sigin.data.entity.SigninResponse
import javax.inject.Inject

class SigninRepositoryImpl @Inject constructor(
    private val client: TriflerAPI.UnauthenticatedAPI
) : SigninRepository {
    override suspend fun login(signinRequest: SigninRequest): Result<SigninResponse.Success, SigninResponse.Failure> {
        return client.signin(signinRequest)
    }
}