package com.meshdesh.trifler.common.customview

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.meshdesh.trifler.R
import kotlinx.android.synthetic.main.custom_signin_signup_header.view.*

class SigninUpHeader(
    context: Context,
    attr: AttributeSet
) : ConstraintLayout(context, attr) {

    private var primaryButtonClicked: (() -> Unit)? = null
    private var secondaryButtonClicked: (() -> Unit)? = null

    init {
        inflate(context, R.layout.custom_signin_signup_header, this)

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
        primaryButton.text = text
    }

    fun setTitle(text: String) {
        title.text = text
    }

    fun setSubtitlePrimary(text: String) {
        subtitle_primary.text = text
    }

    fun setSubtitleSecondary(text: String) {
        subtitle_secondary.text = text
    }

    fun setButtonClickListener(listener: (() -> Unit)) {
        primaryButtonClicked = listener
        primaryButton.setOnClickListener {
            primaryButtonClicked?.invoke()
        }
    }

    fun secondaryButtonClickListener(listener: (() -> Unit)) {
        secondaryButtonClicked = listener
        subtitle_secondary.setOnClickListener {
            secondaryButtonClicked?.invoke()
        }
    }
}