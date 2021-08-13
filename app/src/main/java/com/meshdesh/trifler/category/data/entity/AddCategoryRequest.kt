package com.meshdesh.trifler.category.data.entity

data class AddCategoryRequest(
    val userId: String,
    val categoryName: String,
    val about: String = ""
)