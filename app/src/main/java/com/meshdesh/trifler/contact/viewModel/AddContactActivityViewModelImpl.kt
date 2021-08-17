package com.meshdesh.trifler.contact.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddContactActivityViewModelImpl @Inject constructor() : ViewModel(),
    AddContactActivityViewModel {


    override val currentStepStatus = MutableLiveData<AddContactActivityViewModel.UiState>()

    init {
        currentStepStatus.value = AddContactActivityViewModel.UiState.Step1
    }

    fun updateCurrentState(state: AddContactActivityViewModel.UiState) {
        currentStepStatus.value = state
    }
}