package com.meshdesh.trifler.signup.data.repository

import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.signup.data.entity.SignupRequest
import com.meshdesh.trifler.signup.data.entity.SignupResponse

interface SignupRepository {
    suspend fun signup(signupRequest: SignupRequest): Result<SignupResponse.Success, SignupResponse.Failure>
}