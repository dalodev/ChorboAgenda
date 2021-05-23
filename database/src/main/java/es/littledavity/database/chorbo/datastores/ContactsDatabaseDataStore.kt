/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo.datastores

import androidx.lifecycle.LiveData
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.data.commons.Pagination
import es.littledavity.data.contacts.DataContact
import es.littledavity.data.contacts.datastores.ContactsLocalDataStore
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.entities.Contact
import es.littledavity.database.chorbo.tables.ContactDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
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
) : ContactsLocalDataStore {

    override suspend fun searchGames(
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
    ).toDataGamesFlow()

    private fun Flow<List<DatabaseContact>>.toDataGamesFlow() = distinctUntilChanged()
        .map(contactMapper::mapToDataContact)
        .flowOn(dispatcherProvider.computation)

    /**
     * Obtain all database added chorbo ordering by name field.
     *
     * @return [LiveData] List with chorbo.
     */
    fun getAllChorboLiveData(): LiveData<List<Contact>> =
        contactDao.getAllChorbosLiveData()

    /**
     * Obtain all database added Chorbo ordering by name field.
     *
     * @return List with chorbos.
     */
    suspend fun getChorbos() =
        contactDao.getChorbos()

    /**
     * Obtain database Chorbo by identifier.
     *
     * @param chorboId Chorbo identifier.
     *
     * @return Chorbo if exist, otherwise null
     */
    suspend fun getChorbo(chorboId: Int): Contact? =
        contactDao.getChorbo(chorboId)

    /**
     * Obtain all database added Chorbo ordering by name field.
     *
     * @param offset Skip the specified number of resources in the result set.
     * @param limit Limit the result set to the specified number of resources.
     * @return List with chorbos.
     */
    suspend fun getChorbos(offset: Int, limit: Int) =
        contactDao.getChorbos(offset, limit)

    /**
     * Delete all database chorbo.
     */
    suspend fun deleteAllChorbos() =
        contactDao.deleteAllChorbos()

    /**
     * Delete database chorbo by identifier.
     *
     * @param chorboId chorbo identifier.
     */
    suspend fun deleteChorboById(chorboId: Int) =
        contactDao.deleteChorboById(chorboId)

    /**
     * Delete database chorbos by identifier.
     *
     * @param idList chorbos identifier.
     */
    suspend fun deleteChorbosById(idList: List<Int>) =
        contactDao.deleteChorbosById(idList)

    /**
     * Delete database chorbo.
     *
     * @param chorbo Chorbos.
     */
    suspend fun deleteChorbo(chorbo: Contact) =
        contactDao.deleteChorbo(chorbo)

    /**
     * Add to database a list of chorbos.
     *
     * @param chorbos List of chorbos.
     */
    suspend fun insertChorbos(chorbos: List<Contact>) =
        contactDao.insertChorbos(chorbos)

    /**
     * Add to database a chrobo.
     *
     * @param id Chorbo identifier.
     * @param name Chorbo name.
     */
    /*suspend fun insertChorbo(
        id: Int,
        name: String,
        image: String,
        countryCode: String,
        countryName: String,
        flag: String,
        whatsapp: String,
        instagram: String
    ) {
        val chorbo = Chorbo(
            id = id,
            name = name,
            image = image,
            countryCode = countryCode,
            countryName = countryName,
            flag = flag,
            whatsapp = whatsapp,
            instagram = instagram
        )
        chorboDao.insertChorbo(chorbo)
    }*/

    /**
     * Add to database a chrobo.
     *
     * @param chorbo Chorbo object.
     */
    suspend fun insertChorbo(chorbo: Contact) {
        contactDao.insertChorbo(chorbo)
    }
}
