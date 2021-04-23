package com.meshdesh.trifler.sigin.data.entity

import com.google.gson.annotations.SerializedName

sealed class SigninResponse {
    data class Success(
        @SerializedName("status") val status: String,
        @SerializedName("message") val message: String,
        @SerializedName("user") val user: User
    ) : SigninResponse() {
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
    ) : SigninResponse()
}