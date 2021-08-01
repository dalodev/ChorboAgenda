/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.extensions

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.Window
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

@get:ColorInt
var Fragment.statusBarColor: Int
    set(@ColorInt value) { requireActivity().statusBarColor = value }
    get() = requireActivity().statusBarColor

@get:ColorInt
var Fragment.navigationBarColor: Int
    set(@ColorInt value) { requireActivity().navigationBarColor = value }
    get() = requireActivity().navigationBarColor

val Fragment.window: Window
    get() = requireActivity().window

fun Fragment.getColor(@ColorRes colorId: Int): Int {
    return requireContext().getCompatColor(colorId)
}

fun Fragment.getDimensionPixelSize(@DimenRes dimenId: Int): Int {
    return requireContext().getDimensionPixelSize(dimenId)
}

fun Fragment.getInteger(@IntegerRes intId: Int): Int {
    return requireContext().getInteger(intId)
}

fun Fragment.getDimension(@DimenRes dimenId: Int): Float {
    return requireContext().getDimension(dimenId)
}

fun Fragment.getFloat(@IntegerRes floatId: Int): Float {
    return requireContext().getFloat(floatId)
}

fun Fragment.getFont(@FontRes fontId: Int): Typeface? {
    return requireContext().getFont(fontId)
}

fun Fragment.getDrawable(@DrawableRes drawableId: Int): Drawable? {
    return requireContext().getCompatDrawable(drawableId)
}

fun Fragment.getColoredDrawable(@DrawableRes drawableId: Int, @ColorInt color: Int): Drawable? {
    return requireContext().getColoredDrawable(drawableId, color)
}

fun Fragment.getColoredStrokeDrawable(
    @DrawableRes drawableId: Int,
    @ColorInt strokeColor: Int,
    strokeWidth: Int
): Drawable? {
    return requireContext().getCompatDrawable(drawableId)
        ?.run { mutate() as? GradientDrawable }
        ?.apply { setStroke(strokeWidth, strokeColor) }
}

fun Fragment.showShortToast(message: CharSequence): Toast {
    return requireContext().showShortToast(message)
}

fun Fragment.showLongToast(message: CharSequence): Toast {
    return requireContext().showLongToast(message)
}

fun Fragment.isPermissionGranted(permission: String): Boolean {
    return requireContext().isPermissionGranted(permission)
}

fun Fragment.isPermissionDenied(permission: String): Boolean {
    return requireContext().isPermissionDenied(permission)
}

fun Fragment.arePermissionsGranted(permissions: Set<String>): Boolean {
    return requireContext().arePermissionsGranted(permissions)
}

fun Fragment.arePermissionsDenied(permissions: Set<String>): Boolean {
    return requireContext().arePermissionsDenied(permissions)
}

fun Fragment.makeScreenAlwaysAwake() {
    requireActivity().makeScreenAlwaysAwake()
}

fun Fragment.makeScreenSleepable() {
    requireActivity().makeScreenSleepable()
}

fun Fragment.setScreenAlwaysAwake(isScreenAlwaysAwake: Boolean) {
    requireActivity().setScreenAlwaysAwake(isScreenAlwaysAwake)
}

fun Fragment.setSoftInputMode(mode: Int) {
    requireActivity().setSoftInputMode(mode)
}

fun Fragment.addOnBackPressCallback(
    onBackPressed: OnBackPressedCallback.() -> Unit
): OnBackPressedCallback {
    return requireActivity()
        .onBackPressedDispatcher
        .addCallback(viewLifecycleOwner, onBackPressed = onBackPressed)
}

val Fragment.navController: NavController
    get() = findNavController()
