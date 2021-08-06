package com.meshdesh.trifler.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meshdesh.trifler.R
import com.meshdesh.trifler.dashboard.view.DashboardActivity
import com.meshdesh.trifler.databinding.ActivityLoginBinding
import com.meshdesh.trifler.login.viewmodel.LoginViewModel
import com.meshdesh.trifler.login.viewmodel.LoginViewModelImpl
import com.meshdesh.trifler.signup.view.SignupActivity
import com.meshdesh.trifler.util.setGone
import com.meshdesh.trifler.util.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        fun getInstance(context: Context) = Intent(context, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
    }

    private val viewModel: LoginViewModelImpl by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loginStatusLiveData.observe(this, ::loginStatusUpdate)

        setHeader()
        binding.loginButton.setOnClickListener { startLogin() }
    }

    private fun loginStatusUpdate(loginStatus: LoginViewModel.LoginStatus) {
        when (loginStatus) {
            is LoginViewModel.LoginStatus.Loading -> {
                binding.progress.setVisible()
            }

            is LoginViewModel.LoginStatus.Success -> {
                // TODO Redirect
                binding.progress.setGone()
                Toast.makeText(this, loginStatus.message, Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }

            is LoginViewModel.LoginStatus.Failure -> {
                binding.progress.setGone()
                Toast.makeText(this, loginStatus.message, Toast.LENGTH_SHORT).show()
            }

            is LoginViewModel.LoginStatus.Blank.Email -> {
                binding.progress.setGone()
                binding.emailContainer.error = getString(R.string.email_blank)
            }
            is LoginViewModel.LoginStatus.Blank.Password -> {
                binding.progress.setGone()
                binding.passwordContainer.error = getString(R.string.password_blank)
            }
        }
    }

    private fun setHeader() {
        binding.header.setButtonClickListener {
            openSignup()
        }
        binding.header.setSecondaryButtonClickListener {
            openSignup()
        }
    }

    private fun openSignup() {
        startActivity(SignupActivity.getInstance(this))
        finish()
    }

    private fun startLogin() {
        // TODO Restore Login
        binding.emailContainer.isErrorEnabled = false
        binding.passwordContainer.isErrorEnabled = false

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        viewModel.startLogin(email, password)

        // binding.progress.setGone()
        // // Toast.makeText(this, signinStatus.message, Toast.LENGTH_SHORT).show()
        // startActivity(Intent(this, DashboardActivity::class.java))
        // finish()
    }
}