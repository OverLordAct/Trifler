package com.meshdesh.trifler.common.customview

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.meshdesh.trifler.R
import kotlinx.android.synthetic.main.custom_main_header.view.*

class MainHeader(
    context: Context,
    attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet) {

    private var logoClickListener: (() -> Unit)? = null
    private var notificationClickListener: (() -> Unit)? = null
    private var avatarClickListener: (() -> Unit)? = null

    init {
        inflate(context, R.layout.custom_main_header, this)
    }

    fun setNotificationIcon(image: Int) {
        // TODO Set notification icon
    }

    fun setAvatarIcon() {
        // TODO Set avatar icon
    }

    fun setlogoClickListener(listener: (() -> Unit)) {
        logoClickListener = listener
        logo.setOnClickListener {
            logoClickListener?.invoke()
        }
    }

    fun setNotificationClickListener(listener: (() -> Unit)) {
        notificationClickListener = listener
        notificationIcon.setOnClickListener {
            notificationClickListener?.invoke()
        }
    }

    fun setAvatarClickListener(listener: (() -> Unit)) {
        avatarClickListener = listener
        avatarIcon.setOnClickListener {
            avatarClickListener?.invoke()
        }
    }
}