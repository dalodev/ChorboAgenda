/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.contacts

import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.widgets.contacts.ContactModel
import es.littledavity.commons.ui.widgets.contacts.ContactsUiState
import es.littledavity.domain.contacts.commons.ObserveContactsUseCaseParams
import es.littledavity.domain.contacts.usecases.ObserveContactsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val MAX_CONTACTS_COUNT = 100

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val observeContactsUseCase: ObserveContactsUseCase,
    private val uiStateFactory: ContactsUiStateFactory,
) : BaseViewModel() {

    private var isObservingContacts = false
    private var hasMoreContactsToLoad = false
    private var observeContactsUseCaseParams = ObserveContactsUseCaseParams()

    private var gamesObservingJob: Job? = null

    private val _uiState = MutableStateFlow(uiStateFactory.createWithEmptyState())
    val uiState: StateFlow<ContactsUiState>
        get() = _uiState

    fun loadData() {
        observeContacts()
    }

    private fun observeContacts() {
        if(isObservingContacts) return
        //TODO
    }

    fun onContactClicked(game: ContactModel) {
        route(ContactsRoute.Info(game.id))
    }

    fun onBottomReached() {
//        observeNewContactsBatch()
    }

}
