/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.add

import android.net.Uri
import android.util.Patterns
import app.cash.turbine.test
import es.littledavity.commons.ui.widgets.contacts.ContactModel
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.usecases.SaveContactUseCase
import es.littledavity.testUtils.DOMAIN_CONTACT
import es.littledavity.testUtils.FakeDispatcherProvider
import es.littledavity.testUtils.FakeErrorMapper
import es.littledavity.testUtils.FakeLogger
import es.littledavity.testUtils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddContactViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var saveContactUseCase: SaveContactUseCase

    private lateinit var logger: FakeLogger
    private lateinit var viewModel: AddContactViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        logger = FakeLogger()
        viewModel = AddContactViewModel(
            saveContactUseCase = saveContactUseCase,
            uiStateFactory = FakeUiStateFactory(),
            dispatcherProvider = FakeDispatcherProvider(),
            errorMapper = FakeErrorMapper(),
            logger = logger,
            permissionService = mockk()
        )
    }

    @Test
    fun onToolbarRightButtonClickedShouldGoBack() = mainCoroutineRule.runBlockingTest {
        viewModel.routeFlow.test {
            viewModel.onToolbarBackButtonClicked()
            assertThat(awaitItem() is AddContactRoute.Back).isTrue
        }
    }

    @Test
    fun onToolbarRightButtonClickedWithCantDoneShouldError() =
        mainCoroutineRule.runBlockingTest {
            viewModel.uiState.test {
                viewModel.onAddContactClicked("", "")
                assertThat(awaitItem() is AddContactUiState.New).isTrue
                assertThat(awaitItem() is AddContactUiState.Error).isTrue
            }
        }

    @Test
    fun updatePhotoShouldBeUiStateWithResult() = mainCoroutineRule.runBlockingTest {
        viewModel.uiState.test {
            val uri = mockk<Uri>()
            viewModel.updatePhoto(uri)
            assertThat(awaitItem() is AddContactUiState.New).isTrue
            assertThat((awaitItem() as AddContactUiState.Result).model.coverImageUrl).isEqualTo("test")
        }
    }

    @Test
    fun onToolbarRightButtonClickedWithCanDoneShouldSaveContact() =
        mainCoroutineRule.runBlockingTest {
            coEvery { saveContactUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACT)
            viewModel.uiState.test {
                viewModel.onAddContactClicked("test", "")
                assertThat(awaitItem() is AddContactUiState.New).isTrue
                assertThat(awaitItem() is AddContactUiState.Loading).isTrue
                assertThat((awaitItem() as AddContactUiState.Result).model.name).isEqualTo("test")
            }
        }

    private class FakeUiStateFactory : AddContactUiStateFactory {

        override fun createWithNewState(): AddContactUiState {
            return AddContactUiState.New
        }

        override fun createWithLoadingState(): AddContactUiState {
            return AddContactUiState.Loading
        }

        override fun createWithErrorState(nameError: Boolean, phoneError: Boolean): AddContactUiState {
            return AddContactUiState.Error(false, false)
        }

        override fun createWithPermissionError(navigation: () -> Unit): AddContactUiState {
            return AddContactUiState.ErrorPermission(navigation)
        }

        override fun createWithResultState(contact: Contact): AddContactUiState {
            return AddContactUiState.Result(
                ContactModel(
                    id = 1,
                    coverImageUrl = "test",
                    name = "test",
                    phone = "test",
                    creationDate = "test"
                )
            )
        }
    }
}
