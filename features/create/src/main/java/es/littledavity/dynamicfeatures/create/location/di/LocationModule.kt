package es.littledavity.dynamicfeatures.create.location.di

import androidx.annotation.VisibleForTesting
import dagger.Module
import dagger.Provides
import es.littledavity.commons.ui.extensions.viewModel
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.create.location.LocationFragment
import es.littledavity.dynamicfeatures.create.location.LocationViewModel
import es.littledavity.dynamicfeatures.create.name.di.NameComponent

/**
 * Class that contributes to the object graph [NameComponent].
 *
 * @see Module
 */
@Module
class LocationModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment: LocationFragment
) {

    /**
     * Create a provider method binding for [LocationViewModel].
     *
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesLocationViewModel() = fragment.viewModel {
        LocationViewModel()
    }
}
