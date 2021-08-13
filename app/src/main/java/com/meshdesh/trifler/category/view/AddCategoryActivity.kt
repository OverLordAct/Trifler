package com.meshdesh.trifler.category.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.meshdesh.trifler.category.viewmodel.AddCategoryActivityViewModel
import com.meshdesh.trifler.category.viewmodel.AddCategoryActivityViewModelImpl
import com.meshdesh.trifler.databinding.ActivityAddCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCategoryBinding
    private val viewModel by viewModels<AddCategoryActivityViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.currentStepLiveData.observe(this, ::observeCurrentStep)
    }

    private fun observeCurrentStep(currentStep: AddCategoryActivityViewModel.CurrentStep) {
        when (currentStep) {
            is AddCategoryActivityViewModel.CurrentStep.Step1 -> {
                setInitialFragment()
            }
            is AddCategoryActivityViewModel.CurrentStep.Step2 -> {
                showCategoryAddSuccessFragment(currentStep)
            }
            is AddCategoryActivityViewModel.CurrentStep.Step3, AddCategoryActivityViewModel.CurrentStep.Close -> {
                finish()
            }
        }
    }

    private fun setInitialFragment() {
        supportFragmentManager.commit {
            val fragment = AddCategoryFragment()
            add(binding.container.id, fragment)
            setReorderingAllowed(true)
        }
    }

    private fun showCategoryAddSuccessFragment(currentStep: AddCategoryActivityViewModel.CurrentStep.Step2) {
        supportFragmentManager.commit {
            val args = currentStep.category
            val fragment =
                AddCategorySuccessFragment.create(AddCategorySuccessFragment.Args(args))
            replace(binding.container.id, fragment)
            setReorderingAllowed(true)
        }
    }
}