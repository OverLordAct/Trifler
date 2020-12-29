package com.meshdesh.trifler.signup.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meshdesh.trifler.R
import com.meshdesh.trifler.sigin.view.SigninActivity
import com.meshdesh.trifler.signup.viewModel.SignupViewModel
import com.meshdesh.trifler.signup.viewModel.SignupViewModelImpl
import com.meshdesh.trifler.util.setGone
import com.meshdesh.trifler.util.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_signup.*

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    companion object {
        fun getInstance(context: Context) = Intent(context, SignupActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
    }

    private val viewModel: SignupViewModelImpl by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        viewModel.signupStatusLiveData.observe(this, ::signupStatus)

        setTermsAndConditions()
        setHeader()
        setSignupButton()
    }

    private fun setSignupButton() {
        signUpEmailButton.setOnClickListener {
            val firstName = firstName.text?.toString() ?: ""
            val lastName = lastName.text?.toString() ?: ""
            val email = email.text?.toString() ?: ""
            val password = password.text?.toString() ?: ""
            val conditionCheck = termsCheckbox.isChecked

            firstNameTextLayout.isErrorEnabled = false
            lastNameTextLayout.isErrorEnabled = false
            emailTextLayout.isErrorEnabled = false
            passwordTextLayout.isErrorEnabled = false
            termsCheckbox.error = null

            viewModel.signup(firstName, lastName, email, password, conditionCheck)
        }
    }

    private fun setTermsAndConditions() {
        val spannableString = SpannableString(getString(R.string.signup_terms_and_conditions))

        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.radicalred)),
            11, 27,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.radicalred)),
            32, spannableString.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        termsCheckbox.text = spannableString
    }

    private fun setHeader() {
        header.setButtonClickListener {
            startActivity(SigninActivity.getInstance(this))
        }
        header.setSecondaryButtonClickListener {
            startActivity(SigninActivity.getInstance(this))
        }
    }

    private fun signupStatus(status: SignupViewModel.SignupStatus) {
        when (status) {
            is SignupViewModel.SignupStatus.Loading -> {
                progress.setVisible()
            }

            is SignupViewModel.SignupStatus.Failure -> {
                progress.setGone()
                Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()
            }

            is SignupViewModel.SignupStatus.Success -> {
                progress.setGone()
                Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()
            }

            is SignupViewModel.SignupStatus.Empty.FirstName -> {
                progress.setGone()
                firstNameTextLayout.isErrorEnabled = true
                firstNameTextLayout.error = getString(R.string.signup_firstname_blank)
            }

            is SignupViewModel.SignupStatus.Empty.LastName -> {
                progress.setGone()
                lastNameTextLayout.isErrorEnabled = true
                lastNameTextLayout.error = getString(R.string.signup_lastname_blank)
            }

            is SignupViewModel.SignupStatus.Empty.Email -> {
                progress.setGone()
                emailTextLayout.isErrorEnabled = true
                emailTextLayout.error = getString(R.string.signup_email_blank)
            }

            is SignupViewModel.SignupStatus.Empty.Password -> {
                progress.setGone()
                passwordTextLayout.isErrorEnabled = true
                passwordTextLayout.error = getString(R.string.signup_password_blank)
            }

            is SignupViewModel.SignupStatus.Empty.Condition -> {
                progress.setGone()
                termsCheckbox.error = getString(R.string.signup_termsandconditions_unchecked)
            }
        }
    }
}