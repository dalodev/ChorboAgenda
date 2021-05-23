/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.extensions

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

private const val SWIPE_REFRESH_ANIM_DURATION = 200L

fun SwipeRefreshLayout.disableAfterAnimationEnds() {
    postActionDelayed(SWIPE_REFRESH_ANIM_DURATION) {
        isEnabled = false
    }
}
