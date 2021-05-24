/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo.datastores

import es.littledavity.core.providers.TimestampProvider
import es.littledavity.data.contacts.DataContact
import es.littledavity.database.chorbo.DatabaseContact
import javax.inject.Inject

internal class ContactMapper @Inject constructor(
    private val timestampProvider: TimestampProvider
) {

    fun mapToDatabaseContact(dataContact: DataContact) = DatabaseContact(
        id = dataContact.id,
        name = dataContact.name,
        image = dataContact.image,
        phone = dataContact.phone,
        createTimestamp = timestampProvider.getUnixTimestamp()
    )

    fun mapToDataContact(databaseContact: DatabaseContact) = DataContact(
        id = databaseContact.id,
        name = databaseContact.name,
        image = databaseContact.image,
        phone = databaseContact.phone,
    )
}

internal fun ContactMapper.mapToDatabaseContacts(dataContacts: List<DataContact>) =
    dataContacts.map(::mapToDatabaseContact)

internal fun ContactMapper.mapToDataContact(databaseGames: List<DatabaseContact>) =
    databaseGames.map(::mapToDataContact)
