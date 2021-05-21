package es.littledavity.domain.commons.extensions

import es.littledavity.domain.commons.usecases.UseCase

suspend fun <Out> UseCase<Unit, Out>.execute(): Out {
    return execute(Unit)
}