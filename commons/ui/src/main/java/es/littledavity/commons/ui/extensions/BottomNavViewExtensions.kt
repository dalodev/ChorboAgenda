/*
 * Copyright 2021 dalodev
 */
package es.littledavity.commons.ui.extensions

import android.R
import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setItemColors(
    @ColorInt unselectedStateColor: Int,
    @ColorInt selectedStateColor: Int
) {
    ColorStateList(
        arrayOf(
            intArrayOf(-R.attr.state_checked),
            intArrayOf()
        ),
        intArrayOf(
            unselectedStateColor,
            selectedStateColor
        )
    ).also {
        itemTextColor = it
        itemIconTintList = it
    }
}
