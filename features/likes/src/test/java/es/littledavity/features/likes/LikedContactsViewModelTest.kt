/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.likes

import app.cash.turbine.test
import es.littledavity.commons.ui.base.events.GeneralCommand
import es.littledavity.commons.ui.widgets.contacts.ContactModel
import es.littledavity.commons.ui.widgets.contacts.ContactsUiState
import es.littledavity.domain.DomainContact
import es.littledavity.domain.contacts.usecases.likes.ObserveLikedContactsUseCase
import es.littledavity.testUtils.DOMAIN_CONTACTS
import es.littledavity.testUtils.FakeDispatcherProvider
import es.littledavity.testUtils.FakeErrorMapper
import es.littledavity.testUtils.FakeLogger
import es.littledavity.testUtils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class LikedContactsViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: LikedContactsViewModel

    @MockK
    private lateinit var observeLikedContactsUseCase: ObserveLikedContactsUseCase

    private lateinit var logger: FakeLogger

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        logger = FakeLogger()
        viewModel = LikedContactsViewModel(
            observeLikedContactsUseCase = observeLikedContactsUseCase,
            uiStateFactory = FakeUiStateFactory(),
            dispatcherProvider = FakeDispatcherProvider(),
            errorMapper = FakeErrorMapper(),
            logger = logger
        )
    }

    @Test
    fun whenLoadData_souldCorrectUiStates() = mainCoroutineRule.runBlockingTest {
        coEvery { observeLikedContactsUseCase.execute(any()) } returns flowOf(DOMAIN_CONTACTS)
        viewModel.uiState.test {
            viewModel.loadData()
            assertThat(awaitItem() is ContactsUiState.Empty).isTrue
            assertThat(awaitItem() is ContactsUiState.Loading).isTrue
            assertThat(awaitItem() is ContactsUiState.Result).isTrue
        }
    }

    @Test
    fun logsErrorWhenLikesContactsLoadingFails() = mainCoroutineRule.runBlockingTest {
        coEvery { observeLikedContactsUseCase.execute(any()) } returns flow { throw Exception("Error") }
        viewModel.loadData()
        assertThat(logger.errorMessage).isNotEmpty
    }

    @Test
    fun dispatchesToastShowingCommandWhenLikesContactsLoadingFails() =
        mainCoroutineRule.runBlockingTest {
            coEvery { observeLikedContactsUseCase.execute(any()) } returns flow { throw Exception("Error") }
            viewModel.commandFlow.test {
                viewModel.loadData()
                assertThat(awaitItem() is GeneralCommand.ShowLongToast).isTrue
            }
        }

    @Test
    fun routeToInfoScreenWhenLikeContactIsClicked() = mainCoroutineRule.runBlockingTest {
        viewModel.routeFlow.test {
            viewModel.onContactClicked(
                ContactModel(
                    id = 1,
                    name = "name",
                    coverImageUrl = "image",
                    phone = "1234123",
                    creationDate = ""
                )
            )
            val route = awaitItem()
            assertThat(route is LikedContactsRoute.Info).isTrue
        }
    }

    private class FakeUiStateFactory :
        LikedContactsUiStateFactory {

        override fun createWithEmptyState(): ContactsUiState {
            return ContactsUiState.Empty(iconId = -1, title = "title")
        }

        override fun createWithLoadingState(): ContactsUiState {
            return ContactsUiState.Loading
        }

        override fun createWithResultState(contacts: List<DomainContact>): ContactsUiState {
            return ContactsUiState.Result(
                contacts.map {
                    ContactModel(
                        id = it.id,
                        name = it.name,
                        coverImageUrl = it.image?.id,
                        phone = it.phone,
                        creationDate = "test"
                    )
                }
            )
        }
    }
}
