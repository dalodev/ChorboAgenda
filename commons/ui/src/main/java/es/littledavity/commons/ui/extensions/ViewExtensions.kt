/*
 * Copyright 2020 littledavity
 */
package es.littledavity.commons.ui.extensions

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * Get resource string from optional id
 *
 * @param resId Resource string identifier.
 * @return The key value if exist, otherwise empty.
 */
fun View.showTopSnackbar(@StringRes resId: Int?): Snackbar? =
    resId?.let {
        val snack = Snackbar.make(this, this.context.getString(resId), Snackbar.LENGTH_SHORT)
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.show()
        return snack
    } ?: run {
        null
    }
