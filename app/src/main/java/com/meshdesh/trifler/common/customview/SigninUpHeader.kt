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

    var buttonClicked: (() -> Unit)? = null

    init {
        inflate(context, R.layout.custom_signin_signup_header, this)

        context.obtainStyledAttributes(attr, R.styleable.SigninUpHeader).apply {
            setButtonText(getString(R.styleable.SigninUpHeader_buttonText).toString())
            setTitle(getString(R.styleable.SigninUpHeader_titleText).toString())
            setSubtitlePrimary(getString(R.styleable.SigninUpHeader_subtitlePrimaryText).toString())
            setSubtitleSecondary(getString(R.styleable.SigninUpHeader_subtitleSecondaryText).toString())
            buttonClicked?.let { setButtonClickListener(it) }
            recycle()
        }
    }

    fun setButtonText(text: String) {
        navButton.text = text
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
        buttonClicked = listener
        navButton.setOnClickListener {
            buttonClicked?.invoke()
        }
    }
}