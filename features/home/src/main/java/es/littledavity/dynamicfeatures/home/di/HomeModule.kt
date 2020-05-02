package es.littledavity.dynamicfeatures.home.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.home.HomeFragment
import es.littledavity.dynamicfeatures.home.HomeViewModel
import es.littledavity.commons.ui.extensions.viewModel
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.dynamicfeatures.home.adapter.ChorbosListAdapter
import es.littledavity.dynamicfeatures.home.model.ChorboItemMapper
import es.littledavity.dynamicfeatures.home.paging.ChorboPageDataSource
import es.littledavity.dynamicfeatures.home.paging.ChorboPageDataSourceFactory

/**
 * Class that contributes to the object graph [HomeComponent].
 *
 * @see Module
 */
@Module
class HomeModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: HomeFragment
) {

    /**
     * Create a provider method binding for [HomeViewModel].
     *
     * @param dataFactory Data source factory for chorbos.
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesHomeViewModel(
        dataFactory: ChorboPageDataSourceFactory
    ) = fragment.viewModel {
        HomeViewModel(dataFactory)
    }

    /**
     * Create a provider method binding for [ChorboPageDataSource].
     *
     * @return Instance of data source.
     * @see Provides
     */
    @Provides
    fun providesChorboPageDataSource(
        viewModel: HomeViewModel,
        repository: ChorboRepository,
        mapper: ChorboItemMapper
    ) = ChorboPageDataSource(
        repository = repository,
        scope = viewModel.viewModelScope,
        mapper = mapper
    )

    /**
     * Create a provider method binding for [ChorboItemMapper].
     *
     * @return Instance of mapper.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesChorboItemMapper() = ChorboItemMapper()

    /**
     * Create a provider method binding for [ChorbosListAdapter].
     *
     * @return Instance of adapter.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesChorboListAdapter(
        viewModel: HomeViewModel
    ) = ChorbosListAdapter(viewModel)
}