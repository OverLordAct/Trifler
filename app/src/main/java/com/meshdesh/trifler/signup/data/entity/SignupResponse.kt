package com.meshdesh.trifler.signup.data.entity

import com.google.gson.annotations.SerializedName

sealed class SignupResponse {
    data class Success(
        @SerializedName("status") val status: Boolean,
        @SerializedName("data") val payload: Payload
    ) {
        data class Payload(
            @SerializedName("userId") private val userId: String,
            @SerializedName("name") val name: String,
            @SerializedName("contactNo") val phoneNo: String,
            @SerializedName("accessToken") val accessToken: String,
            @SerializedName("refreshToken") val refreshToken: String
        )
    }
}
