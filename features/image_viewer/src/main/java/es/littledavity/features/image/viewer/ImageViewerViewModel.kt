/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.image.viewer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.core.providers.StringProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

internal const val PARAM_TITLE = "title"
internal const val PARAM_INITIAL_POSITION = "initial_position"
internal const val PARAM_IMAGE_URLS = "image_urls"
internal const val KEY_SELECTED_POSITION = "selected_position"

@HiltViewModel
internal class ImageViewerViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val title: String = savedStateHandle.get<String>(PARAM_TITLE)
        ?: stringProvider.getString(R.string.image_viewer_default_toolbar_title)

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
        return savedStateHandle.get(KEY_SELECTED_POSITION) ?: checkNotNull(savedStateHandle.get<Int>(PARAM_INITIAL_POSITION))
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
}
