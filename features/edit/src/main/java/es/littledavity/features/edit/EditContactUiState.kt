package es.littledavity.features.edit

import es.littledavity.features.edit.widgets.model.ContactInfoModel

internal sealed class EditContactUiState {
    object Loading : EditContactUiState()
    object Error : EditContactUiState()
    data class Result(val model: ContactInfoModel) : EditContactUiState()
}