package com.meshdesh.trifler.contact.view

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.meshdesh.trifler.R
import com.meshdesh.trifler.contact.viewModel.AddContactActivityViewModel
import com.meshdesh.trifler.contact.viewModel.AddContactActivityViewModelImpl
import com.meshdesh.trifler.contact.viewModel.AddContactDetailsViewModel
import com.meshdesh.trifler.contact.viewModel.AddContactDetailsViewModelImpl
import com.meshdesh.trifler.databinding.FragmentAddContactDetailsBinding
import com.meshdesh.trifler.util.setInvisible
import com.meshdesh.trifler.util.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize

@AndroidEntryPoint
class AddContactDetailsFragment : Fragment() {

    companion object {
        private const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"

        @Parcelize
        data class Args(val contactNumber: String) : Parcelable

        fun create(args: Args): AddContactDetailsFragment {
            val fragment = AddContactDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_CONTACT_NUMBER, args)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var binding: FragmentAddContactDetailsBinding? = null

    private val viewModel: AddContactDetailsViewModelImpl by viewModels()
    private val activityViewModel by activityViewModels<AddContactActivityViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddContactDetailsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.getParcelable<Args>(EXTRA_CONTACT_NUMBER)
            ?: throw Exception("Arguments expected here")

        viewModel.uiStateLiveData.observe(viewLifecycleOwner, ::observeUiState)
    }

    private fun observeUiState(state: AddContactDetailsViewModel.UiState) {
        when (state) {
            is AddContactDetailsViewModel.UiState.Loading -> {
                binding?.progress?.setVisible()
            }
            is AddContactDetailsViewModel.UiState.DownloadError -> {
                // TODO: 15-08-2021 Handle this case
                binding?.progress?.setInvisible()
            }
            is AddContactDetailsViewModel.UiState.FormDownloaded -> {
                binding?.progress?.setInvisible()

                val categoryList = state.categoryList.map { it.categoryName }
                val durationList = state.durationList

                val categoryAdapter =
                    ArrayAdapter(requireContext(), R.layout.list_item_category, categoryList)
                binding?.categoryLayout?.let {
                    (it.editText as? AutoCompleteTextView)?.setAdapter(categoryAdapter)
                    (it.editText as? AutoCompleteTextView)?.setText(
                        categoryList[0],
                        false
                    )
                    (it.editText as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, id ->
                        // TODO Refactor this logic into viewModel
                        if (position != 0) binding?.contactDurationLayout?.setInvisible()
                        else binding?.contactDurationLayout?.setVisible()
                    }
                }

                val durationAdapter =
                    ArrayAdapter(requireContext(), R.layout.list_item_category, durationList)
                (binding?.contactDurationLayout?.editText as? AutoCompleteTextView)?.let {
                    it.setAdapter(durationAdapter)
                    it.setText(durationList[0], false)
                    it.setOnItemClickListener { _, _, position, id ->
                        // TODO Refactor this logic into viewModel
                    }
                }

                setupCta()
            }
            is AddContactDetailsViewModel.UiState.Success -> {
                activityViewModel.updateCurrentState(AddContactActivityViewModel.UiState.Step4)
            }
        }
    }

    private fun setupCta() {
        binding?.cta?.setOnClickListener {
            val contactName =
                binding?.contactNameInput?.text?.toString() ?: return@setOnClickListener
            val categoryName = binding?.categoryInput?.text?.toString() ?: return@setOnClickListener
            val contactDuration =
                binding?.contactDurationInput?.text?.toString() ?: return@setOnClickListener

            Toast.makeText(
                requireContext(),
                "$contactName $categoryName $contactDuration",
                Toast.LENGTH_SHORT
            ).show()

            viewModel.addContact()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}