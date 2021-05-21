/*
 * Copyright 2020 littledavity
 */
package es.littledavity.chorboagenda

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
internal class ChorboagendaApp : Application() {

    /**
     * Called when the application is starting, before any activity, service, or receiver objects
     * (excluding content providers) have been created.
     *
     * @see Application.onCreate
     */
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    // ============================================================================================
    //  Private init methods
    // ============================================================================================

    /**
     * Initialize log library Timber only on debug build.
     */
    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
