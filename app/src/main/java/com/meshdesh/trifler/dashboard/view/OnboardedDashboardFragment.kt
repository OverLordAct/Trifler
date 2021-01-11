package com.meshdesh.trifler.dashboard.view

import android.graphics.drawable.AnimatedVectorDrawable
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
        return inflater.inflate(R.layout.fragment_onboarded_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        primaryButton.setOnClickListener {
            if (collapsibleLayout.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(collapsibleLayout, AutoTransition())
                collapsibleLayout.visibility = View.VISIBLE
                overlayFrame.visibility = View.VISIBLE
                primaryButton.setIconResource(R.drawable.arrow_down_up)
                (primaryButton.icon as AnimatedVectorDrawable).start()
            } else {
                TransitionManager.beginDelayedTransition(collapsibleLayout, AutoTransition())
                collapsibleLayout.visibility = View.GONE
                overlayFrame.visibility = View.GONE
                primaryButton.setIconResource(R.drawable.arrow_up_down)
                (primaryButton.icon as AnimatedVectorDrawable).start()
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
        catergoryRecycler.setHasFixedSize(true)
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