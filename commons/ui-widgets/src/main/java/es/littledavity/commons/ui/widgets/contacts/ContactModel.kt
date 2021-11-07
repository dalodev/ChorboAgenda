/*
 * Copyright 2021 dalodev
 */
package es.littledavity.commons.ui.widgets.contacts

data class ContactModel(
    val id: Int,
    var coverImageUrl: String?,
    val name: String,
    val phone: String?,
    val creationDate: String,
)
