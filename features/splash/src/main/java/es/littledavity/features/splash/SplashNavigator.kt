/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.splash

import android.content.Context
import es.littledavity.commons.ui.base.navigation.Navigator

interface SplashNavigator : Navigator {

    fun goToDashBoard(context: Context)
}
