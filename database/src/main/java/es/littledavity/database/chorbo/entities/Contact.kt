/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity represents when a user adds a Chorbo, containing the different
 * info required for display on screen.
 */
@Entity(
    tableName = Contact.Schema.TABLE_NAME
)
internal data class Contact(
    @ColumnInfo(name = Schema.ID) @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String? = "",
    @ColumnInfo(name = "phone") var phone: String,
    @ColumnInfo(name = Schema.CREATE_TIMESTAMP) val createTimestamp: Long
) {

    object Schema {
        const val TABLE_NAME = "chorbo"
        const val ID = "id"
        const val CREATE_TIMESTAMP = "create_timestamp"
    }
}
