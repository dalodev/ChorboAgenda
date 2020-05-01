package es.littledavity.core.database.chorbo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import es.littledavity.core.database.ChorboagendaDatabase
import es.littledavity.test_utils.roboelectric.TestRobolectric
import org.junit.Rule
import androidx.test.platform.app.InstrumentationRegistry
import es.littledavity.test_utils.livedata.getValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.hasItem

@get:Rule
var instantTaskExecutorRule = InstantTaskExecutorRule()

private lateinit var database: ChorboagendaDatabase
private lateinit var chorboDao: ChorboDao
private val fakeChorbos = listOf(
    Chorbo(0, "Noemi"),
    Chorbo(1, "Ximena"),
    Chorbo(2, "Ángeles")
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
        assertTrue(chorboDao.getAllChorbos().isNullOrEmpty())
    }

    @Test
    fun obtainAllChorbos_WithData_ShouldReturnSorted() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        assertEquals(fakeChorbos, chorboDao.getAllChorbos())
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

        assertEquals(fakeChorbos, chorboDao.getAllChorbos())
    }

    @Test
    fun deleteAllChorbos_ShouldRemoveAll() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)
        chorboDao.deleteAllChorbos()

        assertTrue(chorboDao.getAllChorbos().isNullOrEmpty())
    }

    @Test
    fun deleteChorbo_Stored_ShouldRemoveIt() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        val chorboToRemove = fakeChorbos.first()
        chorboDao.deleteChorbo(chorboToRemove)

        assertThat(chorboDao.getAllChorbos(), not(hasItem(chorboToRemove)))
    }

    @Test
    fun deleteChorbo_NoStored_ShouldNotRemoveNothing() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        val chorboToRemove = Chorbo(5, "test")
        chorboDao.deleteChorbo(chorboToRemove)

        assertEquals(fakeChorbos, chorboDao.getAllChorbos())
    }

    @Test
    fun deleteChorboById_Stored_ShouldRemoveIt() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        val chorboToRemove = fakeChorbos.first()
        chorboDao.deleteChorboById(chorboToRemove.id)

        assertThat(chorboDao.getAllChorbos(), not(hasItem(chorboToRemove)))
    }

    @Test
    fun deleteChorboById_NoStored_ShouldNotRemoveNothing() = runBlocking {
        chorboDao.insertChorbos(fakeChorbos)

        val chorboNoStoredId = 100L
        chorboDao.deleteChorboById(chorboNoStoredId)

        assertEquals(fakeChorbos, chorboDao.getAllChorbos())
    }
}