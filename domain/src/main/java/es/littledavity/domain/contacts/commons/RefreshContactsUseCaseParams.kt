/*
 * Copyright 2021 dalodev
 */
package es.littledavity.domain.contacts.commons

import es.littledavity.domain.commons.entities.Pagination

data class RefreshContactsUseCaseParams(
    val pagination: Pagination = Pagination()
)
