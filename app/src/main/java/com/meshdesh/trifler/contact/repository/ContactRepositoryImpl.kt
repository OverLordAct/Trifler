package com.meshdesh.trifler.contact.repository

import com.meshdesh.trifler.category.data.entity.GetAllCategoriesResponse
import com.meshdesh.trifler.common.data.api.TriflerAPI
import com.meshdesh.trifler.common.data.entity.GenericErrorResponse
import com.meshdesh.trifler.common.data.entity.Result
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val api: TriflerAPI
) : ContactRepository {
    override suspend fun getContact(userId: String): Result<GetAllCategoriesResponse, GenericErrorResponse> {
        return api.getCategories(userId)
    }

    override fun getDuration(): List<String> = listOf("1 Week", "1 Month", "2 Month", "3 Month")
}