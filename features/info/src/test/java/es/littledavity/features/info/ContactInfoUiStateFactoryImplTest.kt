/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info

import es.littledavity.features.info.mapping.ContactInfoModelFactory
import es.littledavity.features.info.mapping.ContactInfoUiStateFactoryImpl
import es.littledavity.features.info.widgets.ContactInfoUiState
import es.littledavity.features.info.widgets.model.ContactInfoModel
import io.mockk.coEvery
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ContactInfoUiStateFactoryImplTest {

    private lateinit var factory: ContactInfoUiStateFactoryImpl
    private lateinit var modelFactory: ContactInfoModelFactory

    @Before
    fun setup() {
        modelFactory = mockk()
        factory = ContactInfoUiStateFactoryImpl(modelFactory)
    }

    @Test
    fun createWithEmptyState_shouldReturnEmptyState() {
        val result = factory.createWithEmptyState()
        assertThat(result).isEqualTo(ContactInfoUiState.Empty)
    }

    @Test
    fun createWithLoadingState_shouldReturnLoadingState() {
        val result = factory.createWithLoadingState()
        assertThat(result).isEqualTo(ContactInfoUiState.Loading)
    }

    @Test
    fun createWithPermissionError_shouldReturnPermissionErrorState() {
        val unit = {}
        val result = factory.createWithPermissionError(unit)
        assertThat(result).isEqualTo(ContactInfoUiState.ErrorPermission(unit))
    }

    @Test
    fun createWithResultState_shouldReturnResultState() {
        val model = ContactInfoModel(1, mockk(), emptyList())
        coEvery { modelFactory.createInfoModel(any(), any()) } returns model
        val result = factory.createWithResultState(mockk(), true)
        assertThat(result).isEqualTo(ContactInfoUiState.Result(model))
    }
}
