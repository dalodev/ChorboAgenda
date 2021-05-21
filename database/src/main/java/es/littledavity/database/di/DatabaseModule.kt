/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.littledavity.database.ChorboagendaDatabase
import es.littledavity.database.Constants
import es.littledavity.database.MIGRATIONS
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideChorboagendaDatabase(
        @ApplicationContext context: Context
    ): ChorboagendaDatabase {
        return Room.databaseBuilder(
            context,
            ChorboagendaDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .addMigrations(*MIGRATIONS)
            .build()
    }


}
