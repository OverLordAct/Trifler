package com.meshdesh.trifler.category.viewmodel

interface AddCategoryFragmentViewModel {

    fun addCategory(category: String)

    sealed class UiState {
        object Empty : UiState()
        object Duplicate : UiState()
        data class Success(
            val category: String
        ) : UiState()

        object Error : UiState()
        object Loading : UiState()
    }
}