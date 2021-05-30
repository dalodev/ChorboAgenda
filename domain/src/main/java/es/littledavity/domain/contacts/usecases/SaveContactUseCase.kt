package es.littledavity.domain.contacts.usecases

import es.littledavity.domain.commons.usecases.UseCase
import es.littledavity.domain.contacts.entities.Contact

interface SaveContactUseCase : UseCase<SaveContactUseCase.Params, Unit> {
    data class Params(val contact: Contact)
}