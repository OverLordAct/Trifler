package com.meshdesh.trifler.contact.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.meshdesh.trifler.contact.viewModel.AddContactActivityViewModel
import com.meshdesh.trifler.contact.viewModel.AddContactActivityViewModelImpl
import com.meshdesh.trifler.databinding.FragmentAddContactNumberBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactNumberFragment : Fragment() {

    private val contactActivityViewModel: AddContactActivityViewModelImpl by activityViewModels()
    private var binding: FragmentAddContactNumberBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddContactNumberBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButton()
    }

    private fun setupButton() {
        binding?.cta?.setOnClickListener {
            // TODO: 15-08-2021 Handle incorrect phone numbers here
            val contactNumber = binding?.phoneInput?.text?.toString() ?: return@setOnClickListener
            contactActivityViewModel.updateCurrentState(
                AddContactActivityViewModel.UiState.Step2(
                    contactNumber
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}