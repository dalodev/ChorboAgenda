/*
 * Copyright 2021 dev.id
 */
package es.littledavity.domain.contacts.usecases

import es.littledavity.domain.commons.entities.Pagination
import es.littledavity.domain.commons.usecases.UseCase
import es.littledavity.domain.contacts.Contact
import kotlinx.coroutines.flow.Flow
import es.littledavity.domain.contacts.usecases.SearchContactsUseCase.Params

interface SearchContactsUseCase : UseCase<Params, Flow<List<Contact>>> {
    data class Params(
        val searchQuery: String,
        val pagination: Pagination = Pagination()
    )
}
