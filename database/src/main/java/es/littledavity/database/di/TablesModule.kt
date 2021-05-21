package es.littledavity.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.littledavity.database.ChorboagendaDatabase
import es.littledavity.database.chorbo.tables.ChorboDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TablesModule {

    @Provides
    @Singleton
    fun provideContactsTable(contactsDatabase: ChorboagendaDatabase): ChorboDao {
        return contactsDatabase.chorboDao
    }
}