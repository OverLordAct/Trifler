package com.meshdesh.trifler.contact.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meshdesh.trifler.category.data.repository.CategoryRepository
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.common.storage.account.AccountManager
import com.meshdesh.trifler.contact.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactDetailsViewModelImpl @Inject constructor(
    private val accountManager: AccountManager,
    private val categoryRepository: CategoryRepository,
    private val contactRepository: ContactRepository
) : ViewModel(), AddContactDetailsViewModel {

    val uiStateLiveData = MutableLiveData<AddContactDetailsViewModel.UiState>()

    init {
        uiStateLiveData.value = AddContactDetailsViewModel.UiState.Loading
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
                        uiStateLiveData.value = AddContactDetailsViewModel.UiState.FormDownloaded(
                            categories,
                            durationList
                        )
                    }
                }
                else -> {
                    uiStateLiveData.value = AddContactDetailsViewModel.UiState.DownloadError
                }
            }
        }
    }

    override fun addContact() {
        uiStateLiveData.value = AddContactDetailsViewModel.UiState.Success
    }
}