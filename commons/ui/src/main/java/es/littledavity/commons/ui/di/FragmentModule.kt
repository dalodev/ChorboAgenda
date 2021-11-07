/*
 * Copyright 2021 dalodev
 */
package es.littledavity.commons.ui.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import es.littledavity.commons.ui.extensions.navController

@Module
@InstallIn(FragmentComponent::class)
internal object FragmentModule {

    @Provides
    fun provideNavController(fragment: Fragment) = fragment.navController
}
