/*
 * Copyright 2021 dev.id
 */
package es.littledavity.domain.commons.entities

private const val DEFAULT_PAGE_SIZE = 20

data class Pagination(
    val offset: Int = 0,
    val limit: Int = DEFAULT_PAGE_SIZE
)

fun Pagination.hasDefaultLimit() = limit == DEFAULT_PAGE_SIZE

fun Pagination.nextOffsetPage() = copy(offset = offset + DEFAULT_PAGE_SIZE)

fun Pagination.nextLimitPage() = copy(limit = limit + DEFAULT_PAGE_SIZE)
