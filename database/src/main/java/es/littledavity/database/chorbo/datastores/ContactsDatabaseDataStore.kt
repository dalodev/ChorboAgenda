/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database.chorbo.datastores

import android.net.Uri
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.data.commons.Pagination
import es.littledavity.data.contacts.DataContact
import es.littledavity.data.contacts.datastores.ContactsLocalDataStore
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.tables.ContactDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class ContactsDatabaseDataStore @Inject constructor(
    private val contactDao: ContactDao,
    private val contactMapper: ContactMapper,
    private val dispatcherProvider: DispatcherProvider,
    private val saveContactFactory: SaveContactFactory,
    private val imageGalleryService: ImageGalleryService,
) : ContactsLocalDataStore {

    override suspend fun getContact(id: Int) = contactDao.getChorbo(id)?.let { databaseContact ->
        withContext(dispatcherProvider.computation) {
            contactMapper.mapToDataContact(databaseContact)
        }
    }

    override suspend fun getContacts() = contactDao.getChorbos().let {
        withContext(dispatcherProvider.computation) {
            it.map(contactMapper::mapToDataContact)
        }
    }

    override suspend fun insertContact(chorbo: DataContact): Flow<DataContact> {
        val id = contactDao.insertChorbo(
            withContext(dispatcherProvider.computation) {
                saveContactFactory.createContact(chorbo)
            }
        )
        chorbo.id = id.toInt()
        return flowOf(chorbo)
    }

    override suspend fun removeContact(id: Int) {
        withContext(dispatcherProvider.computation) {
            val chorbo = contactDao.getChorbo(id)
            contactDao.deleteChorboById(id)
            Uri.parse(chorbo?.image?.id)?.let(imageGalleryService::removeMediaFile)
            chorbo?.artworks?.forEach {
                Uri.parse(it.id)?.let(imageGalleryService::removeMediaFile)
            }
        }
    }

    override suspend fun insertContacts(chorbos: List<DataContact>) =
        contactDao.insertChorbos(
            withContext(dispatcherProvider.computation) {
                contactMapper.mapToDatabaseContacts(chorbos)
            }
        )

    override suspend fun searchContacts(
        searchQuery: String,
        pagination: Pagination
    ): List<DataContact> = contactDao.searchContacts(
        searchQuery = searchQuery,
        offset = pagination.offset,
        limit = pagination.limit
    ).let { databaseContacts ->
        withContext(dispatcherProvider.computation) {
            contactMapper.mapToDataContact(databaseContacts)
        }
    }

    override suspend fun observeContacts(pagination: Pagination) = contactDao.observeContacts(
        offset = pagination.offset,
        limit = pagination.limit
    ).toDataContactsFlow()

    private fun Flow<List<DatabaseContact>>.toDataContactsFlow() = distinctUntilChanged()
        .map(contactMapper::mapToDataContact)
        .flowOn(dispatcherProvider.computation)
}
