package com.meshdesh.trifler.onboarding.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.meshdesh.trifler.R
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    companion object {
        private const val INT = "INT"

        fun newInstance(number: Int): FirstFragment {
            val bundle = Bundle()
            bundle.putInt(INT, number)
            val fragment = FirstFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val int = arguments?.getInt(INT)
        button_1.setOnClickListener {
            Toast.makeText(requireContext(), "$int", Toast.LENGTH_SHORT).show()
        }
    }
}