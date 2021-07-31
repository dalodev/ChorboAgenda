/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.extensions

import android.content.Context
import es.littledavity.commons.ui.R

fun Context.defaultWindowAnimationDuration() : Long {
    return resources.getInteger(R.integer.default_window_animation_duration).toLong()
}
