/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.image.viewer

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.commons.ui.base.events.GeneralCommand
import es.littledavity.core.factories.ImageViewerContactUrlFactory
import es.littledavity.core.mapper.ErrorMapper
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.core.providers.StringProvider
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.core.utils.Logger
import es.littledavity.core.utils.onError
import es.littledavity.core.utils.resultOrError
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.usecases.GetContactUseCase
import es.littledavity.domain.contacts.usecases.SaveContactUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

internal const val PARAM_CONTACT_ID = "contact_id"
internal const val PARAM_PROFILE_VIEW = "profile_view"
internal const val PARAM_TITLE = "title"
internal const val PARAM_INITIAL_POSITION = "initial_position"
internal const val PARAM_IMAGE_URLS = "image_urls"
internal const val KEY_SELECTED_POSITION = "selected_position"

@HiltViewModel
internal class ImageViewerViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val savedStateHandle: SavedStateHandle,
    private val getContactUseCase: GetContactUseCase,
    private val saveContactUseCase: SaveContactUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val uiStateFactory: ImageViewerUiStateFactory,
    private val contactUrlFactory: ImageViewerContactUrlFactory,
    private val imageGalleryService: ImageGalleryService,
    private val logger: Logger,
    private val errorMapper: ErrorMapper,
) : BaseViewModel() {

    private val title: String = savedStateHandle.get<String>(PARAM_TITLE)
        ?: stringProvider.getString(R.string.image_viewer_default_toolbar_title)

    private val contactId: Int = savedStateHandle.get<Int>(PARAM_CONTACT_ID) ?: -1
    private val profileView: Boolean = savedStateHandle.get<Boolean>(PARAM_PROFILE_VIEW) ?: false

    private val _selectedPosition: MutableStateFlow<Int>
    private val _imageUrls: MutableStateFlow<List<String>>
    private val _toolbarTitle: MutableStateFlow<String>

    val selectedPosition: StateFlow<Int>
        get() = _selectedPosition

    val imageUrls: StateFlow<List<String>>
        get() = _imageUrls

    val toolbarTitle: StateFlow<String>
        get() = _toolbarTitle

    init {

        val selectedPosition = getSelectedPosition()
        val imageUrls = checkNotNull(savedStateHandle.get<Array<String>>(PARAM_IMAGE_URLS))

        _selectedPosition = MutableStateFlow(selectedPosition)
        _imageUrls = MutableStateFlow(imageUrls.toList())
        _toolbarTitle = MutableStateFlow("")

        observeSelectedPositionChanges()
    }

    private fun getSelectedPosition(): Int {
        return savedStateHandle.get(KEY_SELECTED_POSITION)
            ?: checkNotNull(savedStateHandle.get<Int>(PARAM_INITIAL_POSITION))
    }

    private fun observeSelectedPositionChanges() {
        selectedPosition
            .onEach {
                _toolbarTitle.value = updateToolbarTitle()
                savedStateHandle.set(KEY_SELECTED_POSITION, it)
            }
            .launchIn(viewModelScope)
    }

    private fun updateToolbarTitle(): String {
        if (imageUrls.value.size == 1) return title

        return stringProvider.getString(
            R.string.image_viewer_toolbar_title_template,
            title,
            (selectedPosition.value + 1),
            imageUrls.value.size
        )
    }

    fun onToolbarRightButtonClicked() {
        val currentImageUrl = imageUrls.value[selectedPosition.value]
        val textToShare = stringProvider.getString(
            R.string.text_sharing_message_template,
            stringProvider.getString(R.string.image),
            currentImageUrl
        )

        dispatchCommand(ImageViewerCommand.ShareText(textToShare))
    }

    fun onPageChanged(position: Int) {
        _selectedPosition.value = position
    }

    fun onBackPressed() {
        dispatchCommand(ImageViewerCommand.ResetSystemWindows)
        route(ImageViewerRoute.Back)
    }

    fun deleteImageUrl() {
        val currentImageUrl = imageUrls.value[selectedPosition.value]
        viewModelScope.launch {
            val contact = getContact().onError {
                logger.error(logTag, "Failed to get the contact.", it)
                dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
            }.firstOrNull() ?: return@launch
            if (profileView) {
                imageGalleryService.removeMediaFile(Uri.parse(contact.image?.id.orEmpty()))
                contact.image = null
            } else {
                imageGalleryService.removeMediaFile(Uri.parse(currentImageUrl))
                contact.gallery.remove(contact.gallery.find { it.id == currentImageUrl })
            }
            saveContactUseCase.execute(SaveContactUseCase.Params(contact))
                .map(::mapToUiState)
                .flowOn(dispatcherProvider.computation)
                .collect(::handleUiState)
        }
    }

    private suspend fun getContact(): Flow<Contact> = getContactUseCase.execute(
        GetContactUseCase.Params(contactId)
    ).resultOrError()

    private fun handleUiState(uiState: ImageViewerUiState) {
        when (uiState) {
            is ImageViewerUiState.Result -> handleResultState(uiState)
            else -> Unit
        }
    }

    private fun handleResultState(state: ImageViewerUiState.Result) {
        if (state.urls.isEmpty()) {
            onBackPressed()
        } else {
            _imageUrls.value = state.urls
            _toolbarTitle.value = updateToolbarTitle()
            savedStateHandle.set(KEY_SELECTED_POSITION, selectedPosition.value)
        }
    }

    private fun mapToUiState(contact: Contact?) = if (contact == null) {
        uiStateFactory.createWithErrorState(IllegalStateException())
    } else {
        uiStateFactory.createWithResultState(
            contactUrlFactory.createGalleryImageUrls(contact),
            selectedPosition.value
        )
    }
}
