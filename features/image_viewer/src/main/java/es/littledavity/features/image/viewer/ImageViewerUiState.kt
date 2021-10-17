/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.image.viewer

sealed class ImageViewerUiState {
    object Loading : ImageViewerUiState()
    data class Error(val error: Throwable) : ImageViewerUiState()
    data class Result(val urls: List<String>, val selectedPosition: Int) : ImageViewerUiState()
}
