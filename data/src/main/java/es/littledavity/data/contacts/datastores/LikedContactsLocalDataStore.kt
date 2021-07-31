/*
 * Copyright 2021 dev.id
 */
package es.littledavity.data.contacts.datastores

import es.littledavity.data.commons.Pagination
import es.littledavity.data.contacts.entities.Contact
import kotlinx.coroutines.flow.Flow

interface LikedContactsLocalDataStore {

    suspend fun likeContact(contactId: Int)

    suspend fun unlikeContact(contactId: Int)

    suspend fun isContactLiked(contactId: Int): Boolean

    suspend fun observeContactLikeState(contactId: Int): Flow<Boolean>

    suspend fun observeLikedContacts(pagination: Pagination): Flow<List<Contact>>
}
