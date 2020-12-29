package com.meshdesh.trifler.common.auth.entity

data class TokenResponse(
    val refreshToken: String,
    val accessToken: String
)