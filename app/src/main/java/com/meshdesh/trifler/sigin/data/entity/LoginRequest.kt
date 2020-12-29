package com.meshdesh.trifler.sigin.data.entity

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") private val email: String,
    @SerializedName("password") private val password: String
)