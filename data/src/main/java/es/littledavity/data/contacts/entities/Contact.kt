/*
 * Copyright 2021 dalodev
 */
package es.littledavity.data.contacts.entities

data class Contact(
    var id: Int,
    val image: Image?,
    val name: String,
    val phone: String?,
    val gallery: MutableList<Image>,
    val screenshots: MutableList<Image>,
    val age: String,
    val country: String,
    val creationDate: CreationDate,
    val rating: String?,
    val instagram: String?,
    val info: List<Info>
)
