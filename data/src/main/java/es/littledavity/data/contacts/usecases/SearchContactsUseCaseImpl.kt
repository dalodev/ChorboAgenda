/*
 * Copyright 2021 dev.id
 */
package es.littledavity.data.contacts.usecases

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.core.utils.asSuccess
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.usecases.SearchContactsUseCase
import es.littledavity.domain.contacts.usecases.SearchContactsUseCase.Params
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton
import es.littledavity.core.utils.resultOrError
import es.littledavity.data.contacts.datastores.ContactsDataStores
import es.littledavity.data.commons.utils.toDataPagination
import es.littledavity.domain.commons.DomainResult
import kotlinx.coroutines.delay

private const val LOCAL_SEARCH_DELAY_TIMEOUT = 150L

@Singleton
@BindType
internal class SearchContactsUseCaseImpl @Inject constructor(
    private val gamesDataStores: ContactsDataStores,
    private val dispatcherProvider: DispatcherProvider,
    private val contactMapper: ContactMapper
) : SearchContactsUseCase {

    override suspend fun execute(
        params: Params
    ) = flow { emit(searchContacts(params)) }
        .flowOn(dispatcherProvider.computation)
        .resultOrError()

    private suspend fun searchContacts(
        params: Params
    ): DomainResult<List<Contact>> {
        val pagination = params.pagination.toDataPagination()

        return gamesDataStores.local
            .searchContacts(params.searchQuery, pagination)
            .let(contactMapper::mapToDomainContacts)
            .asSuccess()
            // Delaying to give a sense of "loading" since it's really fast without it
            .also { delay(LOCAL_SEARCH_DELAY_TIMEOUT) }
    }
}
