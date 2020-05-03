package es.littledavity.dynamicfeatures.create.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import dagger.Module
import dagger.Provides
import es.littledavity.commons.ui.extensions.viewModel
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.create.CreateFragment
import es.littledavity.dynamicfeatures.create.CreateViewModel

/**
 * Class that contributes to the object graph [ChorboListComponent].
 *
 * @see Module
 */
@Module
class CreateModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: CreateFragment
) {

    /**
     * Create a provider method binding for [CreateViewModel].
     *
     * @param chorboRepository Data source factory for chorbos.
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesCreateViewModel(
        chorboRepository: ChorboRepository
    ) = fragment.viewModel {
        CreateViewModel(chorboRepository)
    }
}
