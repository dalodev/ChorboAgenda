package es.littledavity.data.contacts.datastores

import es.littledavity.data.commons.Pagination
import es.littledavity.data.contacts.DataContact
import es.littledavity.data.contacts.entities.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsLocalDataStore {

    suspend fun searchGames(
        searchQuery: String,
        pagination: Pagination
    ): List<DataContact>

    suspend fun observeContacts(pagination: Pagination): Flow<List<DataContact>>

}