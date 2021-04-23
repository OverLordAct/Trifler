package com.meshdesh.trifler.dashboard.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meshdesh.trifler.category.view.AddCategoryActivity
import com.meshdesh.trifler.contact.view.ContactActivity
import com.meshdesh.trifler.databinding.FragmentOnboardingDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingDashboardFragment : Fragment() {

    private var binding: FragmentOnboardingDashboardBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingDashboardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding?.let {
            it.addContactCard.setCardClickListener {
                startActivity(Intent(activity, ContactActivity::class.java))
            }

            it.makeNewCategoriesCard.setOnClickListener {
                startActivity(Intent(activity, AddCategoryActivity::class.java))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}