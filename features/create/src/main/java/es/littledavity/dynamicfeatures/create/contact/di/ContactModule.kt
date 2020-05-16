/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.contact.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import dagger.Module
import dagger.Provides
import es.littledavity.commons.ui.extensions.viewModel
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.dynamicfeatures.create.contact.ContactFragment
import es.littledavity.dynamicfeatures.create.contact.ContactViewModel

/**
 * Class that contributes to the object graph [CotnactComponent].
 *
 * @see Module
 */
@Module
class ContactModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: ContactFragment
) {

    /**
     * Create a provider method binding for [ContactViewModel].
     *
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesCreateViewModel(
        chorboRepository: ChorboRepository,
        imageGalleryService: ImageGalleryService
    ) = fragment.viewModel {
        ContactViewModel(chorboRepository, imageGalleryService)
    }
}
