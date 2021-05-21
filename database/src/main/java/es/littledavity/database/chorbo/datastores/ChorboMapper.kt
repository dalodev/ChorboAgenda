/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo.datastores

import es.littledavity.data.contacts.entities.Contact
import es.littledavity.database.chorbo.DatabaseChorbo
import javax.inject.Inject

internal class ChorboMapper @Inject constructor() {

    fun mapToDataContact(databaseChorbo: DatabaseChorbo) = Contact(
        id = databaseChorbo.id,
        name = databaseChorbo.name,
        phone = databaseChorbo.whatsapp,
        image = databaseChorbo.image
    )
}

internal fun ChorboMapper.mapToDataContact(databaseGames: List<DatabaseChorbo>): List<Contact> {
    return databaseGames.map(::mapToDataContact)
}
