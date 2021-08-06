package es.littledavity.features.edit.widgets.model

import es.littledavity.domain.contacts.entities.Image

internal data class ContactInfoHeaderModel(
    val galleryImages: List<Image>,
    val name: String,
    val imageUrl: String?,
    val creationDate: String,
    val age: String,
    val country: String,
    val rating: String?,
    val phone: String?,
    val instagram: String?
)