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
import com.meshdesh.trifler.databinding.ActivitySignupBinding
import com.meshdesh.trifler.login.view.LoginActivity
import com.meshdesh.trifler.signup.viewModel.SignupViewModel
import com.meshdesh.trifler.signup.viewModel.SignupViewModelImpl
import com.meshdesh.trifler.util.setGone
import com.meshdesh.trifler.util.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    companion object {
        fun getInstance(context: Context) = Intent(context, SignupActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
    }

    private val viewModel: SignupViewModelImpl by viewModels()
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.signupStatusLiveData.observe(this, ::signupStatus)

        setTermsAndConditions()
        setHeader()
        setSignupButton()
    }

    private fun setSignupButton() {
        binding.signUpEmailButton.setOnClickListener {
            val firstName = binding.firstName.text?.toString() ?: ""
            val lastName = binding.lastName.text?.toString() ?: ""
            val email = binding.email.text?.toString() ?: ""
            val password = binding.password.text?.toString() ?: ""
            val conditionCheck = binding.termsCheckbox.isChecked
            val phoneNumber = binding.phone.text?.toString() ?: ""

            binding.firstNameTextLayout.isErrorEnabled = false
            binding.lastNameTextLayout.isErrorEnabled = false
            binding.emailTextLayout.isErrorEnabled = false
            binding.passwordTextLayout.isErrorEnabled = false
            binding.termsCheckbox.error = null

            viewModel.signup(firstName, lastName, email, password, conditionCheck, phoneNumber)
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

        binding.termsCheckbox.text = spannableString
    }

    private fun setHeader() {
        binding.header.setButtonClickListener {
            openSignin()
        }
        binding.header.setSecondaryButtonClickListener {
            openSignin()
        }
    }

    private fun openSignin() {
        startActivity(LoginActivity.getInstance(this))
        finish()
    }

    private fun signupStatus(status: SignupViewModel.SignupStatus) {
        when (status) {
            is SignupViewModel.SignupStatus.Loading -> {
                binding.progress.setVisible()
            }

            is SignupViewModel.SignupStatus.Failure -> {
                binding.progress.setGone()
                Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()
            }

            is SignupViewModel.SignupStatus.Success -> {
                binding.progress.setGone()
                Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()

                startActivity(LoginActivity.getInstance(this))
                finish()
            }

            is SignupViewModel.SignupStatus.Empty.FirstName -> {
                binding.progress.setGone()
                binding.firstNameTextLayout.isErrorEnabled = true
                binding.firstNameTextLayout.error = getString(R.string.signup_firstname_blank)
            }

            is SignupViewModel.SignupStatus.Empty.LastName -> {
                binding.progress.setGone()
                binding.lastNameTextLayout.isErrorEnabled = true
                binding.lastNameTextLayout.error = getString(R.string.signup_lastname_blank)
            }

            is SignupViewModel.SignupStatus.Empty.Email -> {
                binding.progress.setGone()
                binding.emailTextLayout.isErrorEnabled = true
                binding.emailTextLayout.error = getString(R.string.signup_email_blank)
            }

            is SignupViewModel.SignupStatus.Empty.Password -> {
                binding.progress.setGone()
                binding.passwordTextLayout.isErrorEnabled = true
                binding.passwordTextLayout.error = getString(R.string.signup_password_blank)
            }

            is SignupViewModel.SignupStatus.Empty.Condition -> {
                binding.progress.setGone()
                binding.termsCheckbox.error =
                    getString(R.string.signup_termsandconditions_unchecked)
            }

            is SignupViewModel.SignupStatus.Empty.Phone -> {
                binding.progress.setGone()
                binding.phoneTextLayout.isErrorEnabled = true
                binding.phoneTextLayout.error = getString(R.string.signup_phone_number_blank)
            }
        }
    }
}