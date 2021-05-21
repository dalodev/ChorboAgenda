package es.littledavity.commons.ui.extensions

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.paulrybitskyi.commons.ktx.postActionDelayed

private const val SWIPE_REFRESH_ANIM_DURATION = 200L

fun SwipeRefreshLayout.disableAfterAnimationEnds() {
    postActionDelayed(SWIPE_REFRESH_ANIM_DURATION) {
        isEnabled = false
    }
}