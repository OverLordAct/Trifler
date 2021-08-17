package com.meshdesh.trifler.contact.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.meshdesh.trifler.R
import com.meshdesh.trifler.contact.viewModel.AddContactActivityViewModel
import com.meshdesh.trifler.contact.viewModel.AddContactActivityViewModelImpl
import com.meshdesh.trifler.databinding.FragmentAddContactSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactSuccessFragment : Fragment() {

    companion object {
        fun create(): AddContactSuccessFragment {
            return AddContactSuccessFragment()
        }
    }

    private val activityViewModel by activityViewModels<AddContactActivityViewModelImpl>()
    private var binding: FragmentAddContactSuccessBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddContactSuccessBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: 15-08-2021 Handle error cases
        binding?.statusText?.text = getString(R.string.contact_step_4_success)
        // binding?.statusText?.text = getText(R.string.contact_step_4_success)
        binding?.cta?.setOnClickListener {
            activityViewModel.updateCurrentState(AddContactActivityViewModel.UiState.Finish)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}