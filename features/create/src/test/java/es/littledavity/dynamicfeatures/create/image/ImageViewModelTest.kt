/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.image

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import es.littledavity.core.service.PermissionService
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ImageViewModelTest {
    private val TAG = "ImageViewModelTest"

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<ImageViewState>

    @MockK(relaxed = true)
    lateinit var eventObserver: Observer<ImageViewEvent>

    @InjectMockKs
    lateinit var viewModel: ImageViewModel

    @MockK
    lateinit var permissionService: PermissionService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel.state.observeForever(stateObserver)
        viewModel.event.observeForever(eventObserver)
    }

    @Test
    fun onLoadImage_shouldBeContinue() {
        val uri = mockk<Uri>()
        viewModel.loadImage(uri)
        val expectedState = ImageViewState.Continue(any())
        assertEquals(expectedState, viewModel.state.value)
        coVerify {
            stateObserver.onChanged(expectedState)
        }
    }

    @Test
    fun onContinue_ShouldbeNextEvent() {
        val expectedEvent = ImageViewEvent.Next
        viewModel.onContinue()

        assertEquals(expectedEvent, viewModel.event.value)
        coVerify {
            eventObserver.onChanged(expectedEvent)
        }
    }
}
