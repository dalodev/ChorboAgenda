/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject

/**
 * Utils implementation for application theme configuration.
 * @see ThemeUtils
 */
class ThemeUtilsImpl @Inject constructor() : ThemeUtils {

    /**
     * @see ThemeUtils.isDarkTheme
     */
    override fun isDarkTheme(context: Context) =
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    /**
     * @see ThemeUtils.isLightTheme
     */
    override fun isLightTheme(context: Context) = !isDarkTheme(context)

    /**
     * @see ThemeUtils.setNightMode
     */
    override fun setNightMode(forceNight: Boolean, delay: Long) {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                AppCompatDelegate.setDefaultNightMode(
                    if (forceNight) {
                        AppCompatDelegate.MODE_NIGHT_YES
                    } else {
                        AppCompatDelegate.MODE_NIGHT_NO
                    }
                )
            },
            delay
        )
    }
}
