/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.image.viewer

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class ImageViewerUiStateFactoryImplTest {

    private lateinit var factory: ImageViewerUiStateFactoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        factory = ImageViewerUiStateFactoryImpl()
    }

    @Test
    fun createWithLoadingState_shouldReturnLoadingState() {
        val result = factory.createWithLoadingState()
        assertThat(result).isEqualTo(ImageViewerUiState.Loading)
    }

    @Test
    fun createWithErrorState_shouldReturnErrorState() {
        val expected = Exception()
        val result = factory.createWithErrorState(expected)
        assertThat(result).isEqualTo(ImageViewerUiState.Error(expected))
    }

    @Test
    fun createWithResultState_shouldReturnResultState() {
        val result = factory.createWithResultState(listOf(), 0)
        assertThat(result).isEqualTo(
            ImageViewerUiState.Result(
                listOf(),
                0
            )
        )
    }
}
