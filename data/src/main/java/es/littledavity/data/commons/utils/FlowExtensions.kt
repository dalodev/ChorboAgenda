package es.littledavity.data.commons.utils

import es.littledavity.core.utils.onFailure
import es.littledavity.core.utils.onSuccess
import es.littledavity.data.commons.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

internal fun <T> Flow<DataResult<T>>.onEachSuccess(action: suspend (T) -> Unit): Flow<DataResult<T>> {
    return onEach { it.onSuccess(action) }
}


internal fun <T> Flow<DataResult<T>>.onEachFailure(action: suspend (Error) -> Unit): Flow<DataResult<T>> {
    return onEach { it.onFailure(action) }
}