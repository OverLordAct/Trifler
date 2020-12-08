package com.meshdesh.trifler.login.view

import android.content.Intent
import android.os.Bundle
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
                // TODO Add leading animation
            }

            is LoginViewModel.LoginStatus.Success -> {
                // TODO Stop loading animation
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            }

            is LoginViewModel.LoginStatus.Failure -> {
                // TODO Stop loading animation
                Toast.makeText(this, loginStatus.message, Toast.LENGTH_SHORT).show()
            }

            is LoginViewModel.LoginStatus.Blank.Email -> {
                // TODO Stop loading animation
                emailContainer.error = loginStatus.message
            }
            is LoginViewModel.LoginStatus.Blank.Password -> {
                // TODO Stop loading animation
                passwordContainer.error = loginStatus.message
            }
            is LoginViewModel.LoginStatus.Blank.Both -> {
                // TODO Stop loading animation
                emailContainer.error = loginStatus.email
                passwordContainer.error = loginStatus.password
            }
        }
    }

    private fun setHeader() {
        header.setButtonClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
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