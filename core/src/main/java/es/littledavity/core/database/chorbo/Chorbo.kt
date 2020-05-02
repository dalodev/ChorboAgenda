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
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String
)