package es.littledavity.features.info.widgets.main.model

import es.littledavity.features.info.widgets.main.header.ContactHeaderImageModel

internal data class ContactInfoHeaderModel(
    val backgroundImageModels: List<ContactHeaderImageModel>,
    val name: String,
    val imageUrl: String?,
    val creationDate: String,
    val isLiked: Boolean,
    val age: String,
    val country: String,
    val rating: String?,
    val instagram: String?
)