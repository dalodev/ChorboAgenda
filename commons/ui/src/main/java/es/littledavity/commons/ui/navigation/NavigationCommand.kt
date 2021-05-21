/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.navigation

import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator

sealed class NavigationCommand {
    data class To(val directions: NavDirections, val extras: FragmentNavigator.Extras? = null) : NavigationCommand()
    object Back : NavigationCommand()
    data class BackTo(val destinationId: Int) : NavigationCommand()
    object ToRoot : NavigationCommand()
}
