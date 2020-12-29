package com.meshdesh.trifler.sigin.data.repository

import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.sigin.data.entity.SigninRequest
import com.meshdesh.trifler.sigin.data.entity.SigninResponse

interface SigninRepository {
    suspend fun login(signinRequest: SigninRequest): Result<SigninResponse.Success, SigninResponse.Failure>
}