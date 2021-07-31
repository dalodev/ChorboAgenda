/*
 * Copyright 2021 dev.id
 */
package es.littledavity.domain.contacts.entities

data class CreationDate(
    val date: Long?,
    val year: Int?,
    val category: CreationDateCategory,
)
