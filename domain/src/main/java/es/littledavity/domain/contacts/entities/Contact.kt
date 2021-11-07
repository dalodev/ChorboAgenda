/*
 * Copyright 2021 dalodev
 */
package es.littledavity.domain.contacts.entities

data class Contact(
    var id: Int,
    var image: Image?,
    var name: String,
    var phone: String?,
    var gallery: MutableList<Image>,
    var screenshots: MutableList<Image>,
    var creationDate: CreationDate?,
    var age: String,
    var rating: String?,
    var country: String,
    var instagram: String?,
    var info: MutableList<Info>
)
