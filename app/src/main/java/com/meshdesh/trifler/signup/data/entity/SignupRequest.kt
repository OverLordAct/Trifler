package com.meshdesh.trifler.signup.data.entity

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("name") private val name: String,
    @SerializedName("contactNo") private val phoneNo: String,
    @SerializedName("password") private val password: String
)