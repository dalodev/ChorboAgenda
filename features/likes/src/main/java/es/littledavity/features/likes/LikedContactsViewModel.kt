/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.likes

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.base.events.GeneralCommand
import es.littledavity.commons.ui.widgets.contacts.ContactModel
import es.littledavity.commons.ui.widgets.contacts.ContactsUiState
import es.littledavity.core.mapper.ErrorMapper
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.core.utils.Logger
import es.littledavity.core.utils.onError
import es.littledavity.domain.commons.entities.hasDefaultLimit
import es.littledavity.domain.commons.entities.nextLimitPage
import es.littledavity.domain.contacts.commons.ObserveContactsUseCaseParams
import es.littledavity.domain.contacts.usecases.likes.ObserveLikedContactsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SUBSEQUENT_EMISSION_DELAY = 500L

@HiltViewModel
class LikedContactsViewModel @Inject constructor(
    private val observeLikedContactsUseCase: ObserveLikedContactsUseCase,
    private val uiStateFactory: LikedContactsUiStateFactory,
    private val dispatcherProvider: DispatcherProvider,
    private val errorMapper: ErrorMapper,
    private val logger: Logger
) : BaseViewModel() {

    private var isObservingContacts = false
    private var hasMoreContactsToLoad = false

    private var observeContactsUseCaseParams = ObserveContactsUseCaseParams()
    private var contactsObservingJob: Job? = null

    private val _uiState = MutableStateFlow(uiStateFactory.createWithEmptyState())

    val uiState: StateFlow<ContactsUiState>
        get() = _uiState

    fun loadData() {
        observeContacts()
    }

    private fun observeContacts() {
        if (isObservingContacts) return

        contactsObservingJob = viewModelScope.launch {
            observeLikedContactsUseCase.execute(observeContactsUseCaseParams)
                .map(uiStateFactory::createWithResultState)
                .flowOn(dispatcherProvider.computation)
                .onError {
                    logger.error(logTag, "failed to load liked contacts.", it)
                    dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
                    emit(uiStateFactory.createWithEmptyState())
                }
                .onStart {
                    isObservingContacts = true
                    emit(uiStateFactory.createWithLoadingState())
                    if (isSubsequentEmission()) delay(SUBSEQUENT_EMISSION_DELAY)
                }
                .onCompletion { isObservingContacts = false }
                .collect {
                    configureNextLoad(it)
                    _uiState.value = it
                }
        }
    }

    private fun isSubsequentEmission(): Boolean {
        return !observeContactsUseCaseParams.pagination.hasDefaultLimit()
    }

    private fun configureNextLoad(uiState: ContactsUiState) {
        if (uiState !is ContactsUiState.Result) return

        val paginationLimit = observeContactsUseCaseParams.pagination.limit
        val itemCount = uiState.items.size

        hasMoreContactsToLoad = (paginationLimit == itemCount)
    }

    fun onContactClicked(contact: ContactModel) {
        route(LikedContactsRoute.Info(contact.id))
    }

    fun onBottomReached() {
        observeNewContactsBatch()
    }

    private fun observeNewContactsBatch() {
        if (!hasMoreContactsToLoad) return

        observeContactsUseCaseParams = observeContactsUseCaseParams.copy(
            observeContactsUseCaseParams.pagination.nextLimitPage()
        )

        viewModelScope.launch {
            contactsObservingJob?.cancelAndJoin()
            observeContacts()
        }
    }

}
