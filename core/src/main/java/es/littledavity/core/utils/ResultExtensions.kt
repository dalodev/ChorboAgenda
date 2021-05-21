package es.littledavity.core.utils

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

fun <T> T.asSuccess(): Ok<T> = Ok(this)


fun <T> T.asFailure(): Err<T> = Err(this)


suspend fun <V, E> Result<V, E>.onSuccess(action: suspend (V) -> Unit): Result<V, E> {
    if(this is Ok) {
        action(value)
    }

    return this
}


suspend fun <V, E> Result<V, E>.onFailure(action: suspend (E) -> Unit): Result<V, E> {
    if(this is Err) {
        action(error)
    }

    return this
}
