package com.meshdesh.trifler.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCategoryActivityViewModelImpl @Inject constructor() : ViewModel(),
    AddCategoryActivityViewModel {

    private val _currentStepLiveData = MutableLiveData<AddCategoryActivityViewModel.CurrentStep>()
    val currentStepLiveData: LiveData<AddCategoryActivityViewModel.CurrentStep>
        get() = _currentStepLiveData

    init {
        _currentStepLiveData.value = AddCategoryActivityViewModel.CurrentStep.Step1
    }

    override fun updateCurrentStep(args: AddCategoryActivityViewModel.CurrentStep) {
        when (args) {
            is AddCategoryActivityViewModel.CurrentStep.Close -> {
                _currentStepLiveData.value = AddCategoryActivityViewModel.CurrentStep.Close
            }
            is AddCategoryActivityViewModel.CurrentStep.Step2 -> {
                _currentStepLiveData.value =
                    AddCategoryActivityViewModel.CurrentStep.Step2(args.category)
            }
            is AddCategoryActivityViewModel.CurrentStep.Step3 -> {
                _currentStepLiveData.value = AddCategoryActivityViewModel.CurrentStep.Step3
            }
            else -> {
            }
        }
    }
}