/*
 * Copyright 2021 dalodev
 */
package es.littledavity.data.contacts.usecases

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.data.contacts.datastores.LikedContactsLocalDataStore
import es.littledavity.domain.contacts.usecases.ToggleContactLikeStateUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class ToggleContactLikeStateUseCaseImpl @Inject constructor(
    private val likedContactsLocalDataStore: LikedContactsLocalDataStore
) : ToggleContactLikeStateUseCase {

    override suspend fun execute(params: ToggleContactLikeStateUseCase.Params) {
        if (likedContactsLocalDataStore.isContactLiked(params.contactId)) {
            likedContactsLocalDataStore.unlikeContact(params.contactId)
        } else {
            likedContactsLocalDataStore.likeContact(params.contactId)
        }
    }
}
