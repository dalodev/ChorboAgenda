package es.littledavity.core.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.core.service.PermissionService
import javax.inject.Singleton

@Module
class ServiceModule {
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

    /**
     * Create a provider method binding for [ImageGalleryService].
     *
     * @return Instance of image gallery service.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideImageGalleryService(
        context: Context,
        chorboRepository: ChorboRepository
    ) = ImageGalleryService(context, chorboRepository)
}
