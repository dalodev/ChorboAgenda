/*
 * Copyright 2021 dev.id
 */
package es.littledavity.chorboagenda.likes

import androidx.navigation.NavController
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.features.dashboard.fragment.DashboardFragmentDirections
import es.littledavity.features.likes.LikedContactsNavigator
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
internal class LikedContactsNavigatorImpl @Inject constructor(
    private val navController: NavController
) : LikedContactsNavigator {

    override fun goToInfo(contactId: Int) {
        navController.navigate(DashboardFragmentDirections.actionInfoFragment(contactId))
    }
}
