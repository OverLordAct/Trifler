package com.meshdesh.trifler.contact.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.meshdesh.trifler.contact.view.viewModel.ContactActivityViewModelImpl
import com.meshdesh.trifler.databinding.FragmentAddContactBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactFragment : Fragment() {

    private val contactActivityViewModel: ContactActivityViewModelImpl by activityViewModels()
    private var binding: FragmentAddContactBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddContactBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButton()
    }

    private fun setupButton() {
        binding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}