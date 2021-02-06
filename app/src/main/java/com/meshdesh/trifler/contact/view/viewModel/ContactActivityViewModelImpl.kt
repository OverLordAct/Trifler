package com.meshdesh.trifler.contact.view.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactActivityViewModelImpl @Inject constructor() : ViewModel(), ContactActivityViewModel {

    override val nextStepStatus = MutableLiveData<ContactActivityViewModel.CurrentStep>()
    override val backStepStatus = MutableLiveData<ContactActivityViewModel.CurrentStep>()

    init {
        nextStepStatus.postValue(ContactActivityViewModel.CurrentStep.Step1)
    }

    override fun onNext(currentStep: ContactActivityViewModel.CurrentStep) {
        // TODO
        when (currentStep) {
            is ContactActivityViewModel.CurrentStep.Step1 -> {
                nextStepStatus.postValue(ContactActivityViewModel.CurrentStep.Step2)
            }

            is ContactActivityViewModel.CurrentStep.Step2 -> {
                // Decide what to do on third step
            }
        }
    }

    override fun onBack(currentStep: ContactActivityViewModel.CurrentStep) {
        when (currentStep) {
            is ContactActivityViewModel.CurrentStep.Step1 -> {
                backStepStatus.postValue(ContactActivityViewModel.CurrentStep.Step1)
            }
            is ContactActivityViewModel.CurrentStep.Step2 -> {
                backStepStatus.postValue(ContactActivityViewModel.CurrentStep.Step2)
            }
        }
    }
}