package com.meshdesh.trifler.onboarding.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meshdesh.trifler.R
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPagerActivity : AppCompatActivity() {

    private lateinit var onboardingPagerAdapter: OnboardingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        onboardingPagerAdapter = OnboardingPagerAdapter(this, 3)
        viewpager.adapter = onboardingPagerAdapter
        dots.setViewPager2(viewpager)
    }
}