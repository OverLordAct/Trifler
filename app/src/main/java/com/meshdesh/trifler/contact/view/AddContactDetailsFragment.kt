package com.meshdesh.trifler.contact.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.meshdesh.trifler.R
import com.meshdesh.trifler.databinding.FragmentAddContactDetailsBinding
import com.meshdesh.trifler.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactDetailsFragment : Fragment() {

    private var binding: FragmentAddContactDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddContactDetailsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf("Temporary", "Family", "Friends", "Work")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_category, items)
        (binding?.categoryLayout?.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (binding?.categoryLayout?.editText as? AutoCompleteTextView)?.setText(items[0], false)
        (binding?.categoryLayout?.editText as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, id ->
            // TODO Refactor this logic into viewModel
            requireContext().showToast(items[position] + " " + id)
            if (position != 0) binding?.contactDurationLayout?.visibility = View.GONE
            else binding?.contactDurationLayout?.visibility = View.VISIBLE

        }

        val durationItems = listOf("1 Week", "1 Month", "2 Month", "3 Month")
        val durationAdapter =
            ArrayAdapter(requireContext(), R.layout.list_item_category, durationItems)
        (binding?.contactDurationLayout?.editText as? AutoCompleteTextView)?.let {
            it.setAdapter(durationAdapter)
            it.setText(durationItems[0], false)
            it.setOnItemClickListener { _, _, position, id ->
                // TODO Refactor this logic into viewModel
                requireContext().showToast(items[position] + " " + id)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}