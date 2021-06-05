package es.littledavity.chorboagenda.info

import androidx.navigation.NavController
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.features.info.ContactInfoFragmentDirections
import es.littledavity.features.info.ContactInfoNavigator
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
internal class ContactInfoNavigatorImpl @Inject constructor(
    private val navController: NavController
) : ContactInfoNavigator {

    override fun goToImageViewer(title: String?, initialPosition: Int, imageUrls: List<String>) {
        navController.navigate(
            ContactInfoFragmentDirections.actionImageViewerFragment(
                title,
                initialPosition,
                imageUrls.toTypedArray()
            )
        )
    }

    override fun goToInfo(contactId: Int) {
        navController.navigate((ContactInfoFragmentDirections.actionInfoFragment(contactId)))
    }

    override fun goBack() {
        navController.popBackStack()
    }
}