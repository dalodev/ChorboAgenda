/*
 * Copyright 2021 dalodev
 */
package es.littledavity.core.urlopener

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object UrlOpenersModule {

    @Provides
    fun provideUrlOpeners(
        @UrlOpenerKey(UrlOpenerKey.Type.NATIVE_APP) nativeAppUrlOpener: UrlOpener,
    ): List<UrlOpener> = listOf(nativeAppUrlOpener)
}
