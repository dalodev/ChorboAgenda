/*
 * Copyright 2021 dalodev
 */
package es.littledavity.data.contacts.usecases

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.data.contacts.datastores.ContactsLocalDataStore
import es.littledavity.domain.contacts.usecases.RemoveContactUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class RemoveContactUseCaseImpl @Inject constructor(
    private val contactsLocalDataStore: ContactsLocalDataStore
) : RemoveContactUseCase {

    override suspend fun execute(params: RemoveContactUseCase.Params) {
        contactsLocalDataStore.removeContact(params.contactId ?: -1)
    }
}
