package com.meshdesh.trifler.sigin.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meshdesh.trifler.R
import com.meshdesh.trifler.sigin.viewmodel.SigninViewModel
import com.meshdesh.trifler.sigin.viewmodel.SigninViewModelImpl
import com.meshdesh.trifler.signup.view.SignupActivity
import com.meshdesh.trifler.util.setGone
import com.meshdesh.trifler.util.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class SigninActivity : AppCompatActivity() {

    companion object {
        fun getInstance(context: Context) = Intent(context, SigninActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
    }

    private val viewModel: SigninViewModelImpl by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.signinStatusLiveData.observe(this, ::loginStatusUpdate)

        setHeader()
        loginButton.setOnClickListener { startLogin() }
    }

    private fun loginStatusUpdate(signinStatus: SigninViewModel.SigninStatus) {
        when (signinStatus) {
            is SigninViewModel.SigninStatus.Loading -> {
                progress.setVisible()
            }

            is SigninViewModel.SigninStatus.Success -> {
                // TODO Redirect
                progress.setGone()
                Toast.makeText(this, signinStatus.message, Toast.LENGTH_SHORT).show()
            }

            is SigninViewModel.SigninStatus.Failure -> {
                progress.setGone()
                Toast.makeText(this, signinStatus.message, Toast.LENGTH_SHORT).show()
            }

            is SigninViewModel.SigninStatus.Blank.Email -> {
                progress.setGone()
                emailContainer.error = getString(R.string.email_blank)
            }
            is SigninViewModel.SigninStatus.Blank.Password -> {
                progress.setGone()
                passwordContainer.error = getString(R.string.password_blank)
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

        val email = emailText.text?.toString() ?: ""
        val password = passwordText.text?.toString() ?: ""

        viewModel.startSignin(email, password)
    }
}