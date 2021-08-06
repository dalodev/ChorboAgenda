/*
 * Copyright 2021 dev.id
 */
package es.littledavity.domain.contacts.entities

data class Contact(
    val id: Int,
    var image: Image?,
    var name: String,
    var phone: String?,
    val gallery: List<Image>,
    val screenshots: List<Image>,
    var creationDate: CreationDate?,
    val age: String,
    val rating: String?,
    val country: String,
    val instagram: String?,
    val info: List<Info>
)
