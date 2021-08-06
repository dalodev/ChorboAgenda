/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.image.viewer

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import es.littledavity.testUtils.FakeStringProvider
import es.littledavity.testUtils.MainCoroutineRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val INITIAL_POSITION = 0

internal class ImageViewerViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ImageViewerViewModel

    @Before
    fun setup() {
        viewModel = ImageViewerViewModel(
            stringProvider = FakeStringProvider(),
            savedStateHandle = setupSavedStateHandle()
        )
    }

    private fun setupSavedStateHandle(): SavedStateHandle = mockk(relaxed = true) {
        every { get<String>(PARAM_TITLE) } returns ""
        every { get<Int>(PARAM_INITIAL_POSITION) } returns INITIAL_POSITION
        every { get<Array<String>>(PARAM_IMAGE_URLS) } returns arrayOf("", "", "")
        every { get<Int>(KEY_SELECTED_POSITION) } returns INITIAL_POSITION
    }

    @Test
    fun whenToolbarRightClicked_shouldDispatchTextSharing() = mainCoroutineRule.runBlockingTest {
        viewModel.commandFlow.test {
            viewModel.onToolbarRightButtonClicked()
            assertThat(awaitItem() is ImageViewerCommand.ShareText).isTrue
        }
    }

    @Test
    fun whenPageIsChanged_shouldEmitSelectedPosition() = mainCoroutineRule.runBlockingTest {
        viewModel.selectedPosition.test {
            viewModel.onPageChanged(10)
            assertThat(awaitItem() == INITIAL_POSITION).isTrue
            assertThat(awaitItem() == 10).isTrue
        }
    }

    @Test
    fun whenPageIsChanged_shouldEmitsToolbarTitle() = mainCoroutineRule.runBlockingTest {
        viewModel.toolbarTitle.test {
            viewModel.onPageChanged(10)
            assertThat(awaitItem()).isNotEmpty()
        }
    }

    @Test
    fun whenBackButtonIsClicked_shouldDispatchesSystemWindowsReset() = mainCoroutineRule.runBlockingTest {
        viewModel.commandFlow.test {
            viewModel.onBackPressed()
            assertThat(awaitItem() is ImageViewerCommand.ResetSystemWindows).isTrue
        }
    }

    @Test
    fun whenBackButtonIsClicked_shouldRoutesToPreviousScreen() = mainCoroutineRule.runBlockingTest {
        viewModel.routeFlow.test {
            viewModel.onBackPressed()
            assertThat(awaitItem() is ImageViewerRoute.Back).isTrue
        }
    }
}
