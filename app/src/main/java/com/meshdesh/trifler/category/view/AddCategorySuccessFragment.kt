package com.meshdesh.trifler.category.view

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.meshdesh.trifler.category.viewmodel.AddCategoryActivityViewModel
import com.meshdesh.trifler.category.viewmodel.AddCategoryActivityViewModelImpl
import com.meshdesh.trifler.category.viewmodel.AddCategorySuccessViewModel
import com.meshdesh.trifler.category.viewmodel.AddCategorySuccessViewModelImpl
import com.meshdesh.trifler.databinding.FragmentCategoryAddSuccessBinding
import com.meshdesh.trifler.util.setInvisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize

@AndroidEntryPoint
class AddCategorySuccessFragment : Fragment() {

    @Parcelize
    data class Args(
        val category: String
    ) : Parcelable

    companion object {
        const val EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME"

        fun create(args: Args): AddCategorySuccessFragment {
            val fragment = AddCategorySuccessFragment()
            val bundle = Bundle()
            try {
                bundle.putParcelable(EXTRA_CATEGORY_NAME, args)
                fragment.arguments = bundle
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return fragment
        }
    }

    private var binding: FragmentCategoryAddSuccessBinding? = null
    private val activityViewModel by activityViewModels<AddCategoryActivityViewModelImpl>()
    private val fragmentViewModel: AddCategorySuccessViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryAddSuccessBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.cta?.setOnClickListener {
            activityViewModel.updateCurrentStep(AddCategoryActivityViewModel.CurrentStep.Step3)
        }

        fragmentViewModel.statusLiveData.observe(viewLifecycleOwner, ::onStatusUpdate)
    }

    private fun onStatusUpdate(status: AddCategorySuccessViewModel.Status) {
        when (status) {
            is AddCategorySuccessViewModel.Status.Success -> {
                binding?.categoryCard?.setCategory(status.category)
            }
            is AddCategorySuccessViewModel.Status.Failure -> {
                // TODO: 23-04-2021 Set Error UI
                binding?.categoryCard?.setInvisible()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}