/*
 * Copyright 2021 dev.id
 */
package es.littledavity.data.contacts.usecases

import es.littledavity.data.contacts.DataContact
import es.littledavity.data.contacts.DataCreationDate
import es.littledavity.data.contacts.DataCreationDateCategory
import es.littledavity.data.contacts.DataImage
import es.littledavity.domain.DomainContact
import es.littledavity.domain.DomainCreationDate
import es.littledavity.domain.DomainCreationDateCategory
import es.littledavity.domain.DomainImage
import javax.inject.Inject

internal class ContactMapper @Inject constructor() {

    fun mapToDomainContact(contact: DataContact) = DomainContact(
        id = contact.id,
        name = contact.name,
        image = contact.image?.toDomainImage(),
        phone = contact.phone,
        gallery = contact.gallery.toDomainImages(),
        screenshots = contact.screenshots.toDomainImages(),
        age = contact.age,
        country = contact.country,
        creationDate = contact.creationDate.toDomainCreationDates(),
        rating = contact.rating
    )

    private fun DataImage.toDomainImage(): DomainImage {
        return DomainImage(
            id = id,
            width = width,
            height = height
        )
    }

    private fun List<DataImage>.toDomainImages(): List<DomainImage> {
        return map { it.toDomainImage() }
    }

    private fun DataCreationDate.toDomainCreationDates() = DomainCreationDate(
        date = this.date,
        year = this.year,
        category = DomainCreationDateCategory.valueOf(this.category.name)
    )

    fun mapToDataContact(contact: DomainContact) = DataContact(
        id = contact.id,
        name = contact.name,
        image = contact.image?.toDataImage(),
        phone = contact.phone,
        gallery = contact.gallery.toDataImages(),
        screenshots = contact.screenshots.toDataImages(),
        age = contact.age,
        country = contact.country,
        creationDate = contact.creationDate.toDataCreationDate(),
        rating = contact.rating
    )

    private fun DomainImage.toDataImage(): DataImage {
        return DataImage(
            id = id,
            width = width,
            height = height
        )
    }

    private fun List<DomainImage>.toDataImages(): List<DataImage> {
        return map { it.toDataImage() }
    }

    private fun DomainCreationDate.toDataCreationDate() = DataCreationDate(
        date = this.date,
        year = this.year,
        category = DataCreationDateCategory.valueOf(this.category.name)
    )
}

internal fun ContactMapper.mapToDomainContacts(dataGames: List<DataContact>) = dataGames.map(::mapToDomainContact)
