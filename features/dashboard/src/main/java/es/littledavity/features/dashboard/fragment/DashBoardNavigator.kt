/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.dashboard.fragment

import es.littledavity.commons.ui.base.navigation.Navigator

interface DashBoardNavigator : Navigator {
    fun goToSearch()
    fun goToAddContact()
}
