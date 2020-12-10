package com.meshdesh.trifler.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meshdesh.trifler.R
import com.meshdesh.trifler.login.viewmodel.LoginViewModel
import com.meshdesh.trifler.login.viewmodel.LoginViewModelImpl
import com.meshdesh.trifler.signup.view.SignupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        fun getInstance(context: Context) = Intent(context, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
    }

    private val viewModel: LoginViewModelImpl by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.loginStatusLiveData.observe(this, ::loginStatusUpdate)

        setHeader()
        loginButton.setOnClickListener { startLogin() }
    }

    private fun loginStatusUpdate(loginStatus: LoginViewModel.LoginStatus) {
        when (loginStatus) {
            is LoginViewModel.LoginStatus.Loading -> {
                progress.visibility = View.VISIBLE
            }

            is LoginViewModel.LoginStatus.Success -> {
                // TODO Redirect
                progress.visibility = View.GONE
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            }

            is LoginViewModel.LoginStatus.Failure -> {
                progress.visibility = View.GONE
                Toast.makeText(this, loginStatus.message, Toast.LENGTH_SHORT).show()
            }

            is LoginViewModel.LoginStatus.Blank.Email -> {
                progress.visibility = View.GONE
                emailContainer.error = loginStatus.message
            }
            is LoginViewModel.LoginStatus.Blank.Password -> {
                progress.visibility = View.GONE
                passwordContainer.error = loginStatus.message
            }
            is LoginViewModel.LoginStatus.Blank.Both -> {
                progress.visibility = View.GONE
                emailContainer.error = loginStatus.email
                passwordContainer.error = loginStatus.password
            }
        }
    }

    private fun setHeader() {
        header.setButtonClickListener {
            startActivity(SignupActivity.getInstance(this))
        }
        header.setSecondaryButtonClickListener {
            startActivity(SignupActivity.getInstance(this))
        }
    }

    private fun startLogin() {
        emailContainer.isErrorEnabled = false
        passwordContainer.isErrorEnabled = false

        val email = emailText.text?.toString()
        val password = passwordText.text?.toString()

        viewModel.startLogin(email, password)
    }
}