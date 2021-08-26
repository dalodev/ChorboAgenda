/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.contacts

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.commons.ui.widgets.contacts.ContactsModelMapper
import es.littledavity.commons.ui.widgets.contacts.ContactsUiState
import es.littledavity.core.providers.StringProvider
import es.littledavity.domain.contacts.entities.Contact
import javax.inject.Inject

interface ContactsUiStateFactory {
    fun createWithEmptyState(): ContactsUiState
    fun createWithLoadingState(): ContactsUiState
    fun createWithResultState(contacts: List<Contact>): ContactsUiState
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class ContactsUiStateFactoryImpl @Inject constructor(
    private val stringProvider: StringProvider,
    private val contactsModelMapper: ContactsModelMapper
) : ContactsUiStateFactory {

    override fun createWithEmptyState() = ContactsUiState.Empty(
        iconId = R.drawable.contact_outline,
        title = stringProvider.getString(R.string.contacts_fragment_info_title)
    )

    override fun createWithLoadingState() = ContactsUiState.Loading

    override fun createWithResultState(contacts: List<Contact>): ContactsUiState {
        if (contacts.isEmpty()) return createWithEmptyState()

        return ContactsUiState.Result(
            contacts.map(contactsModelMapper::mapToContactModel).sortedBy { it.name }
        )
    }
}
