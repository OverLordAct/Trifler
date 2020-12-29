package com.meshdesh.trifler.signup.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.meshdesh.trifler.R
import com.meshdesh.trifler.sigin.view.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_signup.*

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    companion object {
        fun getInstance(context: Context) = Intent(context, SignupActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setTermsAndConditions()
        setHeader()
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
            startActivity(LoginActivity.getInstance(this))
        }
        header.setSecondaryButtonClickListener {
            startActivity(LoginActivity.getInstance(this))
        }
    }
}