/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.search

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.commons.ui.widgets.contacts.ContactsModelMapper
import es.littledavity.commons.ui.widgets.contacts.ContactsUiState
import es.littledavity.core.providers.StringProvider
import es.littledavity.domain.contacts.entities.Contact
import javax.inject.Inject

internal interface ContactsSearchUiStateFactory {
    fun createWithEmptyState(searchQuery: String): ContactsUiState
    fun createWithLoadingState(): ContactsUiState
    fun createWithResultState(contacts: List<Contact>): ContactsUiState
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class ContactsSearchUiStateFactoryImpl @Inject constructor(
    private val stringProvider: StringProvider,
    private val contactsModelMapper: ContactsModelMapper
) : ContactsSearchUiStateFactory {

    override fun createWithEmptyState(searchQuery: String): ContactsUiState {
        val title = if (searchQuery.isBlank()) {
            stringProvider.getString(R.string.contacts_search_fragment_info_title_default)
        } else {
            stringProvider.getString(
                R.string.contacts_search_fragment_info_title_empty,
                searchQuery
            )
        }

        return ContactsUiState.Empty(
            iconId = R.drawable.search,
            title = title
        )
    }

    override fun createWithLoadingState() = ContactsUiState.Loading

    override fun createWithResultState(contacts: List<Contact>) =
        ContactsUiState.Result(contacts.map(contactsModelMapper::mapToContactModel))
}
