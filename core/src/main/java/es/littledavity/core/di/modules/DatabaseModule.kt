/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import es.littledavity.core.BuildConfig
import es.littledavity.core.database.ChorboagendaDatabase
import es.littledavity.core.database.chorbo.ChorboDao
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.core.database.migrations.MIGRATION_1_2
import es.littledavity.core.di.CoreComponent
import javax.inject.Singleton

/**
 * Class that contributes to the object graph [CoreComponent].
 *
 * @see Module
 */
@Module
class DatabaseModule {

    /**
     * Create a provider method binding for [ChorboagendaDatabase].
     *
     * @return Instance of chorboagenda database.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideChorboagendaDatabase(context: Context) =
        Room.databaseBuilder(
            context,
            ChorboagendaDatabase::class.java,
            BuildConfig.CHORBOAGENDA_DATABASE_NAME
        ).addMigrations(MIGRATION_1_2)
            .build()

    /**
     * Create a provider method binding for [ChorboDao].
     *
     * @return Instance of chorbo data access object.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideChorboDao(chorboagendaDatabase: ChorboagendaDatabase) =
        chorboagendaDatabase.chorboDao()

    /**
     * Create a provider method binding for [ChorboRepository].
     *
     * @return Instance of chorbo repository.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideChorboRepository(
        chorboDao: ChorboDao
    ) = ChorboRepository(chorboDao)
}
