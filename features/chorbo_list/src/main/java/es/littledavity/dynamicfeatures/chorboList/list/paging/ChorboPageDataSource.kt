/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.list.paging

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import es.littledavity.core.database.DatabaseState
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.dynamicfeatures.chorboList.list.model.ChorboItem
import es.littledavity.dynamicfeatures.chorboList.list.model.ChorboItemMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PAGE_INIT_ELEMENTS = 0
const val PAGE_MAX_ELEMENTS = 20

/**
 * Incremental data loader for page-keyed content, where requests return keys for next/previous
 * pages. Obtaining paginated the chorbos.
 *
 * @see PageKeyedDataSource
 */
open class ChorboPageDataSource @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val repository: ChorboRepository,
    @VisibleForTesting(otherwise = PRIVATE)
    val scope: CoroutineScope,
    @VisibleForTesting(otherwise = PRIVATE)
    val mapper: ChorboItemMapper
) : PageKeyedDataSource<Int, ChorboItem>() {

    val databaseState = MutableLiveData<DatabaseState>()

    @VisibleForTesting(otherwise = PRIVATE)
    var retry: (() -> Unit)? = null

    /**
     * Load initial data.
     *
     * @param params Parameters for initial load, including requested load size.
     * @param callback Callback that receives initial load data.
     * @see PageKeyedDataSource.loadInitial
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ChorboItem>
    ) {
        databaseState.postValue(DatabaseState.Loading())
        scope.launch(
            CoroutineExceptionHandler { _, _ ->
                retry = {
                    loadInitial(params, callback)
                }
                databaseState.postValue(DatabaseState.Error())
            }
        ) {
            val response = repository.getChorbos(
                offset = PAGE_INIT_ELEMENTS,
                limit = PAGE_MAX_ELEMENTS
            )
            val data = mapper.map(response)
            callback.onResult(data, null, PAGE_MAX_ELEMENTS)
            databaseState.postValue(DatabaseState.Success(isEmptyResponse = data.isEmpty()))
        }
    }

    /**
     * Append page with the key specified by [LoadParams.key].
     *
     * @param params Parameters for the load, including the key for the new page, and requested
     * load size.
     * @param callback Callback that receives loaded data.
     * @see PageKeyedDataSource.loadAfter
     */
    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ChorboItem>
    ) {
        databaseState.postValue(DatabaseState.Loading(true))
        scope.launch(
            CoroutineExceptionHandler { _, _ ->
                retry = {
                    loadAfter(params, callback)
                }
                databaseState.postValue(DatabaseState.Error(true))
            }
        ) {
            val response = repository.getChorbos(
                offset = params.key,
                limit = PAGE_MAX_ELEMENTS
            )
            val data = mapper.map(response)
            callback.onResult(data, params.key + PAGE_MAX_ELEMENTS)
            databaseState.postValue(DatabaseState.Success(true, data.isEmpty()))
        }
    }

    /**
     * Prepend page with the key specified by [LoadParams.key]
     *
     * @param params Parameters for the load, including the key for the new page, and requested
     * load size.
     * @param callback Callback that receives loaded data.
     * @see PageKeyedDataSource.loadBefore
     */
    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ChorboItem>
    ) = Unit // Ignored, since we only ever append to our initial load

    /**
     * Force retry last fetch operation in case it has ever been previously executed.
     */
    fun retry() = retry?.invoke()
}
