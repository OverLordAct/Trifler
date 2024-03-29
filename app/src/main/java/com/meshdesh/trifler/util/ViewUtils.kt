package com.meshdesh.trifler.util

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.getHeightHelper(): Float {
    measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    val height = measuredHeight
    return height.toFloat()
}

fun View.setVisible() {
    if (this.visibility == View.VISIBLE) return
    this.visibility = View.VISIBLE
}

fun View.setGone() {
    if (this.visibility == View.GONE) return
    this.visibility = View.GONE
}

fun View.setInvisible() {
    if (this.visibility == View.INVISIBLE) return
    this.visibility = View.INVISIBLE
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}