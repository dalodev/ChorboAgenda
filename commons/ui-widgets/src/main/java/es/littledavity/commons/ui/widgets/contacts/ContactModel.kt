/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.widgets.contacts

data class ContactModel(
    val id: Int,
    var coverImageUrl: String?,
    val name: String,
    val phone: String?,
    val creationDate: String
)
