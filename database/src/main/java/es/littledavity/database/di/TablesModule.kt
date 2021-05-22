/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.littledavity.database.ChorboagendaDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TablesModule {

    @Provides
    @Singleton
    fun provideContactsTable(contactsDatabase: ChorboagendaDatabase) = contactsDatabase.chorboDao

}
