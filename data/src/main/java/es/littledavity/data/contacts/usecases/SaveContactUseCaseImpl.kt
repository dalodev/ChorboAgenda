package es.littledavity.data.contacts.usecases

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.data.contacts.datastores.ContactsLocalDataStore
import es.littledavity.domain.contacts.usecases.SaveContactUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class SaveContactUseCaseImpl @Inject constructor(
    private val contactsLocalDataStore: ContactsLocalDataStore,
    private val contactMapper: ContactMapper
) : SaveContactUseCase {

    override suspend fun execute(params: SaveContactUseCase.Params) {
        contactsLocalDataStore.insertChorbo(contactMapper.mapToDataContact(params.contact))
    }

}