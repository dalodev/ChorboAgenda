package es.littledavity.dynamicfeatures.create.name

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NameViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<NameViewState>

    @MockK(relaxed = true)
    lateinit var eventObserver: Observer<NameViewEvent>

    @InjectMockKs
    lateinit var viewModel: NameViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel.state.observeForever(stateObserver)
        viewModel.event.observeForever(eventObserver)
    }

    @Test
    fun emptyText_ShouldBeEmptyState() {
        val text = ""

        viewModel.onChangeText.invoke(text)

        val expectedState = NameViewState.EmptyName
        assertEquals(expectedState, viewModel.state.value)

        coVerify {
            stateObserver.onChanged(expectedState)
        }
    }

    @Test
    fun notEmptyText_ShouldBeContinueState() {
        val text = "David"

        viewModel.onChangeText.invoke(text)

        val expectedState = NameViewState.Continue
        assertEquals(expectedState, viewModel.state.value)

        coVerify {
            stateObserver.onChanged(expectedState)
        }
    }

    @Test
    fun onContinue_ShouldbeNextEvent(){
        val expectedEvent = NameViewEvent.Next
        viewModel.onContinue()

        assertEquals(expectedEvent, viewModel.event.value)
        coVerify {
            eventObserver.onChanged(expectedEvent)
        }
    }
}