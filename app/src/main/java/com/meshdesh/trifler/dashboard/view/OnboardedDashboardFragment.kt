package com.meshdesh.trifler.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.meshdesh.trifler.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_dashboard_onboarded_dropdown_primary.*
import kotlinx.android.synthetic.main.fragment_onboarded_dashboard.*

@AndroidEntryPoint
class OnboardedDashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_onboarded_dashboard, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        primaryButton.setOnClickListener {
            if (collapsibleLayout.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(collapsibleLayout, AutoTransition())
                collapsibleLayout.visibility = View.VISIBLE
                overlayFrame.visibility = View.VISIBLE
                // TODO Fix arrow icon
                primaryButton.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_up,
                    0
                )
            } else {
                TransitionManager.beginDelayedTransition(collapsibleLayout, AutoTransition())
                collapsibleLayout.visibility = View.GONE
                overlayFrame.visibility = View.GONE
                primaryButton.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_down,
                    0
                )
            }
        }

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = CategoriesAdapter()

        catergoryRecycler.layoutManager = linearLayoutManager
        catergoryRecycler.adapter = adapter
        catergoryRecycler.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            OnboardedDashboardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}