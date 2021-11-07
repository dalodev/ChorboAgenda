/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database.chorbo.entities

import kotlinx.serialization.Serializable

@Serializable
internal data class Info(
    val title: String?,
    val description: String?
)
