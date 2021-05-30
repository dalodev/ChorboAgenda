/*
 * Copyright 2021 dev.id
 */
package es.littledavity.domain.contacts.entities

data class Contact(
    val id: Int,
    val image: Image?,
    val name: String,
    val phone: String?,
    val gallery: List<Image>,
    val screenshots: List<Image>,
    val creationDate: CreationDate,
    val age: String,
    val rating: String?,
    val country: String
)
