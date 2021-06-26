package es.littledavity.features.add

import es.littledavity.commons.ui.widgets.contacts.ContactModel

sealed class AddContactUiState {
    object New : AddContactUiState()
    object Loading : AddContactUiState()
    object Error : AddContactUiState()
    data class Result(val item: ContactModel) : AddContactUiState()
}