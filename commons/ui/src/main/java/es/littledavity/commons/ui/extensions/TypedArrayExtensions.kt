/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.StyleableRes
import androidx.core.content.res.ResourcesCompat
import es.littledavity.core.commons.SdkInfo

fun TypedArray.getString(@StyleableRes index: Int, default: CharSequence = ""): CharSequence {
    return (getString(index) ?: default)
}

fun TypedArray.getDrawable(@StyleableRes index: Int, default: Drawable? = null): Drawable? {
    return (getDrawable(index) ?: default)
}

@SuppressLint("NewApi")
fun TypedArray.getFont(
    context: Context,
    @StyleableRes index: Int,
    default: Typeface
): Typeface {
    return if(SdkInfo.IS_AT_LEAST_OREO) {
        (getFont(index) ?: default)
    } else {
        getResourceId(index, -1)
            .takeIf { it != -1 }
            ?.let { ResourcesCompat.getFont(context, it) }
            ?: default
    }
}

fun TypedArray.getColorStateList(@StyleableRes index: Int, default: ColorStateList): ColorStateList {
    return (getColorStateList(index) ?: default)
}
