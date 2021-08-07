/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import es.littledavity.data.contacts.DataContact
import es.littledavity.data.contacts.entities.CreationDate
import es.littledavity.data.contacts.entities.CreationDateCategory
import es.littledavity.data.contacts.entities.Image
import es.littledavity.database.chorbo.datastores.ContactsDatabaseDataStore
import es.littledavity.database.chorbo.entities.Contact
import es.littledavity.database.chorbo.tables.ContactDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ContactsDatabaseDataStoreTest {

    @Mock
    internal lateinit var contactDao: ContactDao

    @InjectMocks
    internal lateinit var chorboRepository: ContactsDatabaseDataStore

    private val chorbo = DataContact(
        2,
        Image("test", 1, 1),
        "test",
        "+34",
        age = "18",
        gallery = mutableListOf(),
        country = "test",
        creationDate = CreationDate(1L, 2021, CreationDateCategory.YYYY_MMMM_DD),
        instagram = "test",
        rating = "10",
        screenshots = mutableListOf(),
        info = emptyList()
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getAllChorbosLiveData_ShouldInvokeCorrectDaoMethod() {
        chorboRepository.getAllChorboLiveData()

        verify(contactDao).getAllChorbosLiveData()
    }

    @Test
    fun getAllChorbos_ShouldInvokeCorrectDaoMethod() {
        runBlocking {
            chorboRepository.getChorbos()

            verify(contactDao).getChorbos()
        }
    }

    @Test
    fun getAllChorbosOffset_ShouldInvokeCorrectDaoMethod() {
        runBlocking {
            chorboRepository.getChorbos(0, 10)

            verify(contactDao).getChorbos(any(), any())
        }
    }

    @Test
    fun getChorbo_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorboIdToFind = 1
        val chorboIdCaptor = argumentCaptor<Int>()
        chorboRepository.getChorbo(chorboIdToFind)

        verify(contactDao).getChorbo(chorboIdCaptor.capture())
        assertEquals(chorboIdToFind, chorboIdCaptor.lastValue)
    }

    @Test
    fun deleteAllChorbos_ShouldInvokeCorrectDaoMethod() = runBlocking {
        chorboRepository.deleteAllChorbos()

        verify(contactDao).deleteAllChorbos()
    }

    @Test
    fun deleteChorboById_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorboIdToDelete = 1
        val chorboIdCaptor = argumentCaptor<Int>()
        chorboRepository.deleteChorboById(chorboIdToDelete)

        verify(contactDao).deleteChorboById(chorboIdCaptor.capture())
        assertEquals(chorboIdToDelete, chorboIdCaptor.lastValue)
    }

    @Test
    fun deleteChorbosById_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorboIdToDelete = listOf(1, 2)
        val chorboIdCaptor = argumentCaptor<List<Int>>()
        chorboRepository.deleteChorbosById(chorboIdToDelete)

        verify(contactDao).deleteChorbosById(chorboIdCaptor.capture())
        assertEquals(chorboIdToDelete, chorboIdCaptor.lastValue)
    }

    @Test
    fun deleteChorbo_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorboToDelete = Contact(
            chorbo.id,
            chorbo.name,
            es.littledavity.database.chorbo.entities.Image(
                chorbo.image?.id,
                chorbo.image?.width,
                chorbo.image?.height
            ),
            chorbo.phone,
            age = "18",
            artworks = emptyList(),
            country = "test",
            creationDate = es.littledavity.database.chorbo.entities.CreationDate(
                1L,
                2021,
                es.littledavity.database.chorbo.entities.CreationDateCategory.YYYY_MMMM_DD
            ),
            instagram = "test",
            rating = "10",
            screenshots = emptyList(),
            createTimestamp = 1L,
            infoList = emptyList()
        )
        val chorboCaptor = argumentCaptor<Contact>()
        chorboRepository.deleteChorbo(chorboToDelete)

        verify(contactDao).deleteChorbo(chorboCaptor.capture())
        assertEquals(chorboToDelete, chorboCaptor.lastValue)
    }

    @Test
    fun insertChorbos_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorbosToInsert = listOf(
            chorbo,
            chorbo,
            chorbo
        )
        val chorbosInsertedCaptor = argumentCaptor<List<Contact>>()
        chorboRepository.insertContacts(chorbosToInsert)

        verify(contactDao).insertChorbos(chorbosInsertedCaptor.capture())
        assertEquals(chorbosToInsert, chorbosInsertedCaptor.lastValue)
    }

    /*@Test
    fun insertChorbo_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorboToInsert = Chorbo(
            0,
            "Test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test"
        )
        val chorboInsertedCaptor = argumentCaptor<Chorbo>()
        chorboRepository.insertChorbo(
            id = chorboToInsert.id,
            name = chorboToInsert.name,
            image = chorboToInsert.image,
            countryCode = chorboToInsert.countryCode,
            countryName = chorboToInsert.countryName,
            flag = chorboToInsert.flag,
            whatsapp = chorboToInsert.whatsapp,
            instagram = chorboToInsert.instagram
        )

        verify(chorboDao).insertChorbo(chorboInsertedCaptor.capture())
        assertEquals(chorboToInsert, chorboInsertedCaptor.lastValue)
    }*/
}
