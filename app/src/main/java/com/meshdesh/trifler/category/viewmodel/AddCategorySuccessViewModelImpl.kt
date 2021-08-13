package com.meshdesh.trifler.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.meshdesh.trifler.category.view.AddCategorySuccessFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCategorySuccessViewModelImpl @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), AddCategorySuccessViewModel {

    private val args: AddCategorySuccessFragment.Args =
        savedStateHandle[AddCategorySuccessFragment.EXTRA_CATEGORY_NAME]
            ?: throw Exception("Arg must not be null")

    private val _statusLiveData = MutableLiveData<AddCategorySuccessViewModel.Status>()
    val statusLiveData: LiveData<AddCategorySuccessViewModel.Status>
        get() = _statusLiveData

    init {
        _statusLiveData.value = AddCategorySuccessViewModel.Status.Success(args.category)
    }
}