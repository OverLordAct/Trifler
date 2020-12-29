package com.meshdesh.trifler.dashboard.view

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.meshdesh.trifler.R
import kotlinx.android.synthetic.main.custom_dashboard_onboarding_card.view.*

class OnboardingCard(
    context: Context,
    attributeSet: AttributeSet
) : CardView(context, attributeSet) {

    private var cardListener: (() -> Unit)? = null

    init {
        inflate(context, R.layout.custom_dashboard_onboarding_card, this)

        context.obtainStyledAttributes(attributeSet, R.styleable.OnboardingCard).apply {
            setHeaderText(getString(R.styleable.OnboardingCard_headerText).toString())
            setBodyText(getString(R.styleable.OnboardingCard_bodyText).toString())
            recycle()
        }
    }

    fun setHeaderText(text: String) {
        headerText.text = text
    }

    fun setBodyText(text: String) {
        bodyText.text = text
    }

    fun setCardClickListener(listener: (() -> Unit)) {
        cardListener = listener
        this.setCardClickListener {
            cardListener?.invoke()
        }
    }
}