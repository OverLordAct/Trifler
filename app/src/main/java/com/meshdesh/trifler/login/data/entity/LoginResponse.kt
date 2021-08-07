package com.meshdesh.trifler.login.data.entity

import com.google.gson.annotations.SerializedName

sealed class LoginResponse {
    data class Success(
        @SerializedName("status") val status: Boolean,
        @SerializedName("payload") val payload: Payload,
    ) : LoginResponse() {
        data class Payload(
            @SerializedName("userId") val userId: String,
            @SerializedName("name") val name: String,
            @SerializedName("contactNo") val phone: String,
            @SerializedName("accessToken") val accessToken: String,
            @SerializedName("refreshToken") val refreshToken: String
        )
    }
}