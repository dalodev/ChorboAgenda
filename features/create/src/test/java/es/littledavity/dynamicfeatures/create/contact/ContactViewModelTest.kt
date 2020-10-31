/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.contact

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.testUtils.rules.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ContactViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<ContactViewState>

    @MockK(relaxed = true)
    lateinit var eventObserver: Observer<ContactViewEvent>

    @InjectMockKs
    lateinit var viewModel: ContactViewModel

    @MockK(relaxed = true)
    lateinit var repository: ChorboRepository

    @MockK(relaxed = true)
    lateinit var imageGalleryService: ImageGalleryService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel.state.observeForever(stateObserver)
        viewModel.event.observeForever(eventObserver)
        viewModel.chorbo = mockk(relaxed = true)
    }

    @Test
    fun onContinue_whenException_shouldShowError() = runBlocking {
        val ex = Exception("Uri.parse(chorbo.image) must not be null")
        val expectedState = ContactViewState.Error(ex.message)

        every { imageGalleryService.getBitmap(any<Uri>()) } throws Exception("Uri.parse(chorbo.image) must not be null")

        viewModel.onContinue()

        assertEquals(expectedState, viewModel.state.value)

        coVerify {
            stateObserver.onChanged(expectedState)
        }
    }
}
