package com.meshdesh.trifler.category.data.repository

import com.meshdesh.trifler.category.data.entity.AddCategoryRequest
import com.meshdesh.trifler.category.data.entity.AddCategoryResponse
import com.meshdesh.trifler.category.data.entity.GetAllCategoriesResponse
import com.meshdesh.trifler.common.data.entity.GenericErrorResponse
import com.meshdesh.trifler.common.data.entity.Result

interface CategoryRepository {
    suspend fun addCategory(addCategoryRequest: AddCategoryRequest): Result<AddCategoryResponse, GenericErrorResponse>

    suspend fun getAllCategories(userId: String): Result<GetAllCategoriesResponse, GenericErrorResponse>
}