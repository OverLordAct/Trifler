package com.meshdesh.trifler.onboarding.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingPagerAdapter(
    activity: FragmentActivity,
    private val count: Int
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = count

    override fun createFragment(position: Int): Fragment {
        return FirstFragment.newInstance(position)
    }

}