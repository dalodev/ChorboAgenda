/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.add

import es.littledavity.commons.ui.widgets.contacts.ContactModel

sealed class AddContactUiState {
    object New : AddContactUiState()
    object Loading : AddContactUiState()
    data class Error(val nameError: Boolean, val phoneError: Boolean) : AddContactUiState()
    data class ErrorPermission(val navigation: () -> Unit) : AddContactUiState()
    data class Result(val model: ContactModel) : AddContactUiState()
}
