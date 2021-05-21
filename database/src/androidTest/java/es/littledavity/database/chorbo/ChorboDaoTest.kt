/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import es.littledavity.database.ChorboagendaDatabase
import es.littledavity.database.chorbo.entities.Chorbo
import es.littledavity.database.chorbo.tables.ChorboDao
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
private lateinit var chorboDao: ChorboDao
private val fakeChorbos = listOf(
    Chorbo(
        id = 1,
        name = "Noemi",
        image = "test",
        countryCode = "+34",
        countryName = "España",
        flag = "test",
        whatsapp = "test",
        instagram = "test"
    ),
    Chorbo(
        id = 2,
        name = "Ximena",
        image = "test",
        countryCode = "+34",
        countryName = "España",
        flag = "test",
        whatsapp = "test",
        instagram = "test"
    ),
    Chorbo(
        id = 3,
        name = "Ángeles",
        image = "test",
        countryCode = "+34",
        countryName = "España",
        flag = "test",
        whatsapp = "test",
        instagram = "test"
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
        chorboDao = database.chorboDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun obtainAllChorboLiveData_WithoutData_ShouldReturnNull() {
        val chorbos = chorboDao.getAllChorbosLiveData()
        assertTrue(getValue(chorbos).isNullOrEmpty())
    }

    @Test
    fun obtainAllChorboLiveData_WithData_ShouldReturnSorted() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)
        val chorbos = chorboDao.getAllChorbosLiveData()

        assertEquals(fakeChorbos, getValue(chorbos))
    }

    @Test
    fun obtainAllCChorbos_WithoutData_ShouldReturnEmpty() = runBlocking {
        assertTrue(chorboDao.getChorbos().isNullOrEmpty())
    }

    @Test
    fun obtainAllChorbos_WithData_ShouldReturnSorted() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        assertEquals(fakeChorbos, chorboDao.getChorbos())
    }

    @Test
    fun obtainCChorboById_WithoutData_ShouldNotFound() = runBlocking {
        val chorboToFind = fakeChorbos.first()

        assertNull(chorboDao.getChorbo(chorboToFind.id))
    }

    @Test
    fun obtainChorboById_WithData_ShouldFound() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        val chorboToFind = fakeChorbos.first()
        assertEquals(chorboToFind, chorboDao.getChorbo(chorboToFind.id))
    }

    @Test
    fun insertChorbo_ShouldAdd() = runBlocking {
        fakeChorbos.forEach {
            chorboDao.insertChorbo(it)
        }

        assertEquals(fakeChorbos, chorboDao.getChorbos())
    }

    @Test
    fun deleteAllChorbos_ShouldRemoveAll() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)
        chorboDao.deleteAllChorbos()

        assertTrue(chorboDao.getChorbos().isNullOrEmpty())
    }

    @Test
    fun deleteChorbo_Stored_ShouldRemoveIt() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        val chorboToRemove = fakeChorbos.first()
        chorboDao.deleteChorbo(chorboToRemove)

        assertThat(chorboDao.getChorbos(), not(hasItem(chorboToRemove)))
    }

    @Test
    fun deleteChorbo_NoStored_ShouldNotRemoveNothing() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        val chorboToRemove = Chorbo(5, "test", "test", "+34", "España", "test", "test", "test")
        chorboDao.deleteChorbo(chorboToRemove)

        assertEquals(fakeChorbos, chorboDao.getChorbos())
    }

    @Test
    fun deleteChorboById_Stored_ShouldRemoveIt() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        val chorboToRemove = fakeChorbos.first()
        chorboDao.deleteChorboById(chorboToRemove.id)

        assertThat(chorboDao.getChorbos(), not(hasItem(chorboToRemove)))
    }

    @Test
    fun deleteChorbosById_Stored_ShouldRemoveIt() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        val chorboToRemove = listOf(fakeChorbos.first().id, fakeChorbos[2].id)
        chorboDao.deleteChorbosById(chorboToRemove)

        assertThat(chorboDao.getChorbos(), not(hasItem(chorboToRemove)))
    }

    @Test
    fun deleteChorboById_NoStored_ShouldNotRemoveNothing() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        val chorboNoStoredId = 100L
        chorboDao.deleteChorboById(chorboNoStoredId)

        assertEquals(fakeChorbos, chorboDao.getChorbos())
    }

    @Test
    fun deleteChorbosById_NoStored_ShouldNotRemoveNothing() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        val chorboNoStoredId = listOf(100L, 200L)
        chorboDao.deleteChorbosById(chorboNoStoredId)

        assertEquals(fakeChorbos, chorboDao.getChorbos())
    }

    @Test
    fun obtainAllChorbosPaging_WithData_ShouldReturnNotEmpty() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)
        val chorbos = chorboDao.getChorbos(0, 10)
        assertTrue(chorbos.isNotEmpty())
    }

    @Test
    fun obtainAllChorbosPaging_WithoutData_ShouldReturnNull() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)
        val chorbos = chorboDao.getChorbos(3, 10)
        assertTrue(chorbos.isNullOrEmpty())
    }
}
