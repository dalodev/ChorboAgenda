/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.littledavity.database.chorbo.ContactsTypeConverter
import es.littledavity.database.chorbo.entities.Contact
import es.littledavity.database.chorbo.entities.LikedContact
import es.littledavity.database.chorbo.tables.ContactDao
import es.littledavity.database.chorbo.tables.LikedContactDao
import es.littledavity.database.migrations.MIGRATION_1_2

/**
 * Chorboagenda room database storing the different requested information like: [Contact], etc...
 *
 * @see Database
 */
@Database(
    entities = [Contact::class, LikedContact::class],
    version = Constants.VERSION
)
@TypeConverters(
    ContactsTypeConverter::class
)
internal abstract class ChorboagendaDatabase : RoomDatabase() {

    /**
     * Get [Contact] favorite data access object.
     *
     * @return [Contact] favorite dao.
     */
    abstract val contactDao: ContactDao

    abstract val likedContactsDao: LikedContactDao
}

internal val MIGRATIONS = arrayOf(
    MIGRATION_1_2
)
