/*
 * Copyright 2020 littledavity
 */
package es.littledavity.database

import androidx.room.Database
import androidx.room.RoomDatabase
import es.littledavity.database.migrations.MIGRATION_1_2
import es.littledavity.database.chorbo.entities.Chorbo
import es.littledavity.database.chorbo.tables.ChorboDao

/**
 * Chorboagenda room database storing the different requested information like: [Chorbo], etc...
 *
 * @see Database
 */
@Database(
    entities = [Chorbo::class],
    version = Constants.VERSION
)
internal abstract class ChorboagendaDatabase : RoomDatabase() {

    /**
     * Get [Chorbo] favorite data access object.
     *
     * @return [Chorbo] favorite dao.
     */
    abstract val chorboDao: ChorboDao
}

internal val MIGRATIONS = arrayOf(
    MIGRATION_1_2
)
