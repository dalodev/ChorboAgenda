package es.littledavity.data.contacts.usecases

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.data.contacts.datastores.LikedContactsLocalDataStore
import es.littledavity.domain.contacts.usecases.ObserveContactLikeStateUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class ObserveContactLikeStateUseCaseImpl @Inject constructor(
    private val likeContactsLocalDataStore: LikedContactsLocalDataStore
) : ObserveContactLikeStateUseCase {

    override suspend fun execute(params: ObserveContactLikeStateUseCase.Params) =
        likeContactsLocalDataStore.observeContactLikeState(params.contactId)
}