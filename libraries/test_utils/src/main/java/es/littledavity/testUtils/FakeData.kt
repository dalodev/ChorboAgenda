package es.littledavity.testUtils

import es.littledavity.data.commons.DataPagination
import es.littledavity.data.contacts.DataContact

val DATA_CONTACT = DataContact(
    id = 1,
    name = "name",
    phone = "12345123",
    image = "image"
)
val DATA_CONTACTS = listOf(
    DATA_CONTACT.copy(id = 1),
    DATA_CONTACT.copy(id = 2),
    DATA_CONTACT.copy(id = 3)
)

val DATA_PAGINATION = DataPagination(offset = 0, limit = 20)