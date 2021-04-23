package com.meshdesh.trifler.category.viewmodel

interface AddCategorySuccessViewModel {
    sealed class Status {
        data class Success(val category: String) : Status()
        object Failure : Status()
    }
}