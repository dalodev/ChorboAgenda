/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.image.viewer

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.github.michaelbull.result.Err
import es.littledavity.commons.ui.base.events.GeneralCommand
import es.littledavity.core.factories.ImageViewerContactUrlFactory
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.domain.DomainContact
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.usecases.GetContactUseCase
import es.littledavity.domain.contacts.usecases.SaveContactUseCase
import es.littledavity.testUtils.DOMAIN_ERROR_API
import es.littledavity.testUtils.FakeDispatcherProvider
import es.littledavity.testUtils.FakeErrorMapper
import es.littledavity.testUtils.FakeLogger
import es.littledavity.testUtils.FakeStringProvider
import es.littledavity.testUtils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val INITIAL_POSITION = 0

internal class ImageViewerViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var imageGalleryService: ImageGalleryService

    @MockK
    private lateinit var getContactUseCase: GetContactUseCase

    @MockK
    private lateinit var saveContactUseCase: SaveContactUseCase

    private lateinit var viewModel: ImageViewerViewModel
    private lateinit var logger: FakeLogger

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        logger = FakeLogger()
        viewModel = ImageViewerViewModel(
            stringProvider = FakeStringProvider(),
            savedStateHandle = setupSavedStateHandle(),
            getContactUseCase = getContactUseCase,
            saveContactUseCase = saveContactUseCase,
            dispatcherProvider = FakeDispatcherProvider(),
            uiStateFactory = FakeImageViewerUiStateFactory(),
            contactUrlFactory = FakeContactUrlFactory(),
            imageGalleryService = imageGalleryService,
            logger = logger,
            errorMapper = FakeErrorMapper(),
        )
    }

    private fun setupSavedStateHandle(): SavedStateHandle = mockk(relaxed = true) {
        every { get<String>(PARAM_TITLE) } returns ""
        every { get<Int>(PARAM_INITIAL_POSITION) } returns INITIAL_POSITION
        every { get<Array<String>>(PARAM_IMAGE_URLS) } returns arrayOf("", "", "")
        every { get<Int>(KEY_SELECTED_POSITION) } returns INITIAL_POSITION
        every { get<Int>(PARAM_CONTACT_ID) } returns 1
        every { get<Boolean>(PARAM_PROFILE_VIEW) } returns false
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
    fun whenBackButtonIsClicked_shouldDispatchesSystemWindowsReset() =
        mainCoroutineRule.runBlockingTest {
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

    @Test
    fun whenDeleteImageUrl_onErrorGet_shouldLogsError() = mainCoroutineRule.runBlockingTest {
        coEvery { getContactUseCase.execute(any()) } returns flowOf(Err(DOMAIN_ERROR_API))
        viewModel.deleteImageUrl()
        assertThat(logger.errorMessage).isNotEmpty
    }

    @Test
    fun whenDeleteImageUrl_onErrorGet_shouldDispatchCommand() = mainCoroutineRule.runBlockingTest {
        coEvery { getContactUseCase.execute(any()) } returns flowOf(Err(DOMAIN_ERROR_API))
        viewModel.commandFlow.test {
            viewModel.deleteImageUrl()
            assertThat(awaitItem() is GeneralCommand.ShowLongToast).isTrue
        }
    }

    private class FakeImageViewerUiStateFactory : ImageViewerUiStateFactory {

        override fun createWithLoadingState(): ImageViewerUiState {
            return ImageViewerUiState.Loading
        }

        override fun createWithErrorState(throwable: Throwable): ImageViewerUiState {
            return ImageViewerUiState.Error(throwable)
        }

        override fun createWithResultState(urls: List<String>, position: Int): ImageViewerUiState {
            return ImageViewerUiState.Result(
                urls = listOf("asd", "sdas"),
                selectedPosition = 1
            )
        }
    }

    private class FakeContactUrlFactory : ImageViewerContactUrlFactory {

        override fun createCoverImageUrl(contact: DomainContact): String {
            return "cover_image_url"
        }

        override fun createGalleryImageUrls(contact: DomainContact): List<String> {
            return listOf("url", "url", "url")
        }

        override fun createScreenShotImageUrls(contact: Contact): List<String> {
            return listOf("url", "url", "url")
        }
    }
}
