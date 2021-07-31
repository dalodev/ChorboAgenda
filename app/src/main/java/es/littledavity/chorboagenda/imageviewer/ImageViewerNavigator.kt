/*
 * Copyright 2021 dev.id
 */
package es.littledavity.chorboagenda.imageviewer

import androidx.navigation.NavController
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.features.image.viewer.ImageViewerNavigator
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
internal class ImageViewerNavigatorImol @Inject constructor(
    private val navController: NavController
) : ImageViewerNavigator {
    override fun goBack() {
        navController.popBackStack()
    }

}
