package com.meshdesh.trifler.contact.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactActivityViewModelImpl @Inject constructor() : ViewModel(), ContactActivityViewModel {

    override val nextStepStatus = MutableLiveData<ContactActivityViewModel.UiState>()
    override val backStepStatus = MutableLiveData<ContactActivityViewModel.UiState>()

    private val _currentStepStatus = MutableLiveData<ContactActivityViewModel.UiState>()

    init {
        // nextStepStatus.postValue(ContactActivityViewModel.CurrentStep.Step1)
        _currentStepStatus.value = ContactActivityViewModel.UiState.Step1
    }

    override fun onNext(uiState: ContactActivityViewModel.UiState) {
        // TODO
        // when (currentStep) {
        //     is ContactActivityViewModel.CurrentStep.Step1 -> {
        //         nextStepStatus.postValue(ContactActivityViewModel.CurrentStep.Step2)
        //     }
        //     is ContactActivityViewModel.CurrentStep.Step2 -> {
        //         nextStepStatus.postValue(ContactActivityViewModel.CurrentStep.Step3)
        //     }
        //     is ContactActivityViewModel.CurrentStep.Step3 -> {
        //
        //     }
        // }
    }

    override fun onBack(uiState: ContactActivityViewModel.UiState) {
        // when (currentStep) {
        //     is ContactActivityViewModel.CurrentStep.Step1 -> {
        //         backStepStatus.postValue(ContactActivityViewModel.CurrentStep.Step1)
        //     }
        //     is ContactActivityViewModel.CurrentStep.Step2 -> {
        //         backStepStatus.postValue(ContactActivityViewModel.CurrentStep.Step2)
        //     }
        //     is ContactActivityViewModel.CurrentStep.Step3 -> {
        //         backStepStatus.postValue(ContactActivityViewModel.CurrentStep.Step3)
        //     }
        // }
    }

    fun updateCurrentState(state: ContactActivityViewModel.UiState) {
        _currentStepStatus.value = state
    }
}