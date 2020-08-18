/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.image.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import dagger.Module
import dagger.Provides
import es.littledavity.commons.ui.extensions.viewModel
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.core.service.PermissionService
import es.littledavity.dynamicfeatures.create.image.ImageFragment
import es.littledavity.dynamicfeatures.create.image.ImageViewModel

/**
 * Class that contributes to the object graph [ImageComponent].
 *
 * @see Module
 */
@Module
class ImageModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: ImageFragment
) {

    /**
     * Create a provider method binding for [ImageViewModel].
     *
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesImageViewModel(
        permissionService: PermissionService
    ) = fragment.viewModel {
        ImageViewModel(permissionService)
    }
}
