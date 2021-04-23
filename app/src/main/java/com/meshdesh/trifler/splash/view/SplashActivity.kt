package com.meshdesh.trifler.splash.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meshdesh.trifler.R
import com.meshdesh.trifler.dashboard.view.DashboardActivity
import com.meshdesh.trifler.onboarding.view.OnboardingActivity
import com.meshdesh.trifler.sigin.view.SigninActivity
import com.meshdesh.trifler.splash.viewmodel.SplashActivityViewModel
import com.meshdesh.trifler.splash.viewmodel.SplashActivityViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash.*

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModelImpl: SplashActivityViewModelImpl by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        logo.animate()
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)

                    viewModelImpl.accountStatus.observe(
                        this@SplashActivity,
                        ::onAccountStatusChanged
                    )
                }
            })
            .alpha(1F)
            .duration = 1000
    }

    private fun onAccountStatusChanged(status: SplashActivityViewModel.AccountStatus) {
        when (status) {
            is SplashActivityViewModel.AccountStatus.Authenticated -> {
                // TODO Redirect to Dashboard
                Toast.makeText(this, "Authenticated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
            }
            is SplashActivityViewModel.AccountStatus.Unauthenticated -> {
                when (status.isNew) {
                    true -> {
                        startActivity(OnboardingActivity.getInstance(this))
                    }
                    false -> {
                        startActivity(SigninActivity.getInstance(this))
                    }
                }
                overridePendingTransition(
                    R.animator.appears_from_right,
                    R.animator.disappear_to_left
                )
            }
        }
        finish()
    }
}