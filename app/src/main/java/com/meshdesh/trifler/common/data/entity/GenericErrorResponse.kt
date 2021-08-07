package com.meshdesh.trifler.common.data.entity

import com.google.gson.annotations.SerializedName

data class GenericErrorResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)