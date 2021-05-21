/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.search

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import es.littledavity.commons.ui.widgets.contacts.ContactModel
import es.littledavity.commons.ui.widgets.contacts.ContactsUiState
import es.littledavity.domain.contacts.Contact
import es.littledavity.domain.contacts.usecases.SearchContactsUseCase
import es.littledavity.testUtils.FakeDispatcherProvider
import es.littledavity.testUtils.FakeErrorMapper
import es.littledavity.testUtils.FakeLogger
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class ContactsSearchViewModelTest {

    private lateinit var viewModel: ContactsSearchViewModel

    @MockK
    private lateinit var searchContactsUseCase: SearchContactsUseCase
    private lateinit var logger: FakeLogger

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = ContactsSearchViewModel(
            searchContactsUseCase = searchContactsUseCase,
            uiStateFactory = FakeGamesSearchUiStateFactory(),
            dispatcherProvider = FakeDispatcherProvider(),
            errorMapper = FakeErrorMapper(),
            logger = logger,
            savedStateHandle = setupSavedStateHandle()
        )
    }

    private fun setupSavedStateHandle(): SavedStateHandle {
        return mockk(relaxed = true) {
            every { get<String>(any()) } returns ""
        }
    }

    @Test
    fun onToolbarBackButtonClicked_shouldRoute() = runBlocking {

        viewModel.routeFlow.test {
            Assertions.assertThat(expectItem() is ContactsSearchRoute.Back).isTrue
        }
    }

    private class FakeGamesSearchUiStateFactory : ContactsSearchUiStateFactory {

        override fun createWithEmptyState(searchQuery: String): ContactsUiState {
            return ContactsUiState.Empty(iconId = -1, title = "title")
        }

        override fun createWithLoadingState(): ContactsUiState {
            return ContactsUiState.Loading
        }

        override fun createWithResultState(contacts: List<Contact>): ContactsUiState {
            return ContactsUiState.Result(
                contacts.map {
                    ContactModel(
                        id = it.id,
                        image = it.image,
                        name = it.name,
                        phone = it.phone
                    )
                }
            )
        }

    }

}
