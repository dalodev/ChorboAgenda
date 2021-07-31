/*
 * Copyright 2021 dev.id
 */
package es.littledavity.domain.contacts.usecases

import es.littledavity.domain.commons.usecases.UseCase
import es.littledavity.domain.contacts.entities.Contact
import kotlinx.coroutines.flow.Flow

interface SaveContactUseCase : UseCase<SaveContactUseCase.Params, Flow<Contact?>> {
    data class Params(val contact: Contact)
}
