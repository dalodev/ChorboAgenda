package es.littledavity.dynamicfeatures.chorboList.detail.di

import android.content.Context
import androidx.annotation.VisibleForTesting
import dagger.Module
import dagger.Provides
import es.littledavity.commons.ui.extensions.viewModel
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailFragment
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailViewModel
import es.littledavity.dynamicfeatures.chorboList.detail.adapter.ChorboDetailAdapter
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailMapper
import es.littledavity.dynamicfeatures.chorboList.list.di.ChorboListComponent
import es.littledavity.dynamicfeatures.chorboList.list.model.ChorboItemMapper

/**
 * Class that contributes to the object graph [ChorboListComponent].
 *
 * @see Module
 */
@Module
class ChorboDetailModule(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fragment: ChorboDetailFragment
) {

    /**
     * Create a provider method binding for [ChorboDetailViewModel].
     *
     * @param chorboRepository
     * @param chorboDetailMapper mapper for chorbo detail item
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesChorboDetailViewModel(
        chorboRepository: ChorboRepository,
        chorboDetailMapper: ChorboDetailMapper
    ) = fragment.viewModel {
        ChorboDetailViewModel(chorboRepository, chorboDetailMapper)
    }

    /**
     * Create a provider method binding for [ChorboItemMapper].
     *
     * @return Instance of mapper.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesChorboDetailMapper(context: Context) = ChorboDetailMapper(context)


    /**
     * Create a provider method binding for [ChorboDetailAdapter].
     *
     * @return Instance of adapter.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesChorboDetailAdapter(
        viewModel: ChorboDetailViewModel
    ) = ChorboDetailAdapter(
        viewModel
    )
}
