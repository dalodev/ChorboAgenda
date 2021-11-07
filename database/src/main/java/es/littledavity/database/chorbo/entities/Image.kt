/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database.chorbo.entities

import kotlinx.serialization.Serializable

@Serializable
internal data class Image(
    val id: String?,
    val width: Int?,
    val height: Int?,
    var created: Boolean = false
)
