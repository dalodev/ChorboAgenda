/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info.widgets

import es.littledavity.features.info.widgets.model.ContactInfoModel

internal sealed class ContactInfoUiState {
    object Empty : ContactInfoUiState()
    object Loading : ContactInfoUiState()
    data class ErrorPermission(val navigation: () -> Unit) : ContactInfoUiState()
    data class Result(val model: ContactInfoModel) : ContactInfoUiState()
}
