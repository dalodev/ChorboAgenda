/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.list.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import es.littledavity.commons.ui.extensions.viewModel
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.dynamicfeatures.chorboList.list.ChorboListFragment
import es.littledavity.dynamicfeatures.chorboList.list.ChorboListViewModel
import es.littledavity.dynamicfeatures.chorboList.list.adapter.ChorbosListAdapter
import es.littledavity.dynamicfeatures.chorboList.list.model.ChorboItemMapper
import es.littledavity.dynamicfeatures.chorboList.list.paging.ChorboPageDataSource
import es.littledavity.dynamicfeatures.chorboList.list.paging.ChorboPageDataSourceFactory

/**
 * Class that contributes to the object graph [ChorboListComponent].
 *
 * @see Module
 */
@Module
class ChorboListModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: ChorboListFragment
) {

    /**
     * Create a provider method binding for [ChorboListViewModel].
     *
     * @param dataFactory Data source factory for chorbos.
     * @return Instance of view model.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesChorboListViewModel(
        dataFactory: ChorboPageDataSourceFactory,
        chorboRepository: ChorboRepository,
        imageGalleryService: ImageGalleryService
    ) = fragment.viewModel {
        ChorboListViewModel(dataFactory, chorboRepository, imageGalleryService)
    }

    /**
     * Create a provider method binding for [ChorboPageDataSource].
     *
     * @return Instance of data source.
     * @see Provides
     */
    @Provides
    fun providesChorboPageDataSource(
        viewModel: ChorboListViewModel,
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
    fun providesChorboItemMapper() =
        ChorboItemMapper()

    /**
     * Create a provider method binding for [ChorbosListAdapter].
     *
     * @return Instance of adapter.
     * @see Provides
     */
    @FeatureScope
    @Provides
    fun providesChorboListAdapter(
        viewModel: ChorboListViewModel
    ) = ChorbosListAdapter(
        viewModel
    )
}
