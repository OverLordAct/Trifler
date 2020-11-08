package com.meshdesh.trifler.onboarding.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
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

                if (viewpager.currentItem == PAGES - 1) {
                    skipButton.visibility = View.INVISIBLE
                    appearFromBottom(getStarted)
                    disappearIntoBottom(dots)
                } else {
                    setSkipVisible()
                    disappearIntoBottom(getStarted)
                    appearFromBottom(dots)
                }
            }
        })
    }

    private fun setSkipVisible() {
        if (skipButton.visibility == View.VISIBLE) return
        skipButton.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (viewpager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewpager.currentItem -= 1
        }
    }

    private fun disappearIntoBottom(view: View) {
        if (view.visibility == View.GONE) return

        val y = (view.getHeightHelper() + view.marginBottom)

        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0F, y).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    view.visibility = View.GONE
                }
            })
            duration = 300
            start()
        }
    }

    private fun appearFromBottom(view: View) {
        if (view.visibility == View.VISIBLE) return

        val y = (view.getHeightHelper() + view.marginBottom)

        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, y, 0F).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    view.visibility = View.VISIBLE
                }
            })
            duration = 300
            start()
        }
    }
}

fun View.getHeightHelper(): Float {
    measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    val height = measuredHeight
    return height.toFloat()
}