/*
 * Copyright 2021 dev.id
 */
package es.littledavity.data.contacts.entities

data class CreationDate(
    val date: Long?,
    val year: Int?,
    val category: CreationDateCategory,
)
