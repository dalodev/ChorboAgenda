/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.search

import es.littledavity.commons.ui.widgets.contacts.ContactModel
import es.littledavity.commons.ui.widgets.contacts.ContactsModelMapper
import es.littledavity.commons.ui.widgets.contacts.ContactsUiState
import es.littledavity.core.providers.StringProvider
import es.littledavity.testUtils.DOMAIN_CONTACTS
import es.littledavity.testUtils.FakeStringProvider
import io.mockk.coEvery
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ContactsSearchUiStateFactoryImplTest {

    private lateinit var factory: ContactsSearchUiStateFactoryImpl
    private lateinit var stringProvider: StringProvider
    private lateinit var contactsModelMapper: ContactsModelMapper

    @Before
    fun setup() {
        stringProvider = FakeStringProvider()
        contactsModelMapper = mockk()
        factory = ContactsSearchUiStateFactoryImpl(stringProvider, contactsModelMapper)
    }

    @Test
    fun createWithEmptyState_shouldReturnEmptyState() {
        val result = factory.createWithEmptyState("test")
        assertThat(result).isInstanceOf(ContactsUiState.Empty::class.java)
    }

    @Test
    fun createWithLoadingState_shouldReturnLoadingState() {
        val result = factory.createWithLoadingState()
        assertThat(result).isEqualTo(ContactsUiState.Loading)
    }

    @Test
    fun createWithResultState_shouldReturnResultState() {
        coEvery { contactsModelMapper.mapToContactModel(any()) } returns ContactModel(
            1,
            "test",
            "test",
            "test",
            "test"
        )
        val model = DOMAIN_CONTACTS.map(contactsModelMapper::mapToContactModel)
        val result = factory.createWithResultState(DOMAIN_CONTACTS)
        assertThat(result).isEqualTo(ContactsUiState.Result(model))
    }
}
