package com.meshdesh.trifler.category.data.repository

import com.meshdesh.trifler.category.data.entity.AddCategoryRequestBody
import com.meshdesh.trifler.common.data.api.TriflerAPI
import com.meshdesh.trifler.common.data.entity.GenericErrorResponse
import com.meshdesh.trifler.common.data.entity.GenericSuccessResponse
import com.meshdesh.trifler.common.data.entity.Result
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: TriflerAPI
) : CategoryRepository {
    override suspend fun addCategory(addCategoryRequestBody: AddCategoryRequestBody): Result<GenericSuccessResponse, GenericErrorResponse> {
        return api.addCategory(addCategoryRequestBody)
    }
}