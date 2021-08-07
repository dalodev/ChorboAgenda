/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo.datastores

import es.littledavity.core.providers.TimestampProvider
import es.littledavity.data.contacts.*
import es.littledavity.data.services.ImageGalleryService
import es.littledavity.database.chorbo.*
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.DatabaseCreationDate
import es.littledavity.database.chorbo.DatabaseCreationDateCategory
import es.littledavity.database.chorbo.DatabaseImage
import javax.inject.Inject

internal class ContactMapper @Inject constructor(
    private val timestampProvider: TimestampProvider,
    private val imageGalleryService: ImageGalleryService
) {

    fun mapToDatabaseContact(dataContact: DataContact) = DatabaseContact(
        id = dataContact.id,
        name = dataContact.name,
        image = dataContact.image?.toDatabaseImage(dataContact),
        phone = dataContact.phone,
        artworks = dataContact.gallery.toDatabaseImages(dataContact),
        screenshots = dataContact.screenshots.toDatabaseImages(dataContact),
        createTimestamp = timestampProvider.getUnixTimestamp(),
        age = dataContact.age,
        rating = dataContact.rating,
        creationDate = dataContact.creationDate.toDatabaseCreationDate(),
        country = dataContact.country,
        instagram = dataContact.instagram,
        infoList = dataContact.info.toDatabaseInfo()
    )

    private fun List<DataInfo>.toDatabaseInfo() = map {
        DatabaseInfo(
            title = it.title,
            description = it.description
        )
    }

    private fun DataImage.toDatabaseImage(dataContact: DataContact) = DatabaseImage(
        id = imageGalleryService.createMediaFile(dataContact),
        width = width,
        height = height
    )

    private fun List<DataImage>.toDatabaseImages(dataContact: DataContact) =
        map { it.toDatabaseImage(dataContact) }

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
        country = databaseContact.country,
        instagram = databaseContact.instagram,
        info = databaseContact.infoList.toDataInfo()
    )

    private fun List<DatabaseInfo>.toDataInfo() = map {
        DataInfo(
            title = it.title,
            description = it.description
        )
    }

    private fun DatabaseImage.toDataImage() = DataImage(
        id = id,
        width = width,
        height = height
    )

    private fun List<DatabaseImage>.toDataImages(): MutableList<DataImage> = map { it.toDataImage() }.toMutableList()

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
