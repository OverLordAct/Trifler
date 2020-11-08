package com.meshdesh.trifler.onboarding.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.meshdesh.trifler.R

class OnboardingPagerAdapter(
    activity: FragmentActivity,
    private val count: Int
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = count

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> OnboardingFragment.newInstance(
                OnboardingFragment.Companion.Args(
                    R.drawable.ic_onboard_1,
                    "Easy connect with new people",
                    "Connect with people in one tap"
                )
            )
            2 -> OnboardingFragment.newInstance(
                OnboardingFragment.Companion.Args(
                    R.drawable.ic_onboard_2,
                    "Share conntacts with one tap",
                    "Literally with one tap, all your social profiles"
                )
            )
            else -> OnboardingFragment.newInstance(
                OnboardingFragment.Companion.Args(
                    R.drawable.ic_onboard_3,
                    "Categories people in your life",
                    "Easily access people by putting gthem in different categories"
                )
            )
        }
    }

}