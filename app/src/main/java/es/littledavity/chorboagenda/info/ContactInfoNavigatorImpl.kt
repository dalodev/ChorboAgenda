/*
 * Copyright 2021 dev.id
 */
package es.littledavity.chorboagenda.info

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.navigation.NavController
import com.paulrybitskyi.hiltbinder.BindType
import dagger.hilt.android.qualifiers.ApplicationContext
import es.littledavity.features.info.ContactInfoFragmentDirections
import es.littledavity.features.info.ContactInfoNavigator
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
internal class ContactInfoNavigatorImpl @Inject constructor(
    private val navController: NavController,
    @ApplicationContext private val context: Context
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
        navController.navigateUp()
    }

    override fun goSettingsApp() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
}
