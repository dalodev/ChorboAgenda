package es.littledavity.domain.contacts.usecases

import es.littledavity.domain.commons.usecases.UseCase
import kotlinx.coroutines.flow.Flow

interface ObserveContactLikeStateUseCase : UseCase<ObserveContactLikeStateUseCase.Params, Flow<Boolean>> {
    data class Params(val contactId: Int)
}