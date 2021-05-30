package es.littledavity.domain.contacts.usecases

import es.littledavity.domain.commons.usecases.UseCase

interface ToggleContactLikeStateUseCase : UseCase<ToggleContactLikeStateUseCase.Params, Unit> {
    data class Params(val contactId: Int)
}