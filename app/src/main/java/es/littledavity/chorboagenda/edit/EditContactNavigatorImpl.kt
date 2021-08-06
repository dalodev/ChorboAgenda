package es.littledavity.chorboagenda.edit

import androidx.navigation.NavController
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.features.add.AddContactFragmentDirections
import es.littledavity.features.edit.EditContactNavigator
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
class EditContactNavigatorImpl @Inject constructor(
    private val navController: NavController,
) : EditContactNavigator {
    override fun goBack() {
        navController.popBackStack()
    }

    override fun goList() {
        navController.navigate(AddContactFragmentDirections.actionDashboardFragment())
    }
}