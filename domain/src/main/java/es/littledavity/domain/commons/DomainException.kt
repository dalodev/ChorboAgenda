/*
 * Copyright 2021 dalodev
 */
package es.littledavity.domain.commons

import es.littledavity.domain.commons.entities.Error

class DomainException(
    val error: Error,
    cause: Throwable? = null
) : Exception(error.toString(), cause)
