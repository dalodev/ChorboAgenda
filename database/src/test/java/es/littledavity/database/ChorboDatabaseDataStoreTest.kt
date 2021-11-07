/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import es.littledavity.core.providers.TimestampProvider
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.data.contacts.DataContact
import es.littledavity.database.chorbo.datastores.ContactMapper
import es.littledavity.database.chorbo.datastores.ContactsDatabaseDataStore
import es.littledavity.database.chorbo.datastores.SaveContactFactory
import es.littledavity.database.chorbo.datastores.mapToDatabaseContacts
import es.littledavity.database.chorbo.entities.Contact
import es.littledavity.database.chorbo.entities.CreationDate
import es.littledavity.database.chorbo.entities.CreationDateCategory
import es.littledavity.database.chorbo.tables.ContactDao
import es.littledavity.testUtils.DATA_CONTACT
import es.littledavity.testUtils.DATA_CONTACTS
import es.littledavity.testUtils.DATA_CONTACTS_IMAGE_CREATED
import es.littledavity.testUtils.DATA_CONTACT_IMAGE_CREATED
import es.littledavity.testUtils.DATA_PAGINATION
import es.littledavity.testUtils.FakeDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ChorboDatabaseDataStoreTest {

    @MockK
    private lateinit var contactDao: ContactDao

    private lateinit var contactMapper: ContactMapper
    private lateinit var dataStore: ContactsDatabaseDataStore

    @MockK
    private lateinit var timestampProvider: TimestampProvider

    @MockK
    private lateinit var imageGalleryService: ImageGalleryService

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        contactMapper = ContactMapper(timestampProvider, imageGalleryService)

        dataStore = ContactsDatabaseDataStore(
            contactDao = contactDao,
            contactMapper = contactMapper,
            dispatcherProvider = FakeDispatcherProvider(),
            saveContactFactory = FakeSaveContactFactory()
        )
    }

    @Test
    fun searchesChorbosSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        val dbContacts = contactMapper.mapToDatabaseContacts(DATA_CONTACTS)
        coEvery { contactDao.searchContacts(any(), any(), any()) } returns dbContacts
        assertThat(dataStore.searchContacts("", DATA_PAGINATION)).isEqualTo(
            DATA_CONTACTS_IMAGE_CREATED
        )
    }

    @Test
    fun getChorbosSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        val dbContacts = contactMapper.mapToDatabaseContacts(DATA_CONTACTS)
        coEvery { contactDao.getChorbos() } returns dbContacts
        assertThat(dataStore.getContacts()).isEqualTo(DATA_CONTACTS_IMAGE_CREATED)
    }

    @Test
    fun getChorboByIdSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        val dbContact = contactMapper.mapToDatabaseContact(DATA_CONTACT)
        coEvery { contactDao.getChorbo(any()) } returns dbContact
        assertThat(dataStore.getContact(DATA_CONTACT.id)).isEqualTo(DATA_CONTACT_IMAGE_CREATED)
    }

    private class FakeSaveContactFactory : SaveContactFactory {

        override fun createContact(contact: DataContact) = Contact(
            id = 1,
            name = "test",
            image = null,
            phone = "test",
            createTimestamp = 500L,
            age = "1",
            country = "test",
            artworks = emptyList(),
            creationDate = CreationDate(1L, 2021, CreationDateCategory.YYYY_MMMM_DD),
            instagram = "test",
            rating = "test",
            screenshots = emptyList(),
            infoList = emptyList()
        )
    }
}
