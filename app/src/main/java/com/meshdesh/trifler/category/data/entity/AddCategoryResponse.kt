package com.meshdesh.trifler.category.data.entity

data class AddCategoryResponse(
    val status: Boolean,
    val payload: Payload
) {
    data class Payload(
        val categoryId: String,
        val about: String,
        val categoryName: String,
        val addedOn: String,
        val userId: String
    )
}
