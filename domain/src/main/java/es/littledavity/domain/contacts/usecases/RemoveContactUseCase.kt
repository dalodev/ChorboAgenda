package es.littledavity.domain.contacts.usecases

import es.littledavity.domain.commons.usecases.UseCase

interface RemoveContactUseCase : UseCase<RemoveContactUseCase.Params, Unit> {
    data class Params(val contactId: Int?)
}