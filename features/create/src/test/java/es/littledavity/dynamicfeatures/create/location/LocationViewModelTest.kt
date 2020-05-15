package es.littledavity.dynamicfeatures.create.location

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.debut.countrycodepicker.data.Country
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocationViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<LocationViewState>

    @MockK(relaxed = true)
    lateinit var eventObserver: Observer<LocationViewEvent>

    @MockK(relaxed = true)
    lateinit var countryFlagObserver: Observer<Drawable>

    @MockK(relaxed = true)
    lateinit var countryNameObserver: Observer<String>

    @InjectMockKs
    lateinit var viewModel: LocationViewModel

    @MockK
    lateinit var context: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel.state.observeForever(stateObserver)
        viewModel.event.observeForever(eventObserver)
        viewModel.countryName.observeForever(countryNameObserver)
        viewModel.countryFlag.observeForever(countryFlagObserver)
    }

    @Test
    fun onSelectCountry_ShouldBeCountryPickerEvent() {
        val expectedEvent = LocationViewEvent.CountryPicker

        viewModel.onSelectCountry()
        assertEquals(expectedEvent, viewModel.event.value)

        coVerify {
            eventObserver.onChanged(expectedEvent)
        }
    }

    @Test
    fun onLoadCountry_shouldBeContinueStateAndSetData() {
        every {
            context.resources
        } returns mockk()
        val country = Country(
            name = "test",
            countryCode = "test",
            flag = mockk()
        )
        val expectedState = LocationViewState.Continue
        val expectedCountryName = "+${country.countryCode}  ${country.name}"

        viewModel.loadCountry(country)
        assertEquals(expectedState, viewModel.state.value)
        assertEquals(expectedCountryName, viewModel.countryName.value)

        coVerify {
            stateObserver.onChanged(expectedState)
            countryNameObserver.onChanged(expectedCountryName)
        }
    }

    @Test
    fun onContinue_ShouldbeNextEvent() {
        val expectedEvent = LocationViewEvent.Next
        viewModel.onContinue()

        assertEquals(expectedEvent, viewModel.event.value)
        coVerify {
            eventObserver.onChanged(expectedEvent)
        }
    }
}