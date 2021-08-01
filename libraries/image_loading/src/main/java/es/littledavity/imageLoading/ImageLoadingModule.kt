/*
 * Copyright 2021 dev.id
 */
package es.littledavity.imageLoading

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import com.paulrybitskyi.commons.ktx.getSystemService
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ImageLoadingModule {

    @Provides
    @Singleton
    fun providePicasso(@ApplicationContext context: Context): Picasso {
        val activityManager = context.getSystemService<ActivityManager>()
        // ~50% of the available heap
        val cacheSizeInBytes = (1024 * 1024 * activityManager.memoryClass / 2)

        return Picasso.Builder(context)
            .defaultBitmapConfig(Bitmap.Config.ARGB_8888)
            .memoryCache(LruCache(cacheSizeInBytes))
            .build()
    }
}
