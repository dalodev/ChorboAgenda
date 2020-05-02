package es.littledavity.dynamicfeatures.home

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import es.littledavity.commons.ui.livedata.SingleLiveData
import es.littledavity.core.database.DatabaseState
import es.littledavity.dynamicfeatures.home.paging.ChorboPageDataSourceFactory
import es.littledavity.dynamicfeatures.home.paging.PAGE_MAX_ELEMENTS
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [HomeFragment].
 *
 * @see ViewModel
 */
class HomeViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val dataSourceFactory: ChorboPageDataSourceFactory
) : ViewModel() {

    @VisibleForTesting(otherwise = PRIVATE)
    val databaseState = Transformations.switchMap(dataSourceFactory.sourceLiveData) {
        it.databaseState
    }


    val event = SingleLiveData<ChorboListViewEvent>()
    val data = LivePagedListBuilder(dataSourceFactory, PAGE_MAX_ELEMENTS).build()
    val state = Transformations.map(databaseState) {
        when (it) {
            is DatabaseState.Success ->
                if (it.isAdditional && it.isEmptyResponse) {
                    HomeViewState.NoMoreElements
                } else if (it.isEmptyResponse) {
                    HomeViewState.Empty
                } else {
                    HomeViewState.Loaded
                }
            is DatabaseState.Loading ->
                if (it.isAdditional) {
                    HomeViewState.AddLoading
                } else {
                    HomeViewState.Loading
                }
            is DatabaseState.Error ->
                if (it.isAdditional) {
                    HomeViewState.AddError
                } else {
                    HomeViewState.Error
                }
        }
    }

    // ============================================================================================
    //  Public methods
    // ============================================================================================

    /**
     * Refresh chorbo fetch them again and update the list.
     */
    fun refreshLoadedChorbosList() {
        dataSourceFactory.refresh()
    }

    /**
     * Retry last fetch operation to add chorbo into list.
     */
    fun retryAddChorbosList() {
        dataSourceFactory.retry()
    }

    /**
     * Send interaction event for open chorbo detail view from selected chorbo.
     *
     * @param chorboId chorbo identifier.
     */
    fun openChorboDetail(chorboId: Long) {
        event.postValue(ChorboListViewEvent.OpenChorboDetail(chorboId))
    }

    /**
     * Send interaction event for open chorbo options.
     *
     */
    fun openAddChorboOptions() {
        event.postValue(ChorboListViewEvent.OpenChorboOptions)
    }

}