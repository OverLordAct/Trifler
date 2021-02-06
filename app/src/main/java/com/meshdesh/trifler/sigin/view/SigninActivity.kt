package com.meshdesh.trifler.sigin.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meshdesh.trifler.R
import com.meshdesh.trifler.dashboard.view.DashboardActivity
import com.meshdesh.trifler.databinding.ActivitySigninBinding
import com.meshdesh.trifler.sigin.viewmodel.SigninViewModel
import com.meshdesh.trifler.sigin.viewmodel.SigninViewModelImpl
import com.meshdesh.trifler.signup.view.SignupActivity
import com.meshdesh.trifler.util.setGone
import com.meshdesh.trifler.util.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninActivity : AppCompatActivity() {

    companion object {
        fun getInstance(context: Context) = Intent(context, SigninActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
    }

    private val viewModel: SigninViewModelImpl by viewModels()
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.signinStatusLiveData.observe(this, ::loginStatusUpdate)

        setHeader()
        binding.loginButton.setOnClickListener { startLogin() }
    }

    private fun loginStatusUpdate(signinStatus: SigninViewModel.SigninStatus) {
        when (signinStatus) {
            is SigninViewModel.SigninStatus.Loading -> {
                binding.progress.setVisible()
            }

            is SigninViewModel.SigninStatus.Success -> {
                // TODO Redirect
                binding.progress.setGone()
                Toast.makeText(this, signinStatus.message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }

            is SigninViewModel.SigninStatus.Failure -> {
                binding.progress.setGone()
                Toast.makeText(this, signinStatus.message, Toast.LENGTH_SHORT).show()
            }

            is SigninViewModel.SigninStatus.Blank.Email -> {
                binding.progress.setGone()
                binding.emailContainer.error = getString(R.string.email_blank)
            }
            is SigninViewModel.SigninStatus.Blank.Password -> {
                binding.progress.setGone()
                binding.passwordContainer.error = getString(R.string.password_blank)
            }
        }
    }

    private fun setHeader() {
        binding.header.setButtonClickListener {
            startActivity(SignupActivity.getInstance(this))
        }
        binding.header.setSecondaryButtonClickListener {
            startActivity(SignupActivity.getInstance(this))
        }
    }

    private fun startLogin() {
        // TODO Restore Login
        binding.emailContainer.isErrorEnabled = false
        binding.passwordContainer.isErrorEnabled = false

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        // viewModel.startSignin(email, password)

        binding.progress.setGone()
        // Toast.makeText(this, signinStatus.message, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}