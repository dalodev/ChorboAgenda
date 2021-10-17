/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo.entities

import kotlinx.serialization.Serializable

@Serializable
internal data class Info(
    val title: String?,
    val description: String?
)
