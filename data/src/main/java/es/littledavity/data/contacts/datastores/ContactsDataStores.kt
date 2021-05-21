/*
 * Copyright 2021 dev.id
 */
package es.littledavity.data.contacts.datastores

import javax.inject.Inject

internal class ContactsDataStores @Inject constructor(
    val local: ContactsLocalDataStore
)
