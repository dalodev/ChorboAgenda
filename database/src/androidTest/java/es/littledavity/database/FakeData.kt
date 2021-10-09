/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database

import es.littledavity.database.chorbo.entities.*
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.DatabaseImage

internal val DATABASE_CONTACT = DatabaseContact(
    id = 1,
    name = "Contact1",
    phone = "12345123",
    image = DatabaseImage("test", 1, 1, true),
    artworks = mutableListOf(),
    screenshots = mutableListOf(),
    creationDate = CreationDate(1L, 1, CreationDateCategory.YYYY_MMMM_DD),
    age = "18",
    country = "Espana",
    rating = "10/10",
    instagram = "@Littledavity",
    infoList = emptyList(),
    createTimestamp = 1000L

)

internal val DATABASE_CONTACTS = listOf(

    DATABASE_CONTACT.copy(
        id = 1,
        name = "Contact1",
    ),
    DATABASE_CONTACT.copy(
        id = 2,
        name = "Contact2",
    ),
    DATABASE_CONTACT.copy(
        id = 3,
        name = "Contact3",
    ),
    DATABASE_CONTACT.copy(
        id = 4,
        name = "Contact4",
    ),
    DATABASE_CONTACT.copy(
        id = 5,
        name = "Contact5",
    )
)

internal val LIKED_CONTACT = LikedContact(
    id = 1,
    contactId = DATABASE_CONTACT.id,
    likeTimestamp = 0L
)

internal val LIKED_CONTACTS = listOf(
    LikedContact(id = 1, contactId = DATABASE_CONTACTS[0].id, likeTimestamp = 1000L),
    LikedContact(id = 2, contactId = DATABASE_CONTACTS[1].id, likeTimestamp = 2000L),
    LikedContact(id = 3, contactId = DATABASE_CONTACTS[2].id, likeTimestamp = 3000L),
    LikedContact(id = 4, contactId = DATABASE_CONTACTS[3].id, likeTimestamp = 4000L),
    LikedContact(id = 5, contactId = DATABASE_CONTACTS[4].id, likeTimestamp = 5000L)
)
