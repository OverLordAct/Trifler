package com.meshdesh.trifler.category.viewmodel

interface AddCategoryFragmentViewModel {

    fun addCategory(category: String)

    sealed class CategoryStatus {
        object Empty : CategoryStatus()
        object Duplicate : CategoryStatus()
        data class Success(
            val category: String
        ) : CategoryStatus()

        object Error : CategoryStatus()
        object Loading : CategoryStatus()
    }
}