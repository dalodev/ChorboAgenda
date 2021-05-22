/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.extensions

import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

private const val DATA_SET_ANIMATION_DURATION = 500L
private val DATA_SET_ANIMATION_INTERPOLATOR = DecelerateInterpolator(1.5F)

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

fun View.fadeIn() {
    animate()
        .alpha(1f)
        .setDuration(DATA_SET_ANIMATION_DURATION)
        .setInterpolator(DATA_SET_ANIMATION_INTERPOLATOR)
        .start()
}

fun View.resetAnimation() {
    cancelActiveAnimations()
    alpha = 0f
}

fun View.cancelActiveAnimations() {
    clearAnimation()
    animate().cancel()
}

fun String?.orEmpty() = this ?: ""
