/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import app.cash.turbine.test
import es.littledavity.core.providers.TimestampProvider
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.data.contacts.DataContact
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.DatabaseImage
import es.littledavity.database.chorbo.datastores.ContactMapper
import es.littledavity.database.chorbo.datastores.ContactsDatabaseDataStore
import es.littledavity.database.chorbo.datastores.SaveContactFactory
import es.littledavity.database.chorbo.datastores.mapToDatabaseContacts
import es.littledavity.database.chorbo.entities.Contact
import es.littledavity.database.chorbo.entities.CreationDate
import es.littledavity.database.chorbo.entities.CreationDateCategory
import es.littledavity.database.chorbo.entities.Image
import es.littledavity.database.chorbo.entities.Info
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
import kotlinx.coroutines.flow.flowOf
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
        val result = dataStore.searchContacts("", DATA_PAGINATION)
        assertThat(result).isEqualTo(DATA_CONTACTS_IMAGE_CREATED)
    }

    @Test
    fun getChorbosSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        val dbContacts = contactMapper.mapToDatabaseContacts(DATA_CONTACTS)
        coEvery { contactDao.getChorbos() } returns dbContacts
        val result = dataStore.getContacts()
        assertThat(result).isEqualTo(DATA_CONTACTS_IMAGE_CREATED)
    }

    @Test
    fun getChorboByIdSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        val dbContact = contactMapper.mapToDatabaseContact(DATA_CONTACT)
        coEvery { contactDao.getChorbo(any()) } returns dbContact
        val result = dataStore.getContact(DATA_CONTACT.id)
        assertThat(result).isEqualTo(DATA_CONTACT_IMAGE_CREATED)
    }

    @Test
    fun insertContactObserve() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { contactDao.insertChorbo(any()) } returns 1L
        dataStore.insertContact(DATA_CONTACT).test {
            assertThat(awaitItem()).isEqualTo(DATA_CONTACT)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun removeContactSuccesfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { contactDao.deleteChorboById(any()) } returns Unit
        val result = dataStore.removeContact(1)
        assertThat(result).isNotNull
    }

    @Test
    fun insertContactsSuccesfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { contactDao.insertChorbos(any()) } returns Unit
        val result = dataStore.insertContacts(DATA_CONTACTS)
        assertThat(result).isNotNull
    }

    @Test
    fun observeContactsSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { contactDao.observeContacts(any(), any()) } returns flowOf(databaseContacts)
        val dbContacts = databaseContacts.map(contactMapper::mapToDataContact)
        dataStore.observeContacts(DATA_PAGINATION).test {
            assertThat(awaitItem()).isEqualTo(dbContacts)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun getContactSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { contactDao.getChorbo(any()) } returns databaseContact
        val dataContact = contactMapper.mapToDataContact(databaseContact)
        val result = dataStore.getContact(databaseContact.id)
        assertThat(result).isEqualTo(dataContact)
    }

    private class FakeSaveContactFactory : SaveContactFactory {

        override fun createContact(contact: DataContact) = Contact(
            id = 1,
            name = "test",
            image = Image("123", 100, 100, false),
            phone = "test",
            createTimestamp = 500L,
            age = "1",
            country = "test",
            artworks = listOf(Image("123", 100, 100, false)),
            creationDate = CreationDate(1L, 2021, CreationDateCategory.YYYY_MMMM_DD),
            instagram = "test",
            rating = "test",
            screenshots = listOf(Image("123", 100, 100, false)),
            infoList = listOf(Info("test", "description"))
        )
    }

    private val databaseContacts = listOf(

        databaseContact.copy(
            id = 1,
            name = "Contact1",
        ),
        databaseContact.copy(
            id = 2,
            name = "Contact2",
        ),
        databaseContact.copy(
            id = 3,
            name = "Contact3",
        ),
        databaseContact.copy(
            id = 4,
            name = "Contact4",
        ),
        databaseContact.copy(
            id = 5,
            name = "Contact5",
        )
    )
}

private val databaseContact = DatabaseContact(
    id = 1,
    name = "Contact1",
    phone = "12345123",
    image = DatabaseImage("test", 1, 1, true),
    artworks = listOf(Image("123", 100, 100, false)),
    screenshots = listOf(Image("123", 100, 100, false)),
    creationDate = CreationDate(1L, 1, CreationDateCategory.YYYY_MMMM_DD),
    age = "18",
    country = "Espana",
    rating = "10/10",
    instagram = "@Littledavity",
    infoList = listOf(Info("test", "description")),
    createTimestamp = 1000L

)
