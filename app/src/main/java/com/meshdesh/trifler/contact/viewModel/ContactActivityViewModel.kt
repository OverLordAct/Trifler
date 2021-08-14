package com.meshdesh.trifler.contact.viewModel

import androidx.lifecycle.LiveData

interface ContactActivityViewModel {

    val nextStepStatus: LiveData<UiState>
    val backStepStatus: LiveData<UiState>

    sealed class UiState {
        object Step1 : UiState()
        data class Step2(
            val contactNumber: String
        ) : UiState()

        data class Step3(
            val contactNumber: String
        ) : UiState()

        object Step4 : UiState()
    }

    fun onNext(uiState: UiState)

    fun onBack(uiState: UiState)
}