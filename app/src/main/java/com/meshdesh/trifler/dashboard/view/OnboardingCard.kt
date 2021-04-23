package com.meshdesh.trifler.dashboard.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.meshdesh.trifler.R
import com.meshdesh.trifler.databinding.CustomDashboardOnboardingCardBinding

class OnboardingCard(
    context: Context,
    attributeSet: AttributeSet
) : MaterialCardView(context, attributeSet) {

    private var cardListener: (() -> Unit)? = null
    private var binding =
        CustomDashboardOnboardingCardBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.OnboardingCard).apply {
            setHeaderText(getString(R.styleable.OnboardingCard_headerText).toString())
            setBodyText(getString(R.styleable.OnboardingCard_bodyText).toString())
            recycle()
        }
    }

    fun setHeaderText(text: String) {
        binding.headerText.text = text
    }

    fun setBodyText(text: String) {
        binding.bodyText.text = text
    }

    fun setCardClickListener(listener: (() -> Unit)) {
        cardListener = listener
        setOnClickListener {
            cardListener?.invoke()
        }
    }
}