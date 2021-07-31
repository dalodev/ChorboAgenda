/*
 * Copyright 2021 dev.id
 */
package es.littledavity.testUtils

import es.littledavity.data.commons.DataPagination
import es.littledavity.data.contacts.DataContact
import es.littledavity.data.contacts.DataImage
import es.littledavity.data.contacts.entities.CreationDate
import es.littledavity.data.contacts.entities.CreationDateCategory
import es.littledavity.domain.DomainContact
import es.littledavity.domain.DomainCreationDate
import es.littledavity.domain.DomainCreationDateCategory
import es.littledavity.domain.DomainImage
import es.littledavity.domain.commons.entities.Error

val DATA_CONTACT = DataContact(
    id = 1,
    name = "name",
    phone = "12345123",
    image = DataImage("test", 1, 1),
    gallery = emptyList(),
    screenshots = emptyList(),
    creationDate = CreationDate(1L, 1, CreationDateCategory.YYYY_MMMM_DD),
    age = "18",
    country = "Espana",
    rating = "10/10",
    instagram = "@Littledavity"
)
val DATA_CONTACTS = listOf(
    DATA_CONTACT.copy(id = 1),
    DATA_CONTACT.copy(id = 2),
    DATA_CONTACT.copy(id = 3)
)

val DATA_PAGINATION = DataPagination(offset = 0, limit = 20)

val DOMAIN_CONTACT = DomainContact(
    id = 1,
    name = "name",
    phone = "1234515",
    image = DomainImage("test", 1, 1),
    gallery = emptyList(),
    screenshots = emptyList(),
    creationDate = DomainCreationDate(1L, 1, DomainCreationDateCategory.YYYY_MMMM_DD),
    age = "18",
    country = "Espana",
    rating = "10/10",
    instagram = "@Littledavity"
)

val DOMAIN_CONTACTS = listOf(
    DOMAIN_CONTACT.copy(id = 1),
    DOMAIN_CONTACT.copy(id = 2),
    DOMAIN_CONTACT.copy(id = 3)
)

val DOMAIN_ERROR_API = Error.ApiError.ClientError("message")
val DOMAIN_ERROR_NOT_FOUND = Error.NotFound("message")
