package es.littledavity.commons.ui.extensions

import android.app.Activity
import com.paulrybitskyi.commons.window.anims.WindowAnimations

/**
 * Overrides the animations of the entering window.
 *
 * @param windowAnimations The animations to use for the entering window
 */
fun Activity.overrideEnterTransition(windowAnimations: WindowAnimations) {
    if(windowAnimations.id == WindowAnimations.DEFAULT_ANIMATIONS.id) {
        return
    }

    overridePendingTransition(
        windowAnimations.windowBEnterAnimation,
        windowAnimations.windowAExitAnimation
    )
}