/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database.chorbo.entities

import kotlinx.serialization.Serializable

@Serializable
internal data class CreationDate(
    val date: Long?,
    val year: Int?,
    val category: CreationDateCategory,
)
