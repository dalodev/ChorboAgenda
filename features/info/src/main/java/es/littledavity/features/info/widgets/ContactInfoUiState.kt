package es.littledavity.features.info.widgets

import es.littledavity.features.info.widgets.main.model.ContactInfoModel

internal sealed class ContactInfoUiState {
    object Empty : ContactInfoUiState()
    object Loading : ContactInfoUiState()
    data class Result(val model: ContactInfoModel) : ContactInfoUiState()
}