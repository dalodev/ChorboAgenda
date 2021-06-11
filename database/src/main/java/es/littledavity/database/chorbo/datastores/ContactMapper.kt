/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo.datastores

import es.littledavity.core.providers.TimestampProvider
import es.littledavity.data.contacts.DataContact
import es.littledavity.data.contacts.DataCreationDate
import es.littledavity.data.contacts.DataCreationDateCategory
import es.littledavity.data.contacts.DataImage
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.DatabaseCreationDate
import es.littledavity.database.chorbo.DatabaseCreationDateCategory
import es.littledavity.database.chorbo.DatabaseImage
import javax.inject.Inject

internal class ContactMapper @Inject constructor(
    private val timestampProvider: TimestampProvider
) {

    fun mapToDatabaseContact(dataContact: DataContact) = DatabaseContact(
        id = dataContact.id,
        name = dataContact.name,
        image = dataContact.image?.toDatabaseImage(),
        phone = dataContact.phone,
        artworks = dataContact.gallery.toDatabaseImages(),
        screenshots = dataContact.screenshots.toDatabaseImages(),
        createTimestamp = timestampProvider.getUnixTimestamp(),
        age = dataContact.age,
        rating = dataContact.rating,
        creationDate = dataContact.creationDate.toDatabaseCreationDate(),
        country = dataContact.country
    )

    private fun DataImage.toDatabaseImage() = DatabaseImage(
        id = id,
        width = width,
        height = height
    )

    private fun List<DataImage>.toDatabaseImages() = map { it.toDatabaseImage() }

    private fun DataCreationDate.toDatabaseCreationDate() = DatabaseCreationDate(
        date = this.date,
        year = this.year,
        category = DatabaseCreationDateCategory.valueOf(this.category.name)
    )

    fun mapToDataContact(databaseContact: DatabaseContact) = DataContact(
        id = databaseContact.id,
        name = databaseContact.name,
        image = databaseContact.image?.toDataImage(),
        gallery = databaseContact.artworks.toDataImages(),
        screenshots = databaseContact.screenshots.toDataImages(),
        phone = databaseContact.phone,
        age = databaseContact.age,
        rating = databaseContact.rating,
        creationDate = databaseContact.creationDate.toDataCreationDate(),
        country = databaseContact.country
    )

    private fun DatabaseImage.toDataImage() = DataImage(
        id = id,
        width = width,
        height = height
    )

    private fun List<DatabaseImage>.toDataImages() = map { it.toDataImage() }

    private fun DatabaseCreationDate.toDataCreationDate() = DataCreationDate(
        date = this.date,
        year = this.year,
        category = DataCreationDateCategory.valueOf(this.category.name)
    )

}

internal fun ContactMapper.mapToDatabaseContacts(dataContacts: List<DataContact>) =
    dataContacts.map(::mapToDatabaseContact)

internal fun ContactMapper.mapToDataContact(databasecontacts: List<DatabaseContact>) =
    databasecontacts.map(::mapToDataContact)
