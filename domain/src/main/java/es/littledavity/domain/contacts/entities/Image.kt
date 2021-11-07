/*
 * Copyright 2021 dalodev
 */
package es.littledavity.domain.contacts.entities

data class Image(
    val id: String?,
    val width: Int? = null,
    val height: Int? = null,
    var created: Boolean = false
)
