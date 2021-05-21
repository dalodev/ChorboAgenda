/*
 * Copyright 2021 dev.id
 */
package es.littledavity.data.contacts.usecases

import es.littledavity.data.contacts.DataContact
import es.littledavity.domain.DomainContact
import javax.inject.Inject

internal class ContactMapper @Inject constructor() {

    fun mapToDomainContact(contact: DataContact) = DomainContact(
        id = contact.id,
        name = contact.name,
        image = contact.image,
        phone = contact.phone
    )
}

internal fun ContactMapper.mapToDomainContacts(dataGames: List<DataContact>): List<DomainContact> {
    return dataGames.map(::mapToDomainContact)
}
