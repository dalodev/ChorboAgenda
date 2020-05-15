/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.contact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.test_utils.rules.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel.state.observeForever(stateObserver)
        viewModel.event.observeForever(eventObserver)
        viewModel.chorbo = mockk()
    }

    @Test
    fun onContinue_shouldInsertAndNextEvent() {
        val expectedEvent = ContactViewEvent.Next

        viewModel.onContinue()

        assertEquals(expectedEvent, viewModel.event.value)

        coVerify {
            eventObserver.onChanged(expectedEvent)
            repository.insertChorbo(viewModel.chorbo)
        }
    }
}
