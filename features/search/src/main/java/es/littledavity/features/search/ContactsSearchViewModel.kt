package es.littledavity.features.search

import androidx.lifecycle.SavedStateHandle
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
import es.littledavity.domain.commons.entities.Pagination
import es.littledavity.domain.commons.entities.nextOffsetPage
import es.littledavity.domain.contacts.Contact
import es.littledavity.domain.contacts.usecases.SearchContactsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val KEY_SEARCH_QUERY = "search_query"

@HiltViewModel
internal class ContactsSearchViewModel @Inject constructor(
    private val searchUseCase: SearchContactsUseCase,
    private val uiStateFactory: ContactsSearchUiStateFactory,
    private val dispatcherProvider: DispatcherProvider,
    private val errorMapper: ErrorMapper,
    private val logger: Logger,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private var hasMoreContactsToLoad = false

    private var useCaseParams = SearchContactsUseCase.Params(searchQuery = "")

    private var searchQuery: String
        set(value) {
            useCaseParams = useCaseParams.copy(searchQuery = value)
            savedStateHandle.set(KEY_SEARCH_QUERY, value)
        }
        get() = useCaseParams.searchQuery

    private var pagination: Pagination
        set(value) {
            useCaseParams = useCaseParams.copy(pagination = value)
        }
        get() = useCaseParams.pagination

    private var totalContactsResult: ContactsUiState.Result? = null
    private val _uiState = MutableStateFlow(createEmptyContactsUiState())

    val uiState: StateFlow<ContactsUiState>
        get() = _uiState


    init {
        onSearchActionRequested(savedStateHandle.get(KEY_SEARCH_QUERY) ?: "")
    }

    private fun createEmptyContactsUiState(): ContactsUiState {
        return uiStateFactory.createWithEmptyState(searchQuery)
    }

    fun onToolbarBackButtonClicked() {
        route(ContactsSearchRoute.Back)
    }

    fun onSearchActionRequested(query: String) {
        if (query.isEmpty() || (searchQuery == query)) return
        searchQuery = query
        resetPagination()
        searchContacts()
    }

    private fun resetPagination() {
        pagination = Pagination()
        totalContactsResult = null
    }

    private fun searchContacts() = viewModelScope.launch {
        if (searchQuery.orEmpty().isBlank()) {
            flowOf(createEmptyContactsUiState())
        } else {
            searchUseCase.execute(useCaseParams)
                .map(::mapToUiState)
                .flowOn(dispatcherProvider.computation)
                .onError {
                    logger.error(logTag, "Failed to search games.", it)
                    dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
                    emit(createEmptyContactsUiState())
                }
                .onStart {
                    if (isPerformingNewSearch()) {
                        dispatchCommand(ContactsSearchCommand.ClearItems)
                    }

                    emit(uiStateFactory.createWithLoadingState())
                }
                .map(::aggregateResults)
        }.collect {
            configureNextLoad(it)
            updateTotalContactsResult(it)
            _uiState.value = it
        }
    }

    private fun mapToUiState(contacts: List<Contact>): ContactsUiState {
        return if (contacts.isEmpty()) {
            createEmptyContactsUiState()
        } else {
            uiStateFactory.createWithResultState(contacts)
        }
    }

    private fun isPerformingNewSearch() = totalContactsResult == null

    private fun aggregateResults(uiState: ContactsUiState): ContactsUiState {
        if ((uiState !is ContactsUiState.Result) || (totalContactsResult == null)) {
            return uiState
        }
        val oldItems = checkNotNull(totalContactsResult).items
        val newItems = uiState.items

        return ContactsUiState.Result(oldItems + newItems)
    }

    private fun configureNextLoad(uiState: ContactsUiState) {
        if (uiState !is ContactsUiState.Result) return

        val paginationLimit = useCaseParams.pagination.limit
        val itemCount = uiState.items.size

        hasMoreContactsToLoad = ((itemCount % paginationLimit) == 0)
    }

    private fun updateTotalContactsResult(uiState: ContactsUiState) {
        if (uiState !is ContactsUiState.Result) return
        totalContactsResult = uiState
    }

    fun onContactClicked(contact: ContactModel) {
        route(ContactsSearchRoute.Info(contact.id))
    }

    fun onBottomReached() {
        loadMoreGames()
    }

    private fun loadMoreGames() {
        if (!hasMoreContactsToLoad) return

        pagination = pagination.nextOffsetPage()
        searchContacts()
    }
}