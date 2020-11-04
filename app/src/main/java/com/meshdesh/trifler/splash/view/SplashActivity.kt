package com.meshdesh.trifler.splash.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meshdesh.trifler.R
import com.meshdesh.trifler.splash.viewmodel.SplashActivityViewModel
import com.meshdesh.trifler.splash.viewmodel.SplashActivityViewModelImpl
import com.meshdesh.trifler.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModelImpl: SplashActivityViewModelImpl by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModelImpl.accountStatus.observe(this, ::onAccountStatusChanged)
    }

    private fun onAccountStatusChanged(status: SplashActivityViewModel.AccountStatus) {
        when (status) {
            is SplashActivityViewModel.AccountStatus.Authenticated -> {
                // TODO Redirect to Dashboard
                Toast.makeText(this, "Authenticated", Toast.LENGTH_SHORT).show()
            }

            is SplashActivityViewModel.AccountStatus.Unauthenticated -> {
                // TODO Redirect to Onboarding Flow
                Toast.makeText(this, "Unauthenticated", Toast.LENGTH_SHORT).show()
            }
        }
    }
}