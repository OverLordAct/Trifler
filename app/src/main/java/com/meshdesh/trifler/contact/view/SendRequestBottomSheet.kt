package com.meshdesh.trifler.contact.view

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.meshdesh.trifler.contact.viewModel.AddContactActivityViewModel
import com.meshdesh.trifler.contact.viewModel.AddContactActivityViewModelImpl
import com.meshdesh.trifler.databinding.FragmentBottomSheetSendRequestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize

@AndroidEntryPoint
class SendRequestBottomSheet : BottomSheetDialogFragment() {

    interface OnClickListener {
        fun onContinue()
        fun onSendRequest(contactNumber: String)
    }

    companion object {
        private const val EXTRA_CONTACT_ARGS = "EXTRA_CONTACT_ARGS"

        @Parcelize
        data class Args(val contactNumber: String) : Parcelable

        fun create(args: Args): SendRequestBottomSheet {
            return SendRequestBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_CONTACT_ARGS, args)
                }
            }
        }
    }

    private var listener: OnClickListener? = null
    private var binding: FragmentBottomSheetSendRequestBinding? = null
    private val contactActivityViewModel: AddContactActivityViewModelImpl by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetSendRequestBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.closeButton?.setOnClickListener {
            if (!isStateSaved) dismiss() else dismissAllowingStateLoss()
        }

        binding?.cta2?.setOnClickListener {
            val args = arguments?.getParcelable<Args>(EXTRA_CONTACT_ARGS) ?: throw RuntimeException(
                "Args expected here"
            )
            val contactNumber = args.contactNumber

            contactActivityViewModel.updateCurrentState(
                AddContactActivityViewModel.UiState.Step3(
                    contactNumber
                )
            )

            if (!isStateSaved) dismiss() else dismissAllowingStateLoss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnClickListener)
            listener =
                context else throw RuntimeException("Context must be of type OnClickListener")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}