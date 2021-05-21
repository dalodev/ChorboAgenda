/*
 * Copyright 2021 dev.id
 */
package es.littledavity.domain.commons

import es.littledavity.domain.commons.entities.Error

class DomainException(
    val error: Error,
    cause: Throwable? = null
) : Exception(error.toString(), cause)
