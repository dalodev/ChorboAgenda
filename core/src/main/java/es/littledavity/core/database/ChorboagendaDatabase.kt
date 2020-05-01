package es.littledavity.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import es.littledavity.core.BuildConfig
import es.littledavity.core.database.chorbo.Chorbo
import es.littledavity.core.database.chorbo.ChorboDao

/**
 * Marvel room database storing the different requested information like: chorba, etc...
 *
 * @see Database
 */
@Database(
    entities = [Chorbo::class],
    exportSchema = BuildConfig.CHORBOAGENDA_DATABASE_EXPORT_SCHEMA,
    version = BuildConfig.MARVEL_DATABASE_VERSION
)
abstract class ChorboagendaDatabase : RoomDatabase() {

    /**
     * Get [Chorbo] favorite data access object.
     *
     * @return [Chorbo] favorite dao.
     */
    abstract fun chorboFavoriteDao(): ChorboDao
}