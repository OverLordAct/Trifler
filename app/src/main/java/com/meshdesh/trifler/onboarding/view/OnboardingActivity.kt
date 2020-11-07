package com.meshdesh.trifler.onboarding.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.meshdesh.trifler.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_onboarding.*

const val PAGES = 3

@AndroidEntryPoint
class ViewPagerActivity : AppCompatActivity() {

    private lateinit var onboardingPagerAdapter: OnboardingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        setViewPager()
        setSkipButton()
    }

    private fun setSkipButton() {
        skipButton.setOnClickListener {
            viewpager.setCurrentItem(PAGES - 1, true)
        }
    }

    private fun setViewPager() {
        onboardingPagerAdapter = OnboardingPagerAdapter(this, PAGES)
        viewpager.adapter = onboardingPagerAdapter
        dots.setViewPager2(viewpager)

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (viewpager.currentItem == 2) {
                    skipButton.visibility = View.INVISIBLE
                } else {
                    skipButton.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onBackPressed() {
        if (viewpager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewpager.currentItem -= 1
        }
    }
}