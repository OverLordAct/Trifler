package com.meshdesh.trifler.common.auth.repository

interface TokenRepository {
    fun provideAccessToken(): String?

    fun provideRefreshToken(): String?

    fun refreshToken(): String?
}