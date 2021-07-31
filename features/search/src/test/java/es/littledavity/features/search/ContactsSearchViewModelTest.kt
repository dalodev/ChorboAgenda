/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.search

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import es.littledavity.commons.ui.widgets.contacts.ContactModel
import es.littledavity.commons.ui.widgets.contacts.ContactsModelMapper
import es.littledavity.commons.ui.widgets.contacts.ContactsUiState
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.usecases.SearchContactsUseCase
import es.littledavity.testUtils.FakeDispatcherProvider
import es.littledavity.testUtils.FakeErrorMapper
import es.littledavity.testUtils.FakeLogger
import es.littledavity.testUtils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runBlockingTest

internal class ContactsSearchViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ContactsSearchViewModel

    @MockK
    private lateinit var searchContactsUseCase: SearchContactsUseCase
    private lateinit var logger: FakeLogger

    @MockK
    private lateinit var contactsModelMapper: ContactsModelMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        logger = FakeLogger()
        viewModel = ContactsSearchViewModel(
            searchUseCase = searchContactsUseCase,
            uiStateFactory = FakecontactsSearchUiStateFactory(contactsModelMapper),
            dispatcherProvider = FakeDispatcherProvider(),
            errorMapper = FakeErrorMapper(),
            logger = logger,
            savedStateHandle = setupSavedStateHandle()
        )
    }

    private fun setupSavedStateHandle(): SavedStateHandle = mockk(relaxed = true) {
        every { get<String>(any()) } returns ""
    }

    @Test
    fun onToolbarBackButtonClicked_shouldRoute() = mainCoroutineRule.runBlockingTest {

        viewModel.routeFlow.test {
            viewModel.onToolbarBackButtonClicked()
            assertThat(expectItem() is ContactsSearchRoute.Back).isTrue
        }
    }

    private class FakecontactsSearchUiStateFactory(val contactsModelMapper: ContactsModelMapper) :
        ContactsSearchUiStateFactory {

        override fun createWithEmptyState(searchQuery: String) =
            ContactsUiState.Empty(iconId = -1, title = "title")

        override fun createWithLoadingState() = ContactsUiState.Loading

        override fun createWithResultState(contacts: List<Contact>) = ContactsUiState.Result(
            contacts.map(
                contactsModelMapper::mapToContactModel
            )
        )
    }
}
