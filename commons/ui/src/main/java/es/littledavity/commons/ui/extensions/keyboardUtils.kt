/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.extensions

import android.view.View
import android.view.inputmethod.InputMethodManager

private const val KEYBOARD_SHOWING_DELAY = 300L

fun View.showKeyboard(withDelay: Boolean = false) {
    requestFocus()

    val action: (() -> Unit) = {
        context.getSystemService<InputMethodManager>().showSoftInput(this, 0)
    }

    if (withDelay) {
        postActionDelayed(KEYBOARD_SHOWING_DELAY, action)
    } else {
        action()
    }
}

fun View.hideKeyboard() {
    clearFocus()

    context.getSystemService<InputMethodManager>().hideSoftInputFromWindow(windowToken, 0)
}
