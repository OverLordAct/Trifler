package com.meshdesh.trifler.category.data.repository

import com.meshdesh.trifler.category.data.entity.AddCategoryRequestBody
import com.meshdesh.trifler.common.data.entity.GenericErrorResponse
import com.meshdesh.trifler.common.data.entity.GenericSuccessResponse
import com.meshdesh.trifler.common.data.entity.Result

interface CategoryRepository {
    suspend fun addCategory(addCategoryRequestBody: AddCategoryRequestBody): Result<GenericSuccessResponse, GenericErrorResponse>
}