package es.littledavity.chorboagenda.add

import androidx.navigation.NavController
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.features.add.AddContactNavigator
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
class AddContactNavigatorImpl @Inject constructor(
    private val navController: NavController
) : AddContactNavigator{
    override fun goBack() {
        navController.popBackStack()

    }
}