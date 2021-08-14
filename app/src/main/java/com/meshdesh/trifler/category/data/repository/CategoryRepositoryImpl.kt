package com.meshdesh.trifler.category.data.repository

import com.meshdesh.trifler.category.data.entity.AddCategoryRequest
import com.meshdesh.trifler.category.data.entity.AddCategoryResponse
import com.meshdesh.trifler.category.data.entity.GetAllCategoriesResponse
import com.meshdesh.trifler.common.data.api.TriflerAPI
import com.meshdesh.trifler.common.data.entity.GenericErrorResponse
import com.meshdesh.trifler.common.data.entity.Result
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: TriflerAPI
) : CategoryRepository {
    override suspend fun addCategory(addCategoryRequest: AddCategoryRequest): Result<AddCategoryResponse, GenericErrorResponse> {
        return api.addCategory(addCategoryRequest)
    }

    override suspend fun getAllCategories(userId: String): Result<GetAllCategoriesResponse, GenericErrorResponse> {
        return api.getCategories(userId)
    }
}