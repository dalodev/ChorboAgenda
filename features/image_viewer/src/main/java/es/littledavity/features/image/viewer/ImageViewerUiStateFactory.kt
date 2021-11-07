/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.image.viewer

import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

interface ImageViewerUiStateFactory {
    fun createWithLoadingState(): ImageViewerUiState
    fun createWithErrorState(throwable: Throwable): ImageViewerUiState
    fun createWithResultState(urls: List<String>, position: Int): ImageViewerUiState
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class ImageViewerUiStateFactoryImpl @Inject constructor() : ImageViewerUiStateFactory {

    override fun createWithLoadingState() = ImageViewerUiState.Loading

    override fun createWithErrorState(throwable: Throwable) =
        ImageViewerUiState.Error(throwable)

    override fun createWithResultState(
        urls: List<String>,
        position: Int
    ): ImageViewerUiState.Result {
        return ImageViewerUiState.Result(
            urls = urls,
            selectedPosition = position
        )
    }
}
