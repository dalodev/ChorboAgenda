/*
 * Copyright 2020 littledavity
 */
package es.littledavity.commons.ui.extensions

import android.content.Context
import androidx.annotation.StringRes

/**
 * Get resource string from optional id
 *
 * @param resId Resource string identifier.
 * @return The key value if exist, otherwise empty.
 */
fun Context.getStringOrEmpty(@StringRes resId: Int?) =
    resId?.let {
        getString(it)
    } ?: run {
        ""
    }

fun Boolean?.orFalse() = this ?: false
