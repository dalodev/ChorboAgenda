package es.littledavity.testUtils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Module
@DisableInstallInCheck
object MocksModule {

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer {
        return MockWebServer()
    }
}
