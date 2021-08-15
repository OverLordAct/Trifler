package com.meshdesh.trifler.contact.viewModel

import androidx.lifecycle.LiveData

interface AddContactActivityViewModel {

    val currentStepStatus: LiveData<UiState>

    sealed class UiState {
        object Step1 : UiState()
        data class Step2(
            val contactNumber: String
        ) : UiState()

        data class Step3(
            val contactNumber: String
        ) : UiState()

        object Step4 : UiState()
        object Finish : UiState()
    }
}