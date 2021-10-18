/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.extensions

import android.widget.ImageView

private const val STATE_CHECKED = android.R.attr.state_checked
private const val STATE_CHECKED_ON = STATE_CHECKED * 1
private const val STATE_CHECKED_OFF = STATE_CHECKED * -1

var ImageView.isChecked: Boolean
    set(value) { setImageState(intArrayOf(if (value) STATE_CHECKED_ON else STATE_CHECKED_OFF), true) }
    get() = drawableState.contains(STATE_CHECKED_ON)
