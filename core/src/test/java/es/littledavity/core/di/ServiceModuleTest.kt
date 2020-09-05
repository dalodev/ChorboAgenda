package es.littledavity.core.di

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import es.littledavity.core.di.modules.ServiceModule
import es.littledavity.testUtils.rules.CoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class ServiceModuleTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var module: ServiceModule

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        module = ServiceModule()
    }

    @Test
    fun verifyProvidedPermissionService() {
        val context: Context = mock()
        val permissionService = module.providePermissionService(context)
        assertEquals(context, permissionService.context)
    }
/*
    @Test
    fun verifyProvidedImageGalleryService() {
        val context: Context = mock()
        val imageGalleryService = module.provideImageGalleryService(context, chorboRepository)
        assertEquals(context, imageGalleryService.context)
    }*/
}
