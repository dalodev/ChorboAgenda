/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Entity represents when a user adds a Chorbo, containing the different
 * info required for display on screen.
 */
@Entity(
    tableName = Contact.Schema.TABLE_NAME,
    primaryKeys = [Contact.Schema.ID]
)
internal data class Contact(
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String = "",
    @ColumnInfo(name = "phone") var phone: String
) {

    object Schema {
        const val TABLE_NAME = "chorbo"
        const val ID = "id"
    }
}
