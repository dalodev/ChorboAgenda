/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database

import androidx.room.Database
import androidx.room.RoomDatabase
import es.littledavity.database.migrations.MIGRATION_1_2
import es.littledavity.database.chorbo.entities.Contact
import es.littledavity.database.chorbo.tables.ContactDao

/**
 * Chorboagenda room database storing the different requested information like: [Chorbo], etc...
 *
 * @see Database
 */
@Database(
    entities = [Contact::class],
    version = Constants.VERSION
)
internal abstract class ChorboagendaDatabase : RoomDatabase() {

    /**
     * Get [Contact] favorite data access object.
     *
     * @return [Contact] favorite dao.
     */
    abstract val contactDao: ContactDao
}

internal val MIGRATIONS = arrayOf(
    MIGRATION_1_2
)
