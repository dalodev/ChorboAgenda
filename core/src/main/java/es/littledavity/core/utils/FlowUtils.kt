/*
 * Copyright 2021 dev.id
 */
package es.littledavity.core.utils

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import es.littledavity.domain.commons.DomainException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import java.io.Serializable
import es.littledavity.domain.commons.entities.Error

data class Tuple4<T1, T2, T3, T4>(
    val first: T1,
    val second: T2,
    val third: T3,
    val fourth: T4
) : Serializable {
    companion object {
        const val serialVersionUID = 1L
    }
}

data class Tuple5<T1, T2, T3, T4, T5>(
    val first: T1,
    val second: T2,
    val third: T3,
    val fourth: T4,
    val fifth: T5
) : Serializable {
    companion object {
        const val serialVersionUID = 1L
    }
}

fun <T1, T2> combine(
    f1: Flow<T1>,
    f2: Flow<T2>
): Flow<Pair<T1, T2>> = kotlinx.coroutines.flow.combine(f1, f2) { t1, t2 ->
    t1 to t2
}

fun <T1, T2, T3> combine(
    f1: Flow<T1>,
    f2: Flow<T2>,
    f3: Flow<T3>
): Flow<Triple<T1, T2, T3>> = kotlinx.coroutines.flow.combine(f1, f2, f3) { t1, t2, t3 ->
    Triple(t1, t2, t3)
}

fun <T1, T2, T3, T4> combine(
    f1: Flow<T1>,
    f2: Flow<T2>,
    f3: Flow<T3>,
    f4: Flow<T4>
): Flow<Tuple4<T1, T2, T3, T4>> = kotlinx.coroutines.flow.combine(f1, f2, f3, f4) { t1, t2, t3, t4 ->
    Tuple4(t1, t2, t3, t4)
}

fun <T1, T2, T3, T4, T5> combine(
    f1: Flow<T1>,
    f2: Flow<T2>,
    f3: Flow<T3>,
    f4: Flow<T4>,
    f5: Flow<T5>
): Flow<Tuple5<T1, T2, T3, T4, T5>> = kotlinx.coroutines.flow.combine(f1, f2, f3, f4, f5) { t1, t2, t3, t4, t5 ->
    Tuple5(t1, t2, t3, t4, t5)
}

fun <T> Flow<T>.onSuccess(action: suspend FlowCollector<T>.() -> Unit): Flow<T> = onCompletion { error ->
    if (error == null) action()
}

fun <T> Flow<T>.onError(action: suspend FlowCollector<T>.(cause: Throwable) -> Unit): Flow<T> = catch(action)

fun <T> Flow<T>.onEachError(action: (cause: Throwable) -> Unit): Flow<T> = onError {
    action(it)
    error(it)
}

fun <T> Flow<Result<T, Error>>.resultOrError(): Flow<T> = map {
    if (it is Ok) return@map it.value
    if (it is Err) throw DomainException(it.error)

    error("The result is neither Ok nor Err.")
}
