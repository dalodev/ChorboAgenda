package es.littledavity.database

import app.cash.turbine.test
import es.littledavity.database.chorbo.datastores.ContactMapper
import es.littledavity.database.chorbo.datastores.ContactsDatabaseDataStore
import es.littledavity.database.chorbo.datastores.mapToDatabaseContacts
import es.littledavity.database.chorbo.tables.ContactDao
import es.littledavity.testUtils.DATA_CONTACTS
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

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        contactMapper = ContactMapper()

        dataStore = ContactsDatabaseDataStore(
            contactDao = contactDao,
            contactMapper = contactMapper,
            dispatcherProvider = FakeDispatcherProvider()
        )
    }

    @Test
    fun searchesChorbosSuccessfully() = runBlockingTest {
        val dbContacts = contactMapper.mapToDatabaseContacts(DATA_CONTACTS)
        coEvery { contactDao.searchContacts(any(), any(), any()) } returns dbContacts
        assertThat(dataStore.searchGames("", DATA_PAGINATION)).isEqualTo(DATA_CONTACTS)
    }

    @Test
    fun observesContactsSuccessfully() = runBlockingTest {
        val dbContacts = contactMapper.mapToDatabaseContacts(DATA_CONTACTS)
        coEvery { contactDao.observeContacts(any(), any()) } returns flowOf(dbContacts)
        dataStore.observeContacts(DATA_PAGINATION).test {
            assertThat(expectItem()).isEqualTo(DATA_CONTACTS)
            expectComplete()
        }
    }
}
