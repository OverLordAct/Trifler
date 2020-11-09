package com.meshdesh.trifler.common.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import androidx.core.view.marginBottom
import com.meshdesh.trifler.util.getHeightHelper
import com.meshdesh.trifler.util.setGone
import com.meshdesh.trifler.util.setVisible
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimationHelper @Inject constructor() {

     fun disappearIntoBottom(view: View) {
        if (view.visibility == View.GONE) return

        val y = (view.getHeightHelper() + view.marginBottom)

        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0F, y).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    view.setVisible()
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    view.setGone()
                }
            })
            duration = 300
            start()
        }
    }

     fun appearFromBottom(view: View) {
        if (view.visibility == View.VISIBLE) return

        val y = (view.getHeightHelper() + view.marginBottom)

        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, y, 0F).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    view.setVisible()
                }
            })
            duration = 300
            start()
        }
    }
}