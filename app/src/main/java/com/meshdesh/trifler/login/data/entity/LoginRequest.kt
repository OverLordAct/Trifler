package com.meshdesh.trifler.login.data.entity

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("contactNo") private val phone: String,
    @SerializedName("password") private val password: String
)