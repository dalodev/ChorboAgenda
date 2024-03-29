/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import es.littledavity.commons.ui.base.events.GeneralCommand
import es.littledavity.core.factories.ImageViewerContactUrlFactory
import es.littledavity.domain.DomainContact
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.features.info.mapping.ContactInfoUiStateFactory
import es.littledavity.features.info.widgets.ContactInfoUiState
import es.littledavity.features.info.widgets.model.ContactInfoHeaderModel
import es.littledavity.features.info.widgets.model.ContactInfoModel
import es.littledavity.testUtils.DOMAIN_CONTACT
import es.littledavity.testUtils.DOMAIN_ERROR_API
import es.littledavity.testUtils.DOMAIN_ERROR_NOT_FOUND
import es.littledavity.testUtils.FakeDispatcherProvider
import es.littledavity.testUtils.FakeErrorMapper
import es.littledavity.testUtils.FakeLogger
import es.littledavity.testUtils.FakeStringProvider
import es.littledavity.testUtils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ContactInfoViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var useCases: ContactInfoUseCases
    private lateinit var logger: FakeLogger
    private lateinit var viewModel: ContactInfoViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCases = setupUseCases()
        logger = FakeLogger()
        viewModel = ContactInfoViewModel(
            useCases = useCases,
            uiStateFactory = FakeContactInfoUiStateFactory(),
            contactUrlFactory = FakeContactUrlFactory(),
            dispatcherProvider = FakeDispatcherProvider(),
            stringProvider = FakeStringProvider(),
            errorMapper = FakeErrorMapper(),
            logger = logger,
            savedStateHandle = setupSavedStateHandle(),
            permissionService = mockk(),
        )
    }

    private fun setupUseCases() = ContactInfoUseCases(
        getContactUseCase = mockk(),
        observeContactLikeStateUseCase = mockk {
            coEvery { execute(any()) } returns flowOf(true)
        },
        toggleContactLikeStateUseCase = mockk(),
        saveContactUseCase = mockk()
    )

    private fun setupSavedStateHandle(): SavedStateHandle = mockk(relaxed = true) {
        every { get<Int>(any()) } returns 1
    }

    @Test
    fun whenLoadingData_shouldEmitsCorrectUiStates() = mainCoroutineRule.runBlockingTest {
        coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))
        coEvery { useCases.saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
        viewModel.uiState.test {
            viewModel.loadData(resultEmissionDelay = 0L)
            assertThat(awaitItem() is ContactInfoUiState.Empty).isTrue
            assertThat(awaitItem() is ContactInfoUiState.Loading).isTrue
            assertThat(awaitItem() is ContactInfoUiState.Result).isTrue
        }
    }

    @Test
    fun whenGameFetchingUseCase_throwsError_shouldLogsError() = mainCoroutineRule.runBlockingTest {
        coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Err(DOMAIN_ERROR_API))
        coEvery { useCases.saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
        viewModel.loadData(resultEmissionDelay = 0L)
        assertThat(logger.errorMessage).isNotEmpty
    }

    @Test
    fun whenGameFetchingUseCase_throwsError_shouldDispatchesToastCommand() =
        mainCoroutineRule.runBlockingTest {
            coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(
                Err(
                    DOMAIN_ERROR_NOT_FOUND
                )
            )
            coEvery { useCases.saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
            viewModel.commandFlow.test {
                viewModel.loadData(resultEmissionDelay = 0L)
                assertThat(awaitItem() is GeneralCommand.ShowLongToast).isTrue
            }
        }

    @Test
    fun whenGalleryIsClicked_shouldRoutesToImageViewer() = mainCoroutineRule.runBlockingTest {
        viewModel.routeFlow.test {
            coEvery { useCases.saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
            coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))
            viewModel.onGalleryClicked(position = 0)
            assertThat(awaitItem() is ContactInfoRoute.ImageViewer).isTrue
        }
    }

    @Test
    fun whenBackButtonIsClicked_shouldRouteToPrevious() = mainCoroutineRule.runBlockingTest {
        coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))
        coEvery { useCases.saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
        viewModel.loadData(0L)
        viewModel.routeFlow.test {
            viewModel.onBackButtonClicked()
            assertThat(awaitItem() is ContactInfoRoute.Back).isTrue
        }
    }

    @Test
    fun whenImageIsClicked_shouldRoutesToImageViewer() = mainCoroutineRule.runBlockingTest {
        coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))
        coEvery { useCases.saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
        viewModel.loadData(0L)
        viewModel.routeFlow.test {
            viewModel.onImageClicked()
            assertThat(awaitItem() is ContactInfoRoute.ImageViewer).isTrue
        }
    }

    @Test
    fun whenOnBackPressed_shouldSaveContactAndBack() = mainCoroutineRule.runBlockingTest {
        coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))
        coEvery { useCases.saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
        viewModel.loadData(0L)
        viewModel.routeFlow.test {
            viewModel.onBackPressed()
            assertThat(awaitItem() is ContactInfoRoute.Back).isTrue
        }
    }

    @Test
    fun onLikeButtonClicked_shouldUpdateUiState() = mainCoroutineRule.runBlockingTest {
        coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))
        coEvery { useCases.saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
        coEvery { useCases.toggleContactLikeStateUseCase.execute(any()) } returns Unit
        coEvery { useCases.observeContactLikeStateUseCase.execute(any()) } returns flowOf(false)
        viewModel.uiState.test {
            viewModel.onLikeButtonClicked()
            assertThat(awaitItem() is ContactInfoUiState.Result).isFalse()
        }
    }

    @Test
    fun updatePhoto_shouldSaveCurrentContactAndUpdateUiState() = mainCoroutineRule.runBlockingTest {
        coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))
        coEvery { useCases.saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
        viewModel.uiState.test {
            viewModel.loadData(0L)
            assertThat(awaitItem() is ContactInfoUiState.Empty).isTrue
            assertThat(awaitItem() is ContactInfoUiState.Loading).isTrue
            assertThat(awaitItem() is ContactInfoUiState.Result).isTrue
            viewModel.updatePhoto(mockk())
            assertThat(awaitItem() is ContactInfoUiState.Loading).isTrue
            assertThat(awaitItem() is ContactInfoUiState.Result).isTrue
        }
    }

    @Test
    fun addGalleryImage_shouldSaveCurrentContactAndUpdateUiState() {
        mainCoroutineRule.runBlockingTest {
            coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))
            coEvery { useCases.saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
            viewModel.uiState.test {
                viewModel.loadData(0L)
                assertThat(awaitItem() is ContactInfoUiState.Empty).isTrue
                assertThat(awaitItem() is ContactInfoUiState.Loading).isTrue
                assertThat(awaitItem() is ContactInfoUiState.Result).isTrue
                viewModel.addGalleryImage(mockk())
                assertThat(awaitItem() is ContactInfoUiState.Loading).isTrue
                assertThat(awaitItem() is ContactInfoUiState.Result).isTrue
            }
        }
    }

    @Test
    fun updateContactData_shouldSaveCurrentContact() =
        mainCoroutineRule.runBlockingTest {
            coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))
            coEvery { useCases.saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
            viewModel.uiState.test {
                viewModel.loadData(0L)
                assertThat(awaitItem() is ContactInfoUiState.Empty).isTrue
                assertThat(awaitItem() is ContactInfoUiState.Loading).isTrue
                assertThat(awaitItem() is ContactInfoUiState.Result).isTrue
                viewModel.updateContactData(
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    "test",
                    null,
                    false
                )
            }
        }

    private class FakeContactInfoUiStateFactory : ContactInfoUiStateFactory {

        override fun createWithEmptyState(): ContactInfoUiState {
            return ContactInfoUiState.Empty
        }

        override fun createWithLoadingState(): ContactInfoUiState {
            return ContactInfoUiState.Loading
        }

        override fun createWithResultState(
            contact: DomainContact,
            isLiked: Boolean
        ): ContactInfoUiState {
            return ContactInfoUiState.Result(
                ContactInfoModel(
                    id = 1,
                    headerModel = ContactInfoHeaderModel(
                        backgroundImageModels = emptyList(),
                        isLiked = true,
                        rating = "rating",
                        age = "18",
                        country = "Spain",
                        instagram = "@test",
                        creationDate = "creation_date",
                        name = "name",
                        imageUrl = null,
                        phone = "123",
                    ),
                    info = emptyList()
                )
            )
        }

        override fun createWithPermissionError(navigation: () -> Unit): ContactInfoUiState {
            return ContactInfoUiState.ErrorPermission(navigation)
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
