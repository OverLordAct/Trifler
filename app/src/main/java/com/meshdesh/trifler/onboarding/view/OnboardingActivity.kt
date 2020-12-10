package com.meshdesh.trifler.onboarding.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.meshdesh.trifler.R
import com.meshdesh.trifler.common.animation.AnimationHelper
import com.meshdesh.trifler.signup.view.SignupActivity
import com.meshdesh.trifler.util.setInvisible
import com.meshdesh.trifler.util.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_onboarding.*
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    companion object {
        fun getInstance(context: Context) = Intent(context, OnboardingActivity::class.java).apply {}
    }

    private lateinit var onboardingPagerAdapter: OnboardingPagerAdapter
    private val PAGES_VIEWPAGER = 3

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
            startActivity(SignupActivity.getInstance(this))
            finish()
        }
    }

    private fun setViewPager() {
        onboardingPagerAdapter = OnboardingPagerAdapter(this, PAGES_VIEWPAGER)
        viewpager.adapter = onboardingPagerAdapter
        dots.setViewPager2(viewpager)

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (viewpager.currentItem == PAGES_VIEWPAGER - 1) {
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
            viewpager.setCurrentItem(PAGES_VIEWPAGER - 1, true)
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