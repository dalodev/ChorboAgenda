/*
 * Copyright 2021 dalodev
 */
package es.littledavity.data.contacts.usecases.likes

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.data.commons.utils.toDataPagination
import es.littledavity.data.contacts.datastores.LikedContactsLocalDataStore
import es.littledavity.data.contacts.usecases.ContactMapper
import es.littledavity.data.contacts.usecases.mapToDomainContacts
import es.littledavity.domain.contacts.commons.ObserveContactsUseCaseParams
import es.littledavity.domain.contacts.usecases.likes.ObserveLikedContactsUseCase
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class ObserveLikedContactsUseCaseImpl @Inject constructor(
    private val likedContactsLocalDataStore: LikedContactsLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
    private val contactMapper: ContactMapper
) : ObserveLikedContactsUseCase {

    override suspend fun execute(params: ObserveContactsUseCaseParams) =
        likedContactsLocalDataStore.observeLikedContacts(params.pagination.toDataPagination())
            .map(contactMapper::mapToDomainContacts)
            .flowOn(dispatcherProvider.computation)
}
