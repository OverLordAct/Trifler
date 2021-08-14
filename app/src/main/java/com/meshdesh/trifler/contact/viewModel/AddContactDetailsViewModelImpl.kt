package com.meshdesh.trifler.contact.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meshdesh.trifler.category.data.repository.CategoryRepository
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.common.storage.account.AccountManager
import com.meshdesh.trifler.contact.repository.ContactRepository
import kotlinx.coroutines.launch

class AddContactDetailsViewModelImpl constructor(
    private val accountManager: AccountManager,
    private val categoryRepository: CategoryRepository,
    private val contactRepository: ContactRepository
) : ViewModel(), AddContactDetailsViewModel {

    val _uiStateLiveData = MutableLiveData<AddContactDetailsViewModel.UiState>()

    init {
        _uiStateLiveData.value = AddContactDetailsViewModel.UiState.Loading
        downloadFormData()
    }

    private fun downloadFormData() {
        viewModelScope.launch {
            val durationList = contactRepository.getDuration()
            val userId = accountManager.userId

            val response = categoryRepository.getAllCategories(
                userId ?: throw Exception("User must be logged in")
            )
            when (response) {
                is Result.Success -> {
                    val result = response.data
                    if (result != null) {
                        val categories = result.payload
                        _uiStateLiveData.value = AddContactDetailsViewModel.UiState.FormDownloaded(
                            categories,
                            durationList
                        )
                    }
                }
                else -> {
                    _uiStateLiveData.value = AddContactDetailsViewModel.UiState.DownloadError
                }
            }
        }
    }

    override fun addContact() {
        TODO("Not yet implemented")
    }
}