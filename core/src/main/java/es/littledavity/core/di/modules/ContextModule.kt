package es.littledavity.core.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import es.littledavity.core.service.PermissionService
import javax.inject.Singleton

/**
 * Class that contributes to the object graph [CoreComponent].
 *
 * @see Module
 */
@Module
class ContextModule(private val application: Application) {

    /**
     * Create a provider method binding for [Context].
     *
     * @return Instance of context.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideContext(): Context = application

    /**
     * Create a provider method binding for [PermissionService].
     *
     * @return Instance of permission service.
     * @see Provides
     */
    @Singleton
    @Provides
    fun providePermissionService(
        context: Context
    ) = PermissionService(context)
}