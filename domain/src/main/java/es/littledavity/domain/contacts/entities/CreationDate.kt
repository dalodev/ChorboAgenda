/*
 * Copyright 2021 dalodev
 */
package es.littledavity.domain.contacts.entities

data class CreationDate(
    val date: Long?,
    val year: Int?,
    val category: CreationDateCategory,
)
