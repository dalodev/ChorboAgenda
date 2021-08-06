package es.littledavity.features.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.base.events.GeneralCommand
import es.littledavity.core.mapper.ErrorMapper
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.core.utils.Logger
import es.littledavity.core.utils.onError
import es.littledavity.core.utils.resultOrError
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.usecases.GetContactUseCase
import es.littledavity.features.edit.widgets.mapping.EditContactUiStateFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PARAM_CONTACT_ID = "contact_id"

@HiltViewModel
internal class EditContactViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getContactUseCase: GetContactUseCase,
    private val uiStateFactory: EditContactUiStateFactory,
    private val dispatcherProvider: DispatcherProvider,
    private val logger: Logger,
    private val errorMapper: ErrorMapper
) : BaseViewModel() {

    private var isObservingContactData = false

    private val contactId = checkNotNull(savedStateHandle.get<Int>(PARAM_CONTACT_ID))

    private val _uiState = MutableStateFlow<EditContactUiState>(EditContactUiState.Loading)

    internal val uiState: StateFlow<EditContactUiState>
        get() = _uiState

    fun loadData(resultEmissionDelay: Long) {
        observeContactData(resultEmissionDelay)
    }

    private fun observeContactData(resultEmissionDelay: Long) {
        if (isObservingContactData) return
        viewModelScope.launch {
            observeContactDataInternal(resultEmissionDelay)
        }
    }

    private suspend fun observeContactDataInternal(resultEmissionDelay: Long) {
        getContact().map { contact ->
            uiStateFactory.createWithResultState(
                contact
            )
        }
            .flowOn(dispatcherProvider.computation)
            .onError {
                logger.error(logTag, "Failed to load contact info data.", it)
                dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
                emit(uiStateFactory.createWithErrorState())
            }
            .onStart {
                isObservingContactData = true
                emit(uiStateFactory.createWithLoadingState())
                delay(resultEmissionDelay)
            }
            .onCompletion { isObservingContactData = false }
            .collect { _uiState.value = it }
    }

    private suspend fun getContact(): Flow<Contact> = getContactUseCase.execute(
        GetContactUseCase.Params(contactId)
    ).resultOrError()

    fun onBackButtonClicked() = route(EditContactRoute.Back)

}