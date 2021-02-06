package com.meshdesh.trifler.contact.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.meshdesh.trifler.contact.viewModel.ContactActivityViewModel
import com.meshdesh.trifler.contact.viewModel.ContactActivityViewModelImpl
import com.meshdesh.trifler.databinding.FragmentBottomSheetSendRequestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendRequestBottomSheet : BottomSheetDialogFragment() {

    interface OnClickListener {
        fun onCloseClicked()
    }

    private var listener: OnClickListener? = null
    private var binding: FragmentBottomSheetSendRequestBinding? = null
    private val contactActivityViewModel: ContactActivityViewModelImpl by activityViewModels()

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
            listener?.onCloseClicked()
            contactActivityViewModel.onBack(ContactActivityViewModel.CurrentStep.Step2)
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