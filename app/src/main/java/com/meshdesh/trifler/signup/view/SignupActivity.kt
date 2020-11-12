package com.meshdesh.trifler.signup.view

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.meshdesh.trifler.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_signup.*

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setTermsAndConditions()
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
}