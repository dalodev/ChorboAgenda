/*
 * Copyright 2021 dev.id
 */
package es.littledavity.domain.contacts.commons

import es.littledavity.domain.commons.entities.Pagination

data class ObserveContactsUseCaseParams(
    val pagination: Pagination = Pagination()
)
