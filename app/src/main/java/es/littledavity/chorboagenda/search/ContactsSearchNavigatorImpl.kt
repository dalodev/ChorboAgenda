package es.littledavity.chorboagenda.search

import androidx.navigation.NavController
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.features.dashboard.fragment.DashboardFragmentDirections
import es.littledavity.features.search.ContactsSearchNavigator
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
internal class ContactsSearchNavigatorImpl @Inject constructor(
    private val navController: NavController
) : ContactsSearchNavigator {

    override fun goToInfo(contactId: Int) {
        navController.navigate(DashboardFragmentDirections.actionInfoFragment(contactId))
    }

    override fun goBack() {
        navController.popBackStack()
    }
}