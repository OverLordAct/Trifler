package com.meshdesh.trifler.category.viewmodel

interface AddCategoryActivityViewModel {
    fun updateCurrentStep(args: CurrentStep)

    sealed class CurrentStep {
        object Step1 : CurrentStep()
        data class Step2(
            val category: String
        ) : CurrentStep()

        object Step3 : CurrentStep()
        object Close : CurrentStep()
    }
}