/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = LikedContact.Schema.TABLE_NAME,
    indices = [
        Index(LikedContact.Schema.CONTACT_ID),
        Index(LikedContact.Schema.LIKE_TIMESTAMP)
    ]
)
internal data class LikedContact(
    @ColumnInfo(name = Schema.ID) @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = Schema.CONTACT_ID) val contactId: Int,
    @ColumnInfo(name = Schema.LIKE_TIMESTAMP) val likeTimestamp: Long
) {

    object Schema {
        const val TABLE_NAME = "liked_contacts"
        const val ID = "id"
        const val CONTACT_ID = "contact_id"
        const val LIKE_TIMESTAMP = "like_timestamp"
    }
}
