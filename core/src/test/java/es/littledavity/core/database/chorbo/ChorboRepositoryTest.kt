package es.littledavity.core.database.chorbo

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.junit.Assert.assertEquals

class ChorboRepositoryTest {
    @Mock
    lateinit var chorboDao: ChorboDao

    @InjectMocks
    lateinit var chorboRepository: ChorboRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getAllChorbosLiveData_ShouldInvokeCorrectDaoMethod() {
        chorboRepository.getAllChorboLiveData()

        verify(chorboDao).getAllChorbosLiveData()
    }

    @Test
    fun getAllChorbos_ShouldInvokeCorrectDaoMethod() {
        runBlocking {
            chorboRepository.getAllChorbos()

            verify(chorboDao).getAllChorbos()
        }
    }

    @Test
    fun getChorbo_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorboIdToFind = 1L
        val chorboIdCaptor = argumentCaptor<Long>()
        chorboRepository.getChorbo(chorboIdToFind)

        verify(chorboDao).getChorbo(chorboIdCaptor.capture())
        assertEquals(chorboIdToFind, chorboIdCaptor.lastValue)
    }

    @Test
    fun deleteAllChorbos_ShouldInvokeCorrectDaoMethod() = runBlocking {
        chorboRepository.deleteAllChorbos()

        verify(chorboDao).deleteAllChorbos()
    }

    @Test
    fun deleteChorboById_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorboIdToDelete = 1L
        val chorboIdCaptor = argumentCaptor<Long>()
        chorboRepository.deleteChorboById(chorboIdToDelete)

        verify(chorboDao).deleteChorboById(chorboIdCaptor.capture())
        assertEquals(chorboIdToDelete, chorboIdCaptor.lastValue)
    }

    @Test
    fun deleteChorbo_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorboToDelete = Chorbo(
            0,
            "Lorena",
            "test"
        )
        val chorboCaptor = argumentCaptor<Chorbo>()
        chorboRepository.deleteChorbo(chorboToDelete)

        verify(chorboDao).deleteChorbo(chorboCaptor.capture())
        assertEquals(chorboToDelete, chorboCaptor.lastValue)
    }

    @Test
    fun insertChorbos_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorbosToInsert = listOf(
            Chorbo(0, "Noemi", "test"),
            Chorbo(1, "Ximena", "test"),
            Chorbo(2, "Ángeles", "test")
        )
        val chorbosInsertedCaptor = argumentCaptor<List<Chorbo>>()
        chorboRepository.insertChorbos(chorbosToInsert)

        verify(chorboDao).insertChorbos(chorbosInsertedCaptor.capture())
        assertEquals(chorbosToInsert, chorbosInsertedCaptor.lastValue)
    }

    @Test
    fun insertChorbo_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorboToInsert = Chorbo(
            0,
            "Test",
            "test"
        )
        val chorboInsertedCaptor = argumentCaptor<Chorbo>()
        chorboRepository.insertChorbo(
            id = chorboToInsert.id,
            name = chorboToInsert.name,
            description = chorboToInsert.description
        )

        verify(chorboDao).insertChorbo(chorboInsertedCaptor.capture())
        assertEquals(chorboToInsert, chorboInsertedCaptor.lastValue)
    }
}