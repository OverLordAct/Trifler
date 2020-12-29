package com.meshdesh.trifler.sigin.data.entity

import com.google.gson.annotations.SerializedName

sealed class LoginResponse {
    data class Success(
        @SerializedName("status") val status: String,
        @SerializedName("message") val message: String,
        @SerializedName("data") val user: User
    ) {
        data class User(
            @SerializedName("password") private val password: String,
            @SerializedName("email") val email: String,
            @SerializedName("name") val name: String,
            @SerializedName("refreshToken") val refreshToken: String,
            @SerializedName("accessToken") val accessToken: String
        )
    }

    data class Failure(
        val status: String,
        val message: String
    )
}