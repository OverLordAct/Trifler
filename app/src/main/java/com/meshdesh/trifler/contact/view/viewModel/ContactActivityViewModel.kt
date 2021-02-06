package com.meshdesh.trifler.contact.view.viewModel

import androidx.lifecycle.LiveData

interface ContactActivityViewModel {

    val nextStepStatus: LiveData<CurrentStep>
    val backStepStatus: LiveData<CurrentStep>

    sealed class CurrentStep {
        object Step1 : CurrentStep()
        object Step2 : CurrentStep()
    }

    fun onNext(currentStep: CurrentStep)

    fun onBack(currentStep: CurrentStep)
}