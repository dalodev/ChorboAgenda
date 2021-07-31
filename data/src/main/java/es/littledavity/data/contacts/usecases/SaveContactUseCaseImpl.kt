package es.littledavity.data.contacts.usecases

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.data.contacts.datastores.ContactsLocalDataStore
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.usecases.SaveContactUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class SaveContactUseCaseImpl @Inject constructor(
    private val contactsLocalDataStore: ContactsLocalDataStore,
    private val contactMapper: ContactMapper,
    private val dispatcherProvider: DispatcherProvider
) : SaveContactUseCase {

    override suspend fun execute(params: SaveContactUseCase.Params) =
        contactsLocalDataStore.insertContact(contactMapper.mapToDataContact(params.contact))
            .map(contactMapper::mapToDomainContact)
            .flowOn(dispatcherProvider.computation)

}