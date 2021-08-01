/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.base.events.GeneralCommand
import es.littledavity.core.factories.ImageViewerContactUrlFactory
import es.littledavity.core.mapper.ErrorMapper
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.core.providers.StringProvider
import es.littledavity.core.utils.Logger
import es.littledavity.core.utils.combine
import es.littledavity.core.utils.onError
import es.littledavity.core.utils.resultOrError
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.usecases.GetContactUseCase
import es.littledavity.domain.contacts.usecases.ObserveContactLikeStateUseCase
import es.littledavity.domain.contacts.usecases.ToggleContactLikeStateUseCase
import es.littledavity.features.info.mapping.ContactInfoUiStateFactory
import es.littledavity.features.info.widgets.ContactInfoUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PARAM_CONTACT_ID = "contact_id"

@HiltViewModel
internal class ContactInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCases: ContactInfoUseCases,
    private val uiStateFactory: ContactInfoUiStateFactory,
    private val contactUrlFactory: ImageViewerContactUrlFactory,
    private val dispatcherProvider: DispatcherProvider,
    private val stringProvider: StringProvider,
    private val errorMapper: ErrorMapper,
    private val logger: Logger,
) : BaseViewModel() {

    private var isObservingContactData = false

    private val contactId = checkNotNull(savedStateHandle.get<Int>(PARAM_CONTACT_ID))

    private val _uiState = MutableStateFlow<ContactInfoUiState>(ContactInfoUiState.Empty)

    val uiState: StateFlow<ContactInfoUiState>
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
        getContact()
            .flatMapConcat { contact ->
                combine(
                    flowOf(contact),
                    observeContactLikeState(contact)
                )
            }
            .map { (contact, isContactLiked) ->
                uiStateFactory.createWithResultState(
                    contact,
                    isContactLiked
                )
            }
            .flowOn(dispatcherProvider.computation)
            .onError {
                logger.error(logTag, "Failed to load contact info data.", it)
                dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
                emit(uiStateFactory.createWithEmptyState())
            }
            .onStart {
                isObservingContactData = true
                emit(uiStateFactory.createWithLoadingState())
                delay(resultEmissionDelay)
            }
            .onCompletion { isObservingContactData = false }
            .collect { _uiState.value = it }
    }

    private suspend fun getContact(): Flow<Contact> = useCases.getContactUseCase.execute(
        GetContactUseCase.Params(contactId)
    ).resultOrError()

    private suspend fun observeContactLikeState(contact: Contact) = useCases.observeContactLikeStateUseCase
        .execute(ObserveContactLikeStateUseCase.Params(contact.id))

    fun onBackButtonClicked() = route(ContactInfoRoute.Back)

    fun onGalleryClicked(position: Int) {
        navigateToImageViewer(
            title = stringProvider.getString(R.string.gallery),
            initialPosition = position,
            contactImageUrlsProvider = contactUrlFactory::createGalleryImageUrls
        )
    }

    private fun navigateToImageViewer(
        title: String,
        initialPosition: Int = 0,
        contactImageUrlsProvider: (Contact) -> List<String>
    ) {
        viewModelScope.launch {
            val contact = getContact()
                .onError {
                    logger.error(logTag, "Failed to get the game.", it)
                    dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
                }.firstOrNull() ?: return@launch

            val images = contactImageUrlsProvider(contact)
                .takeIf(List<String>::isNotEmpty) ?: return@launch
            route(ContactInfoRoute.ImageViewer(title, initialPosition, images))
        }
    }

    fun onCoverClicked() {
        navigateToImageViewer(
            title = stringProvider.getString(R.string.cover),
            contactImageUrlsProvider = { game ->
                contactUrlFactory.createCoverImageUrl(game)
                    ?.let(::listOf)
                    ?: emptyList()
            }
        )
    }

    fun onLikeButtonClicked() {
        viewModelScope.launch {
            useCases.toggleContactLikeStateUseCase
                .execute(ToggleContactLikeStateUseCase.Params(contactId))
        }
    }
}
