/*
 * Copyright 2021 dalodev
 */
package es.littledavity.chorboagenda.contacts

import androidx.navigation.NavController
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.features.contacts.ContactsNavigator
import es.littledavity.features.dashboard.fragment.DashboardFragmentDirections
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
internal class ContactsNavigatorImpl @Inject constructor(
    private val navController: NavController
) : ContactsNavigator {

    override fun goToInfo(contactId: Int) {
        navController.navigate(DashboardFragmentDirections.actionInfoFragment(contactId))
    }
}
