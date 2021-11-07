/*
 * Copyright 2021 dalodev
 */
package es.littledavity.chorboagenda.add

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.navigation.NavController
import com.paulrybitskyi.hiltbinder.BindType
import dagger.hilt.android.qualifiers.ApplicationContext
import es.littledavity.features.add.AddContactNavigator
import es.littledavity.features.edit.EditContactFragmentDirections
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
class AddContactNavigatorImpl @Inject constructor(
    private val navController: NavController,
    @ApplicationContext private val context: Context
) : AddContactNavigator {
    override fun goBack() {
        navController.popBackStack()
    }

    override fun goToInfo(contactId: Int) {
        navController.navigate(EditContactFragmentDirections.actionInfoFragment(contactId))
    }

    override fun goSettingsApp() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
}
