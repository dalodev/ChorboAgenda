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

val DATA_CONTACT = DataContact(
    id = 1,
    name = "name",
    phone = "12345123",
    image = DataImage("", 1, 1),
    gallery = emptyList(),
    screenshots = emptyList(),
    creationDate = CreationDate(1L, 1, CreationDateCategory.YYYY_MMMM_DD),
    age = "18",
    country = "Espana",
    rating = "10/10"
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
    image = DomainImage("", 1, 1),
    gallery = emptyList(),
    screenshots = emptyList(),
    creationDate = DomainCreationDate(1L, 1, DomainCreationDateCategory.YYYY_MMMM_DD),
    age = "18",
    country = "Espana",
    rating = "10/10"
)

val DOMAIN_CONTACTS = listOf(
    DOMAIN_CONTACT.copy(id = 1),
    DOMAIN_CONTACT.copy(id = 2),
    DOMAIN_CONTACT.copy(id = 3)
)