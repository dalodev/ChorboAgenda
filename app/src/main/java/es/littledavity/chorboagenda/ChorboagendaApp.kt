/*
 * Copyright 2020 littledavity
 */
package es.littledavity.chorboagenda

import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompatApplication
import es.littledavity.chorboagenda.di.DaggerAppComponent
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.DaggerCoreComponent
import es.littledavity.core.di.modules.ContextModule
import es.littledavity.core.utils.ThemeUtils
import timber.log.Timber
import javax.inject.Inject

class ChorboagendaApp : SplitCompatApplication() {

    lateinit var coreComponent: CoreComponent

    @Inject
    lateinit var themeUtils: ThemeUtils

    companion object {

        /**
         * Obtain core dagger component.
         *
         * @param context The application context
         */
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as? ChorboagendaApp)?.coreComponent
    }

    /**
     * Called when the application is starting, before any activity, service, or receiver objects
     * (excluding content providers) have been created.
     *
     * @see SplitCompatApplication.onCreate
     */
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initCoreDependencyInjection()
        initAppDependencyInjection()
        initRandomNightMode()
    }

    // ============================================================================================
    //  Private init methods
    // ============================================================================================
    /**
     * Initialize app dependency injection component.
     */
    private fun initAppDependencyInjection() {
        DaggerAppComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    /**
     * Initialize core dependency injection component.
     */
    private fun initCoreDependencyInjection() {
        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    /**
     * Initialize log library Timber only on debug build.
     */
    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * Initialize random nightMode to make developer aware of day/night themes.
     */
    private fun initRandomNightMode() {
        if (BuildConfig.DEBUG) {
//            themeUtils.setNightMode(Random.nextBoolean())
        }
    }
}
