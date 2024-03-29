/*
 * Copyright 2021 dalodev
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

private const val SPAIN_STRING = "Espana"
private const val RATING_STRING = "10/10"
private const val INSTAGRAM_STRING = "@Littledavity"

val DATA_CONTACT = DataContact(
    id = 1,
    name = "name",
    phone = "12345123",
    image = DataImage("test", 1, 1, false),
    gallery = mutableListOf(),
    screenshots = mutableListOf(),
    creationDate = CreationDate(1L, 1, CreationDateCategory.YYYY_MMMM_DD),
    age = "18",
    country = SPAIN_STRING,
    rating = RATING_STRING,
    instagram = INSTAGRAM_STRING,
    info = emptyList()
)

val DATA_CONTACT_IMAGE_CREATED = DataContact(
    id = 1,
    name = "name",
    phone = "12345123",
    image = DataImage("test", 1, 1, true),
    gallery = mutableListOf(),
    screenshots = mutableListOf(),
    creationDate = CreationDate(1L, 1, CreationDateCategory.YYYY_MMMM_DD),
    age = "18",
    country = SPAIN_STRING,
    rating = RATING_STRING,
    instagram = INSTAGRAM_STRING,
    info = emptyList()
)
val DATA_CONTACTS = listOf(
    DATA_CONTACT.copy(id = 1),
    DATA_CONTACT.copy(id = 2),
    DATA_CONTACT.copy(id = 3)
)

val DATA_CONTACTS_IMAGE_CREATED = listOf(
    DATA_CONTACT_IMAGE_CREATED.copy(id = 1),
    DATA_CONTACT_IMAGE_CREATED.copy(id = 2),
    DATA_CONTACT_IMAGE_CREATED.copy(id = 3)
)

val DATA_PAGINATION = DataPagination(offset = 0, limit = 20)

val DOMAIN_CONTACT = DomainContact(
    id = 1,
    name = "name",
    phone = "1234515",
    image = DomainImage("test", 1, 1, false),
    gallery = mutableListOf(),
    screenshots = mutableListOf(),
    creationDate = DomainCreationDate(1L, 1, DomainCreationDateCategory.YYYY_MMMM_DD),
    age = "18",
    country = SPAIN_STRING,
    rating = RATING_STRING,
    instagram = INSTAGRAM_STRING,
    info = mutableListOf()
)

val DOMAIN_CONTACTS = listOf(
    DOMAIN_CONTACT.copy(id = 1),
    DOMAIN_CONTACT.copy(id = 2),
    DOMAIN_CONTACT.copy(id = 3)
)

val DOMAIN_ERROR_API = Error.ApiError.ClientError("message")
val DOMAIN_ERROR_NOT_FOUND = Error.NotFound("message")
