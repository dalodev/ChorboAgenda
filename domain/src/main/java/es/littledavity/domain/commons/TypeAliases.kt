package es.littledavity.domain.commons

import com.github.michaelbull.result.Result
import es.littledavity.domain.commons.entities.Pagination
import es.littledavity.domain.commons.entities.Error

typealias DomainPagination = Pagination
typealias DomainResult<T> = Result<T, Error>