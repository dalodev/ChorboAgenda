/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.tables

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import es.littledavity.database.ChorboagendaDatabase
import es.littledavity.database.MIGRATIONS
import es.littledavity.database.commons.RoomTypeConverter
import es.littledavity.database.commons.addTypeConverters
import es.littledavity.testUtils.MainCoroutineRule
import kotlinx.coroutines.asExecutor
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideChorboagendaDatabase(
        typeConverters: Set<@JvmSuppressWildcards RoomTypeConverter>
    ): ChorboagendaDatabase {
        return Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            ChorboagendaDatabase::class.java
        )
            .setTransactionExecutor(MainCoroutineRule().dispatcher.asExecutor())
            .setQueryExecutor(MainCoroutineRule().dispatcher.asExecutor())
            .addTypeConverters(typeConverters)
            .addMigrations(*MIGRATIONS)
            .build()
    }
}
