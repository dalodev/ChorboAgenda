/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo.datastores

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.data.commons.Pagination
import es.littledavity.data.contacts.DataContact
import es.littledavity.data.contacts.datastores.LikedContactsLocalDataStore
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.tables.LikedContactDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class LikedContactsDatabaseDataStore @Inject constructor(
    private val likedContactDao: LikedContactDao,
    private val likedContactFactory: LikedContactFactory,
    private val dispatcherProvider: DispatcherProvider,
    private val contactMapper: ContactMapper
) : LikedContactsLocalDataStore {

    override suspend fun likeContact(contactId: Int) {
        likedContactDao.saveLikeContact(likedContactFactory.createLikeContact(contactId))
    }

    override suspend fun unlikeContact(contactId: Int) {
        likedContactDao.deleteLikedContact(contactId)
    }

    override suspend fun isContactLiked(contactId: Int) = likedContactDao.isContactLiked(contactId)

    override suspend fun observeContactLikeState(contactId: Int) =
        likedContactDao.observeContactLikeState(contactId)

    override suspend fun observeLikedContacts(pagination: Pagination) =
        likedContactDao.observeLikedContacts(
            offset = pagination.offset,
            limit = pagination.limit
        ).toDataContactsFlow()

    private fun Flow<List<DatabaseContact>>.toDataContactsFlow(): Flow<List<DataContact>> =
        distinctUntilChanged()
            .map(contactMapper::mapToDataContact)
            .flowOn(dispatcherProvider.computation)
}
