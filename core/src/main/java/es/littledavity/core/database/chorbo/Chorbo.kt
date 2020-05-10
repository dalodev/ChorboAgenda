package es.littledavity.core.database.chorbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity represents when a user adds a Chorba, containing the different
 * info required for display on screen.
 */
@Entity(tableName = "chorbo")
data class Chorbo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "countryCode") val countryCode: String,
    @ColumnInfo(name = "countryName") val countryName: String,
    @ColumnInfo(name = "flag") val flag: String,
    @ColumnInfo(name = "whatsapp") var whatsapp: String,
    @ColumnInfo(name = "instagram") var instagram: String

)