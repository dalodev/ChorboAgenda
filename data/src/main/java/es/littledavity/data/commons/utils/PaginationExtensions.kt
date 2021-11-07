/*
 * Copyright 2021 dalodev
 */
package es.littledavity.data.commons.utils

import es.littledavity.data.commons.DataPagination
import es.littledavity.domain.commons.DomainPagination

internal fun DomainPagination.toDataPagination() = DataPagination(
    offset = offset,
    limit = limit
)
