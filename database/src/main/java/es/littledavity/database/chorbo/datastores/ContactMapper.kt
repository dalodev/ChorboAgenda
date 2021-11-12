/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database.chorbo.datastores

import es.littledavity.core.providers.TimestampProvider
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.data.contacts.DataContact
import es.littledavity.data.contacts.DataCreationDate
import es.littledavity.data.contacts.DataCreationDateCategory
import es.littledavity.data.contacts.DataImage
import es.littledavity.data.contacts.DataInfo
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.DatabaseCreationDate
import es.littledavity.database.chorbo.DatabaseCreationDateCategory
import es.littledavity.database.chorbo.DatabaseImage
import es.littledavity.database.chorbo.DatabaseInfo
import javax.inject.Inject

internal class ContactMapper @Inject constructor(
    private val timestampProvider: TimestampProvider,
    private val imageGalleryService: ImageGalleryService
) {

    fun mapToDatabaseContact(dataContact: DataContact) = DatabaseContact(
        id = dataContact.id,
        name = dataContact.name,
        image = dataContact.image?.toDatabaseImage(dataContact.image?.id, dataContact.name),
        phone = dataContact.phone,
        artworks = dataContact.gallery.toDatabaseImages(dataContact.name),
        screenshots = dataContact.screenshots.toDatabaseImages(dataContact.name),
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

    private fun DataImage.toDatabaseImage(imageId: String?, name: String) = DatabaseImage(
        id = if (!created) {
            imageGalleryService.createMediaFile(imageId, name)
        } else {
            id
        },
        width = width,
        height = height,
        created = when (created) {
            created -> true
            !created -> true
            else -> false
        }
    )

    private fun List<DataImage>.toDatabaseImages(name: String) =
        map { it.toDatabaseImage(it.id, name) }

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
        height = height,
        created = created
    )

    private fun List<DatabaseImage>.toDataImages(): MutableList<DataImage> =
        map { it.toDataImage() }.toMutableList()

    private fun DatabaseCreationDate.toDataCreationDate() = DataCreationDate(
        date = this.date,
        year = this.year,
        category = DataCreationDateCategory.valueOf(this.category.name)
    )
}

internal fun ContactMapper.mapToDatabaseContacts(dataContacts: List<DataContact>) =
    dataContacts.map(::mapToDatabaseContact)

internal fun ContactMapper.mapToDataContact(databaseContacts: List<DatabaseContact>) =
    databaseContacts.map(::mapToDataContact)
