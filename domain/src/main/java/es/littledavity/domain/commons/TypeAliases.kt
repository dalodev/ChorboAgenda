/*
 * Copyright 2021 dalodev
 */
package es.littledavity.domain.commons

import com.github.michaelbull.result.Result
import es.littledavity.domain.commons.entities.Error
import es.littledavity.domain.commons.entities.Pagination

typealias DomainPagination = Pagination
typealias DomainResult<T> = Result<T, Error>
