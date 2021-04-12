package com.target.targetcasestudy.util

import android.app.Activity
import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.updateVisibility(show: Boolean) {
    visibility = if(show) View.VISIBLE else View.GONE
}

fun Activity.showSnackBar(message: String, action: String? = null, duration: Int = Snackbar.LENGTH_LONG, actionListener: ((View) -> Unit)? = null) {
    val snackBar = Snackbar.make(findViewById(android.R.id.content), message, duration)
        .setTextColor(Color.WHITE)
    if (action != null && actionListener!=null) {
        snackBar.setAction(action, actionListener)
    }
    snackBar.show()
}