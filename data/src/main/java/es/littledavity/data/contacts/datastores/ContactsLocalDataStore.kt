/*
 * Copyright 2021 dev.id
 */
package es.littledavity.data.contacts.datastores

import es.littledavity.data.commons.Pagination
import es.littledavity.data.contacts.DataContact
import kotlinx.coroutines.flow.Flow

interface ContactsLocalDataStore {

    suspend fun getContact(id: Int): DataContact?

    suspend fun insertContacts(chorbos: List<DataContact>)

    suspend fun insertContact(chorbo: DataContact): Flow<DataContact>

    suspend fun removeContact(id: Int)

    suspend fun searchContacts(
        searchQuery: String,
        pagination: Pagination
    ): List<DataContact>

    suspend fun observeContacts(pagination: Pagination): Flow<List<DataContact>>
}
