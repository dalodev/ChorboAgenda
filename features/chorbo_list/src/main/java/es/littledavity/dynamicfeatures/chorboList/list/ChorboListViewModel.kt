/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.list

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.livedata.SingleLiveData
import es.littledavity.core.database.DatabaseState
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.dynamicfeatures.chorboList.list.paging.ChorboPageDataSourceFactory
import es.littledavity.dynamicfeatures.chorboList.list.paging.PAGE_MAX_ELEMENTS
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [ChorboListFragment].
 *
 * @see ViewModel
 */
class ChorboListViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val dataSourceFactory: ChorboPageDataSourceFactory,
    private val chorboRepository: ChorboRepository,
    private val imageGalleryService: ImageGalleryService
) : BaseViewModel() {

    @VisibleForTesting(otherwise = PRIVATE)
    val databaseState = Transformations.switchMap(dataSourceFactory.sourceLiveData) {
        it.databaseState
    }

    val event = SingleLiveData<ChorboListViewEvent>()
    val data = LivePagedListBuilder(dataSourceFactory, PAGE_MAX_ELEMENTS).build()
    private val _viewState = MutableLiveData<ChorboListViewState>()
    val viewState: LiveData<ChorboListViewState>
        get() = _viewState
    val state = Transformations.map(databaseState) {
        when (it) {
            is DatabaseState.Success ->
                if (it.isAdditional && it.isEmptyResponse) {
                    ChorboListViewState.NoMoreElements
                } else if (it.isEmptyResponse) {
                    ChorboListViewState.Empty
                } else {
                    ChorboListViewState.Loaded
                }
            is DatabaseState.Loading ->
                if (it.isAdditional) {
                    ChorboListViewState.AddLoading
                } else {
                    ChorboListViewState.Loading
                }
            is DatabaseState.Error ->
                if (it.isAdditional) {
                    ChorboListViewState.AddError
                } else {
                    ChorboListViewState.Error
                }
        }
    }

    // ============================================================================================
    //  Public methods
    // ============================================================================================

    /**
     * Refresh chorbo fetch them again and update the list.
     */
    fun refreshLoadedChorboList() = dataSourceFactory.refresh()

    /**
     * Retry last fetch operation to add chorbo into list.
     */
    fun retryAddChorbosList() = dataSourceFactory.retry()

    /**
     * Send interaction event for open chorbo detail view from selected chorbo.
     *
     * @param chorboId chorbo identifier.
     */
    fun openChorboDetail(chorboId: Long) = navigate(ChorboListFragmentDirections.toDetail(chorboId))

    /**
     * Send interaction event for open chorbo options.
     *
     */
    fun openAddChorboOptions() = event.postValue(ChorboListViewEvent.OpenChorboOptions)

    /**
     * Get file from path
     *
     * @param imagePath path of image
     */
    fun getImageFile(imagePath: String) = File(imagePath)
}
