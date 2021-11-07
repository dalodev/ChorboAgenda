/*
 * Copyright 2021 dalodev
 */
package es.littledavity.domain.commons.usecases

interface UseCase<In, Out> {
    suspend fun execute(params: In): Out
}
