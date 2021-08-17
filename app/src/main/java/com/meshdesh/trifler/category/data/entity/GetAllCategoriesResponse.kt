package com.meshdesh.trifler.category.data.entity

data class GetAllCategoriesResponse(
    val status: Boolean,
    val payload: List<Payload>
) {
    data class Payload(
        val categoryId: String,
        val categoryName: String,
        val about: String,
        val addedOn: String
    )
}
