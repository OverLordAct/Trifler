package com.meshdesh.trifler.category.data.entity

data class AddCategoryRequestBody(
    val userId: String,
    val categoryName: String,
    val about: String
)