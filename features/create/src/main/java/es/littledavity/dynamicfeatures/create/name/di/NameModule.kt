package es.littledavity.dynamicfeatures.create.name.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import dagger.Module
import dagger.Provides
import es.littledavity.commons.ui.extensions.viewModel
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.create.name.NameFragment
import es.littledavity.dynamicfeatures.create.name.NameViewModel

/**
 * Class that contributes to the object graph [NameComponent].
 *
 * @see Module
 */
@Module
class NameModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: NameFragment
) {

    /**
     * Create a provider method binding for [NameViewModel].
     *
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesCreateViewModel() = fragment.viewModel {
        NameViewModel()
    }
}
