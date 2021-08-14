package com.meshdesh.trifler.contact.viewModel

import com.meshdesh.trifler.category.data.entity.GetAllCategoriesResponse

interface AddContactDetailsViewModel {
    fun addContact()

    sealed class UiState {
        object DownloadError : UiState()
        data class FormDownloaded(
            val categoryList: List<GetAllCategoriesResponse.Payload>,
            val durationList: List<String>
        ) : UiState()

        object Loading : UiState()
    }
}