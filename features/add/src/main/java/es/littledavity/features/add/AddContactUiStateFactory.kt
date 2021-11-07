/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.add

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.commons.ui.widgets.contacts.ContactsModelMapper
import es.littledavity.domain.contacts.entities.Contact
import javax.inject.Inject

interface AddContactUiStateFactory {
    fun createWithNewState(): AddContactUiState
    fun createWithLoadingState(): AddContactUiState
    fun createWithErrorState(nameError: Boolean, phoneError: Boolean): AddContactUiState
    fun createWithPermissionError(navigation: () -> Unit): AddContactUiState
    fun createWithResultState(contact: Contact): AddContactUiState
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class AddContactUiStateFactoryImpl @Inject constructor(
    private val contactModelMapper: ContactsModelMapper
) : AddContactUiStateFactory {
    override fun createWithNewState() = AddContactUiState.New

    override fun createWithLoadingState() = AddContactUiState.Loading

    override fun createWithErrorState(nameError: Boolean, phoneError: Boolean) =
        AddContactUiState.Error(nameError, phoneError)

    override fun createWithPermissionError(navigation: () -> Unit) =
        AddContactUiState.ErrorPermission(navigation)

    override fun createWithResultState(contact: Contact) = AddContactUiState.Result(
        contactModelMapper.mapToContactModel(contact)
    )
}
