package com.meshdesh.trifler.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meshdesh.trifler.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingDashboardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding_dashboard, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OnboardingDashboardFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}