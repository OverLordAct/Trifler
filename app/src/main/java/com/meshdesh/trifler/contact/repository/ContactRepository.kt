package com.meshdesh.trifler.contact.repository

import com.meshdesh.trifler.category.data.entity.GetAllCategoriesResponse
import com.meshdesh.trifler.common.data.entity.GenericErrorResponse
import com.meshdesh.trifler.common.data.entity.Result

interface ContactRepository {
    suspend fun getContact(userId: String): Result<GetAllCategoriesResponse, GenericErrorResponse>

    fun getDuration(): List<String>
}