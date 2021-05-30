package es.littledavity.domain.contacts.usecases

import es.littledavity.domain.commons.DomainResult
import es.littledavity.domain.commons.usecases.UseCase
import es.littledavity.domain.contacts.entities.Contact
import kotlinx.coroutines.flow.Flow

interface GetContactUseCase : UseCase<GetContactUseCase.Params, Flow<DomainResult<Contact>>> {
    data class Params(val contactId: Int)
}