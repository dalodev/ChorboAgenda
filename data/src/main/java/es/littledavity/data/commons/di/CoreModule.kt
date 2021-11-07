/*
 * Copyright 2021 dalodev
 */
package es.littledavity.data.commons.di

import android.content.Context
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
    fun provideContactsPreferences(@ApplicationContext context: Context) = context.contactsPreferences
}
