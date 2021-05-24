/*
 * Copyright 2021 dev.id
 */
package es.littledavity.data.contacts.entities

data class Contact(
    val id: Int,
    val image: String?,
    val name: String,
    val phone: String
)
