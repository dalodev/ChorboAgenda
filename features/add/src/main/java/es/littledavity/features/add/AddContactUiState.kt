/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.add

import es.littledavity.commons.ui.widgets.contacts.ContactModel

sealed class AddContactUiState {
    object New : AddContactUiState()
    object Loading : AddContactUiState()
    object Error : AddContactUiState()
    data class ErrorPermission(val navigation: () -> Unit) : AddContactUiState()
    data class Result(val model: ContactModel) : AddContactUiState()
}
