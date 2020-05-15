/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.database.chorbo

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ChorboRepositoryTest {
    @Mock
    lateinit var chorboDao: ChorboDao

    @InjectMocks
    lateinit var chorboRepository: ChorboRepository

    private val chorbo = Chorbo(2, "Ángeles", "test", "+34", "España", "test", "test", "test")

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
            chorboRepository.getChorbos()

            verify(chorboDao).getChorbos()
        }
    }

    @Test
    fun getAllChorbosOffset_ShouldInvokeCorrectDaoMethod() {
        runBlocking {
            chorboRepository.getChorbos(0, 10)

            verify(chorboDao).getChorbos(any(), any())
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
        val chorboToDelete = chorbo
        val chorboCaptor = argumentCaptor<Chorbo>()
        chorboRepository.deleteChorbo(chorboToDelete)

        verify(chorboDao).deleteChorbo(chorboCaptor.capture())
        assertEquals(chorboToDelete, chorboCaptor.lastValue)
    }

    @Test
    fun insertChorbos_ShouldInvokeCorrectDaoMethod() = runBlocking {
        val chorbosToInsert = listOf(
            chorbo,
            chorbo,
            chorbo
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
    }
}
