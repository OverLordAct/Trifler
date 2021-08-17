package com.meshdesh.trifler.category.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.meshdesh.trifler.R
import com.meshdesh.trifler.category.viewmodel.AddCategoryActivityViewModel
import com.meshdesh.trifler.category.viewmodel.AddCategoryActivityViewModelImpl
import com.meshdesh.trifler.category.viewmodel.AddCategoryFragmentViewModel
import com.meshdesh.trifler.category.viewmodel.AddCategoryFragmentViewModelImpl
import com.meshdesh.trifler.databinding.FragmentAddCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCategoryFragment : Fragment() {

    private var binding: FragmentAddCategoryBinding? = null
    private val activityViewModel by activityViewModels<AddCategoryActivityViewModelImpl>()
    private val fragmentViewModel by viewModels<AddCategoryFragmentViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonListeners()

        fragmentViewModel.uiState.observe(viewLifecycleOwner, ::observeCategoryStatus)
    }

    private fun setButtonListeners() {
        binding?.cta?.setOnClickListener {
            val category = binding?.categoryInput?.text.toString()
            fragmentViewModel.addCategory(category)
        }
        binding?.backButton?.setOnClickListener {
            activityViewModel.updateCurrentStep(AddCategoryActivityViewModel.CurrentStep.Close)
        }
    }

    private fun observeCategoryStatus(status: AddCategoryFragmentViewModel.UiState) {
        when (status) {
            is AddCategoryFragmentViewModel.UiState.Loading -> {
                binding?.progress?.visibility = View.VISIBLE
                binding?.categoryInputLayout?.isErrorEnabled = false
            }
            is AddCategoryFragmentViewModel.UiState.Empty -> {
                binding?.progress?.visibility = View.GONE
                binding?.categoryInputLayout?.isErrorEnabled = true
                binding?.categoryInputLayout?.error =
                    getString(R.string.category_step_1_empty_error)
            }
            is AddCategoryFragmentViewModel.UiState.Duplicate -> {
                binding?.progress?.visibility = View.GONE
                binding?.categoryInputLayout?.isErrorEnabled = true
                binding?.categoryInputLayout?.error =
                    getString(R.string.category_step_1_duplicate_error)
            }
            is AddCategoryFragmentViewModel.UiState.Error -> {
                // TODO Explore use of snackbar
                binding?.progress?.visibility = View.GONE
                binding?.categoryInputLayout?.isErrorEnabled = true
                binding?.categoryInputLayout?.error =
                    getString(R.string.category_step_1_network_error)
            }
            is AddCategoryFragmentViewModel.UiState.Success -> {
                binding?.progress?.visibility = View.GONE
                binding?.categoryInputLayout?.isErrorEnabled = false
                activityViewModel.updateCurrentStep(
                    AddCategoryActivityViewModel.CurrentStep.Step2(
                        status.category
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}