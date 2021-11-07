/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info.widgets.model

import es.littledavity.features.info.widgets.header.ContactHeaderImageModel

internal data class ContactInfoHeaderModel(
    val backgroundImageModels: List<ContactHeaderImageModel>,
    val name: String,
    val imageUrl: String?,
    val creationDate: String,
    val isLiked: Boolean,
    val age: String,
    val country: String,
    val rating: String?,
    val phone: String?,
    val instagram: String?
)
