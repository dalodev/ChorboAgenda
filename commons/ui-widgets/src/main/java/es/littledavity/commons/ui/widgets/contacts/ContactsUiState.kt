package es.littledavity.commons.ui.widgets.contacts

sealed class ContactsUiState {
    data class Empty(val iconId: Int, val title: String) : ContactsUiState()
    object Loading : ContactsUiState()
    data class Result(val items: List<ContactModel>) : ContactsUiState()
}