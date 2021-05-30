/*
 * Copyright 2021 dev.id
 */
package es.littledavity.data.contacts.entities

data class Contact(
    val id: Int,
    val image: Image?,
    val name: String,
    val phone: String?,
    val gallery: List<Image>,
    val screenshots: List<Image>,
    val age: String,
    val country: String,
    val creationDate: CreationDate,
    val rating: String?,

)
