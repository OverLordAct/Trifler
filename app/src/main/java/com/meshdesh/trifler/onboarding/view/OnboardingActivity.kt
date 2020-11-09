package com.meshdesh.trifler.onboarding.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.meshdesh.trifler.R
import com.meshdesh.trifler.common.animation.AnimationHelper
import com.meshdesh.trifler.login.view.LoginActivity
import com.meshdesh.trifler.util.setInvisible
import com.meshdesh.trifler.util.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_onboarding.*
import javax.inject.Inject

const val PAGES = 3

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingPagerAdapter: OnboardingPagerAdapter

    @Inject
    lateinit var animationHelper: AnimationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        setViewPager()
        setSkipButton()
        setGetStartedButton()
    }

    private fun setGetStartedButton() {
        getStarted.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setViewPager() {
        onboardingPagerAdapter = OnboardingPagerAdapter(this, PAGES)
        viewpager.adapter = onboardingPagerAdapter
        dots.setViewPager2(viewpager)

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (viewpager.currentItem == PAGES - 1) {
                    skipButton.setInvisible()
                    animationHelper.appearFromBottom(getStarted)
                    animationHelper.disappearIntoBottom(dots)
                } else {
                    skipButton.setVisible()
                    animationHelper.disappearIntoBottom(getStarted)
                    animationHelper.appearFromBottom(dots)
                }
            }
        })
    }

    private fun setSkipButton() {
        skipButton.setOnClickListener {
            viewpager.setCurrentItem(PAGES - 1, true)
        }
    }

    override fun onBackPressed() {
        if (viewpager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewpager.currentItem -= 1
        }
    }
}