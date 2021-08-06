package com.meshdesh.trifler.login.data.entity

import com.google.gson.annotations.SerializedName

sealed class LoginResponse {
    data class Success(
        @SerializedName("status") val status: String,
        @SerializedName("message") val message: String,
        @SerializedName("user") val user: User
    ) : LoginResponse() {
        data class User(
            @SerializedName("userId") val userId: String,
            @SerializedName("name") val name: String,
            @SerializedName("contactNo") val phone: String,
            @SerializedName("accessToken") val accessToken: String,
            @SerializedName("refreshToken") val refreshToken: String
        )
    }

    data class Failure(
        val status: String,
        val message: String
    ) : LoginResponse()
}