/*
 * Copyright 2020 littledavity
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
    tableName = Chorbo.Schema.TABLE_NAME,
    primaryKeys = [Chorbo.Schema.ID]
)
internal data class Chorbo(
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String = "",
    @ColumnInfo(name = "countryCode") val countryCode: String,
    @ColumnInfo(name = "countryName") val countryName: String,
    @ColumnInfo(name = "flag") var flag: String,
    @ColumnInfo(name = "whatsapp") var whatsapp: String,
    @ColumnInfo(name = "instagram") var instagram: String

) {

    object Schema {
        const val TABLE_NAME = "chorbo"
        const val ID = "id"
    }
}
