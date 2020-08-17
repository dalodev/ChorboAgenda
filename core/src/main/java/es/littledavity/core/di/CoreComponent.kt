/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.di

import android.content.Context
import dagger.Component
import es.littledavity.core.database.chorbo.ChorboDao
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.core.di.modules.ContextModule
import es.littledavity.core.di.modules.DatabaseModule
import es.littledavity.core.di.modules.ServiceModule
import es.littledavity.core.di.modules.UtilsModule
import es.littledavity.core.utils.ThemeUtils
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 *
 * @see Component
 */
@Singleton
@Component(
    modules = [
        ContextModule::class,
        UtilsModule::class,
        DatabaseModule::class,
        ServiceModule::class
    ]
)
interface CoreComponent {

    /**
     * Provide dependency graph Context
     *
     * @return Context
     */
    fun context(): Context

    /**
     * Provide dependency graph ThemeUtils
     *
     * @return ThemeUtils
     */
    fun themeUtils(): ThemeUtils

    /**
     * Provide dependency graph [ChorboRepository]
     *
     * @return ChorboRepository
     */
    fun chorboRepository(): ChorboRepository

    /**
     * Provide dependency graph ChorboDao
     *
     * @return ChorboDao
     */
    fun chorboDao(): ChorboDao
}
