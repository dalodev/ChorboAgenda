package es.littledavity.core.di

import android.content.Context
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.littledavity.core.database.ChorboagendaDatabase
import es.littledavity.core.database.chorbo.ChorboDao
import es.littledavity.core.di.modules.DatabaseModule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class DatabaseModuleTest {

    private lateinit var databaseModule: DatabaseModule

    @Before
    fun setUp() {
        databaseModule = DatabaseModule()
    }

    @Test
    fun verifyProvidedChorboagendaDatabase() {
        val context: Context = mock()
        val chorboagendaDatabase = databaseModule.provideChorboagendaDatabase(context)

        assertNotNull(chorboagendaDatabase.chorboDao())
    }

    @Test
    fun verifyProvidedChorboDao() {
        val chorboagendaDatabase: ChorboagendaDatabase = mock()
        val chorboDao: ChorboDao = mock()

        doReturn(chorboDao).whenever(chorboagendaDatabase).chorboDao()

        assertEquals(
            chorboDao,
            databaseModule.provideChorboDao(chorboagendaDatabase)
        )
        verify(chorboagendaDatabase).chorboDao()
    }

    @Test
    fun verifyProvidedChorboRepository() {
        val chorboDao: ChorboDao = mock()
        val repository = databaseModule.provideChorboRepository(chorboDao)

        assertEquals(chorboDao, repository.chorboDao)
    }
}