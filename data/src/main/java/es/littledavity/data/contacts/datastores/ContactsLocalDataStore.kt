/*
 * Copyright 2021 dev.id
 */
package es.littledavity.data.contacts.datastores

import es.littledavity.data.commons.Pagination
import es.littledavity.data.contacts.DataContact
import kotlinx.coroutines.flow.Flow

interface ContactsLocalDataStore {

    suspend fun insertChorbos(chorbos: List<DataContact>)

    suspend fun insertChorbo(chorbo: DataContact)

    suspend fun searchGames(
        searchQuery: String,
        pagination: Pagination
    ): List<DataContact>

    suspend fun observeContacts(pagination: Pagination): Flow<List<DataContact>>

}
