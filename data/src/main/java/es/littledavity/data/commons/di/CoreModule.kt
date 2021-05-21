/*
 * Copyright 2021 dev.id
 */
package es.littledavity.data.commons.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.littledavity.data.contacts.Constants

private val Context.contactsPreferences by preferencesDataStore(Constants.CONTACTS_PREFERENCES_NAME)

@Module
@InstallIn(SingletonComponent::class)
internal object CoreModule {

    @Provides
    fun provideContactsPreferences(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.contactsPreferences
    }
}
