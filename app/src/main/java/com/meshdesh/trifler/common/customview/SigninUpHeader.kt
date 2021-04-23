package com.meshdesh.trifler.common.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.meshdesh.trifler.R
import com.meshdesh.trifler.databinding.CustomSigninSignupHeaderBinding

class SigninUpHeader(
    context: Context,
    attr: AttributeSet
) : ConstraintLayout(context, attr) {

    private var primaryButtonClicked: (() -> Unit)? = null
    private var secondaryButtonClicked: (() -> Unit)? = null
    private var binding =
        CustomSigninSignupHeaderBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.obtainStyledAttributes(attr, R.styleable.SigninUpHeader).apply {
            setButtonText(getString(R.styleable.SigninUpHeader_buttonText).toString())
            setTitle(getString(R.styleable.SigninUpHeader_titleText).toString())
            setSubtitlePrimary(getString(R.styleable.SigninUpHeader_subtitlePrimaryText).toString())
            setSubtitleSecondary(getString(R.styleable.SigninUpHeader_subtitleSecondaryText).toString())
            primaryButtonClicked?.let { setButtonClickListener(it) }
            recycle()
        }
    }

    fun setButtonText(text: String) {
        binding.primaryButton.text = text
    }

    fun setTitle(text: String) {
        binding.title.text = text
    }

    fun setSubtitlePrimary(text: String) {
        binding.subtitlePrimary.text = text
    }

    fun setSubtitleSecondary(text: String) {
        binding.subtitleSecondary.text = text
    }

    fun setButtonClickListener(listener: (() -> Unit)) {
        primaryButtonClicked = listener
        binding.primaryButton.setOnClickListener {
            primaryButtonClicked?.invoke()
        }
    }

    fun setSecondaryButtonClickListener(listener: (() -> Unit)) {
        secondaryButtonClicked = listener
        binding.subtitleSecondary.setOnClickListener {
            secondaryButtonClicked?.invoke()
        }
    }
}