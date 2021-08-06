/*
 * Copyright 2021 dev.id
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
import es.littledavity.features.info.widgets.main.model.ContactInfoHeaderModel
import es.littledavity.features.info.widgets.main.model.ContactInfoModel
import es.littledavity.testUtils.DOMAIN_CONTACT
import es.littledavity.testUtils.DOMAIN_ERROR_API
import es.littledavity.testUtils.DOMAIN_ERROR_NOT_FOUND
import es.littledavity.testUtils.FakeDispatcherProvider
import es.littledavity.testUtils.FakeErrorMapper
import es.littledavity.testUtils.FakeLogger
import es.littledavity.testUtils.FakeStringProvider
import es.littledavity.testUtils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ContactInfoViewModelTest {

    @get:Rule
    val mmainCoroutineRule = MainCoroutineRule()

    private lateinit var useCases: ContactInfoUseCases
    private lateinit var logger: FakeLogger
    private lateinit var viewModel: ContactInfoViewModel

    @Before
    fun setup() {
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
            permissionService = mockk()
        )
    }

    private fun setupUseCases() = ContactInfoUseCases(
        getContactUseCase = mockk(),
        observeContactLikeStateUseCase = mockk {
            coEvery { execute(any()) } returns flowOf(true)
        },
        toggleContactLikeStateUseCase = mockk()
    )

    private fun setupSavedStateHandle(): SavedStateHandle = mockk(relaxed = true) {
        every { get<Int>(any()) } returns 1
    }

    @Test
    fun whenLoadingData_shouldEmitsCorrectUiStates() = mmainCoroutineRule.runBlockingTest {
        coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))
        viewModel.uiState.test {
            viewModel.loadData(resultEmissionDelay = 0L)
            assertThat(awaitItem() is ContactInfoUiState.Empty).isTrue
            assertThat(awaitItem() is ContactInfoUiState.Loading).isTrue
            assertThat(awaitItem() is ContactInfoUiState.Result).isTrue
        }
    }

    @Test
    fun whenGameFetchingUseCase_throwsError_shouldLogsError() = mmainCoroutineRule.runBlockingTest {
        coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Err(DOMAIN_ERROR_API))
        viewModel.loadData(resultEmissionDelay = 0L)
        assertThat(logger.errorMessage).isNotEmpty
    }

    @Test
    fun whenGameFetchingUseCase_throwsError_shouldDispatchesToastCommand() = mmainCoroutineRule.runBlockingTest {
        coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Err(DOMAIN_ERROR_NOT_FOUND))
        viewModel.commandFlow.test {
            viewModel.loadData(resultEmissionDelay = 0L)
            assertThat(awaitItem() is GeneralCommand.ShowLongToast).isTrue
        }
    }

    @Test
    fun whenGalleryIsClicked_shouldRoutesToImageViewer() = mmainCoroutineRule.runBlockingTest {
        viewModel.routeFlow.test {
            coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))
            viewModel.onGalleryClicked(position = 0)
            assertThat(awaitItem() is ContactInfoRoute.ImageViewer).isTrue
        }
    }

    @Test
    fun whenBackButtonIsClicked_shouldRouteToPrevious() = mmainCoroutineRule.runBlockingTest {
        viewModel.routeFlow.test {
            viewModel.onBackButtonClicked()
            assertThat(awaitItem() is ContactInfoRoute.Back).isTrue
        }
    }

    @Test
    fun whenImageIsClicked_shouldRoutesToImageViewer() = mmainCoroutineRule.runBlockingTest {
        coEvery { useCases.getContactUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_CONTACT))

        viewModel.routeFlow.test {
            viewModel.onImageClicked()
            assertThat(awaitItem() is ContactInfoRoute.ImageViewer).isTrue
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
