/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import es.littledavity.database.ChorboagendaDatabase
import es.littledavity.database.chorbo.entities.Contact
import es.littledavity.database.chorbo.entities.CreationDate
import es.littledavity.database.chorbo.entities.CreationDateCategory
import es.littledavity.database.chorbo.entities.Image
import es.littledavity.database.chorbo.tables.ContactDao
import es.littledavity.testUtils.livedata.getValue
import es.littledavity.testUtils.roboelectric.TestRobolectric
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@get:Rule
var instantTaskExecutorRule = InstantTaskExecutorRule()

private lateinit var database: ChorboagendaDatabase
private lateinit var contactDao: ContactDao
private val fakeChorbos = listOf(
    Contact(
        id = 1,
        name = "Noemi",
        image = Image("test", 1, 1),
        phone = "test",
        createTimestamp = 500L,
        age = "18",
        artworks = emptyList(),
        country = "test",
        creationDate = CreationDate(1L, 2021, CreationDateCategory.YYYY_MMMM_DD),
        instagram = "test",
        rating = "10",
        screenshots = emptyList()
    ),
    Contact(
        id = 1,
        name = "Noemi",
        image = Image("test", 1, 1),
        phone = "test",
        createTimestamp = 500L,
        age = "18",
        artworks = emptyList(),
        country = "test",
        creationDate = CreationDate(1L, 2021, CreationDateCategory.YYYY_MMMM_DD),
        instagram = "test",
        rating = "10",
        screenshots = emptyList()
    ),
    Contact(
        id = 1,
        name = "Noemi",
        image = Image("test", 1, 1),
        phone = "test",
        createTimestamp = 500L,
        age = "18",
        artworks = emptyList(),
        country = "test",
        creationDate = CreationDate(1L, 2021, CreationDateCategory.YYYY_MMMM_DD),
        instagram = "test",
        rating = "10",
        screenshots = emptyList()
    )
)

class ChorboDaoTest : TestRobolectric() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room
            .inMemoryDatabaseBuilder(context, ChorboagendaDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        contactDao = database.contactDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun obtainAllChorboLiveData_WithoutData_ShouldReturnNull() {
        val chorbos = contactDao.getAllChorbosLiveData()
        assertTrue(getValue(chorbos).isNullOrEmpty())
    }

    @Test
    fun obtainAllChorboLiveData_WithData_ShouldReturnSorted() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)
        val chorbos = contactDao.getAllChorbosLiveData()

        assertEquals(fakeChorbos, getValue(chorbos))
    }

    @Test
    fun obtainAllCChorbos_WithoutData_ShouldReturnEmpty() = runBlocking {
        assertTrue(contactDao.getChorbos().isNullOrEmpty())
    }

    @Test
    fun obtainAllChorbos_WithData_ShouldReturnSorted() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)

        assertEquals(fakeChorbos, contactDao.getChorbos())
    }

    @Test
    fun obtainCChorboById_WithoutData_ShouldNotFound() = runBlocking {
        val chorboToFind = fakeChorbos.first()

        assertNull(contactDao.getChorbo(chorboToFind.id))
    }

    @Test
    fun obtainChorboById_WithData_ShouldFound() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)

        val chorboToFind = fakeChorbos.first()
        assertEquals(chorboToFind, contactDao.getChorbo(chorboToFind.id))
    }

    @Test
    fun insertChorbo_ShouldAdd() = runBlocking {
        fakeChorbos.forEach {
            contactDao.insertChorbo(it)
        }

        assertEquals(fakeChorbos, contactDao.getChorbos())
    }

    @Test
    fun deleteAllChorbos_ShouldRemoveAll() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)
        contactDao.deleteAllChorbos()

        assertTrue(contactDao.getChorbos().isNullOrEmpty())
    }

    @Test
    fun deleteChorbo_Stored_ShouldRemoveIt() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)

        val chorboToRemove = fakeChorbos.first()
        contactDao.deleteChorbo(chorboToRemove)

        assertThat(contactDao.getChorbos(), not(hasItem(chorboToRemove)))
    }

    @Test
    fun deleteChorbo_NoStored_ShouldNotRemoveNothing() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)

        val chorboToRemove = fakeChorbos[0]
        contactDao.deleteChorbo(chorboToRemove)

        assertEquals(fakeChorbos, contactDao.getChorbos())
    }

    @Test
    fun deleteChorboById_Stored_ShouldRemoveIt() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)

        val chorboToRemove = fakeChorbos.first()
        contactDao.deleteChorboById(chorboToRemove.id)

        assertThat(contactDao.getChorbos(), not(hasItem(chorboToRemove)))
    }

    @Test
    fun deleteChorbosById_Stored_ShouldRemoveIt() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)

        val chorboToRemove = listOf(fakeChorbos.first().id, fakeChorbos[2].id)
        contactDao.deleteChorbosById(chorboToRemove)

        assertThat(contactDao.getChorbos(), not(hasItem(chorboToRemove)))
    }

    @Test
    fun deleteChorboById_NoStored_ShouldNotRemoveNothing() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)

        val chorboNoStoredId = 100
        contactDao.deleteChorboById(chorboNoStoredId)

        assertEquals(fakeChorbos, contactDao.getChorbos())
    }

    @Test
    fun deleteChorbosById_NoStored_ShouldNotRemoveNothing() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)

        val chorboNoStoredId = listOf(100, 200)
        contactDao.deleteChorbosById(chorboNoStoredId)

        assertEquals(fakeChorbos, contactDao.getChorbos())
    }

    @Test
    fun obtainAllChorbosPaging_WithData_ShouldReturnNotEmpty() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)
        val chorbos = contactDao.getChorbos(0, 10)
        assertTrue(chorbos.isNotEmpty())
    }

    @Test
    fun obtainAllChorbosPaging_WithoutData_ShouldReturnNull() = runBlocking {
        contactDao.insertChorbos(fakeChorbos)
        val chorbos = contactDao.getChorbos(3, 10)
        assertTrue(chorbos.isNullOrEmpty())
    }
}
