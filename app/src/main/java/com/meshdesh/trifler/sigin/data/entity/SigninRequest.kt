package com.meshdesh.trifler.sigin.data.entity

import com.google.gson.annotations.SerializedName

data class SigninRequest(
    @SerializedName("contactNo") private val phone: String,
    @SerializedName("password") private val password: String
)