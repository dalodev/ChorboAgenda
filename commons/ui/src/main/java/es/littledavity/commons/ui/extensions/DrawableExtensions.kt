/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.extensions

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

fun Drawable.setColor(@ColorInt color: Int): Drawable {
    return when(val newDrawable = mutate()) {
        is RippleDrawable -> newDrawable.apply { setColor(color.toColorStateList()) }
        is GradientDrawable -> newDrawable.apply { setColor(color) }

        else -> newDrawable.apply { colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN) }
    }
}


fun Drawable.setGradientColors(
    @ColorInt startColor: Int,
    @ColorInt endColor: Int
): Drawable {
    return if(this is GradientDrawable) {
        mutate().apply { colors = intArrayOf(startColor, endColor) }
    } else {
        this
    }
}


fun Drawable.setSize(size: Int) {
    applyBounds(
        left = 0,
        top = 0,
        right = size,
        bottom = size
    )
}


fun Drawable.applyBounds(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
): Drawable {
    return apply { setBounds(left, top, right, bottom) }
}


fun Drawable.setTintList(states: Array<IntArray>, colors: IntArray) {
    DrawableCompat.setTintList(
        DrawableCompat.wrap(this),
        ColorStateList(states, colors)
    )
}


fun Drawable.toBitmap(
    config: Bitmap.Config = Bitmap.Config.ARGB_8888
): Bitmap {
    return Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config)
        .also { bitmap ->
            val canvas = Canvas(bitmap)

            applyBounds(
                left = 0,
                top = 0,
                right = canvas.width,
                bottom = canvas.height
            )

            draw(canvas)
        }
}
