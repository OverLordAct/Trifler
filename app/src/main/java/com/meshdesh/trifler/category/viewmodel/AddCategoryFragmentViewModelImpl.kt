package com.meshdesh.trifler.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meshdesh.trifler.category.data.entity.AddCategoryRequest
import com.meshdesh.trifler.category.data.repository.CategoryRepository
import com.meshdesh.trifler.common.data.entity.Result
import com.meshdesh.trifler.common.storage.account.AccountManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryFragmentViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val accountManager: AccountManager
) : ViewModel(), AddCategoryFragmentViewModel {

    private val _categoryStatus = MutableLiveData<AddCategoryFragmentViewModel.CategoryStatus>()
    val categoryStatus: LiveData<AddCategoryFragmentViewModel.CategoryStatus>
        get() = _categoryStatus

    override fun addCategory(category: String) {
        // TODO Check if duplicate exists
        _categoryStatus.value = AddCategoryFragmentViewModel.CategoryStatus.Loading

        if (category.isEmpty()) {
            _categoryStatus.value = AddCategoryFragmentViewModel.CategoryStatus.Empty
            return
        } else {
            // TODO: 23-04-2021 Ask what is About field
            val requestBody =
                AddCategoryRequest(
                    accountManager.userId ?: throw Exception("Username must not be null"),
                    category,
                    ""
                )

            viewModelScope.launch {
                when (categoryRepository.addCategory(requestBody)) {
                    is Result.Success -> {
                        // TODO: 13-08-2021 Need to store added category in local db
                        _categoryStatus.value =
                            AddCategoryFragmentViewModel.CategoryStatus.Success(category)
                    }
                    is Result.UnknownError, is Result.NetworkError, is Result.ServerError -> {
                        _categoryStatus.value = AddCategoryFragmentViewModel.CategoryStatus.Error
                    }
                }
            }
        }
    }
}