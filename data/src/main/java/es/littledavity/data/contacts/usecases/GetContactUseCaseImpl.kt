package es.littledavity.data.contacts.usecases

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.data.contacts.datastores.ContactsLocalDataStore
import es.littledavity.domain.DomainContact
import es.littledavity.domain.commons.DomainResult
import es.littledavity.domain.commons.entities.Error
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.usecases.GetContactUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class GetContactUseCaseImpl @Inject constructor(
    private val contactsLocalDataStore: ContactsLocalDataStore,
    private val contactMapper: ContactMapper,
    private val dispatcherProvider: DispatcherProvider
) : GetContactUseCase {

    override suspend fun execute(params: GetContactUseCase.Params) = flow {
        contactsLocalDataStore.getContact(params.contactId)?.let {
            emit(it)
        }
    }.flowOn(dispatcherProvider.main)
        .map(contactMapper::mapToDomainContact)
        .flowOn(dispatcherProvider.computation)
        .map<DomainContact, DomainResult<Contact>>(::Ok)
        .onEmpty { emit(Err(Error.NotFound("Could not find the contact with ID = ${params.contactId}"))) }
}