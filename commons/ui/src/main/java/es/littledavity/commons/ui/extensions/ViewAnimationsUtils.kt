package es.littledavity.commons.ui.extensions

import android.view.View
import android.view.animation.DecelerateInterpolator

private const val DATA_SET_ANIMATION_DURATION = 500L
private val DATA_SET_ANIMATION_INTERPOLATOR = DecelerateInterpolator(1.5F)


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