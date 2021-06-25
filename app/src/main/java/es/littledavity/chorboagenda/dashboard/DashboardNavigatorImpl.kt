/*
 * Copyright 2021 dev.id
 */
package es.littledavity.chorboagenda.dashboard

import androidx.navigation.NavController
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.features.dashboard.fragment.DashBoardNavigator
import es.littledavity.features.dashboard.fragment.DashboardFragmentDirections
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
internal class DashboardNavigatorImpl @Inject constructor(
    private val navController: NavController
) : DashBoardNavigator {

    override fun goToSearch() {
        navController.navigate(DashboardFragmentDirections.actionSearchFragment())
    }

    override fun goToAddContact() {
        navController.navigate(DashboardFragmentDirections.actionAddContactFragment())
    }
}
