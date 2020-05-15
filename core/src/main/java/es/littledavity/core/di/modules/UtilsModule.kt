/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.di.modules

import dagger.Binds
import dagger.Module
import es.littledavity.core.utils.ThemeUtils
import es.littledavity.core.utils.ThemeUtilsImpl
import javax.inject.Singleton

/**
 * Class that contributes to the object graph [CoreComponent].
 *
 * @see Module
 */
@Module
abstract class UtilsModule {

    /**
     * Create a provider method binding for [ThemeUtilsImpl].
     *
     * @return Instance of theme utils.
     * @see Binds
     */
    @Singleton
    @Binds
    abstract fun bindThemeUtils(themeUtilsImpl: ThemeUtilsImpl): ThemeUtils
}
