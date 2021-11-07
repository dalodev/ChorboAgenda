/*
 * Copyright 2021 dalodev
 */
package es.littledavity.commons.ui.extensions

import android.view.Window
import android.view.WindowManager

var Window.isBackgroundDimmingEnabled: Boolean
    set(value) {
        if (value) {
            addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        } else {
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }
    get() = attributes.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND ==
        WindowManager.LayoutParams.FLAG_DIM_BEHIND

fun Window.makeScreenAlwaysAwake() {
    addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

fun Window.makeScreenSleepable() {
    clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

fun Window.setScreenAlwaysAwake(isScreenAlwaysAwake: Boolean) {
    if (isScreenAlwaysAwake) {
        makeScreenAlwaysAwake()
    } else {
        makeScreenSleepable()
    }
}
