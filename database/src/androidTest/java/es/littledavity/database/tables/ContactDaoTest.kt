/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database.tables

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import es.littledavity.database.DATABASE_CONTACTS
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.tables.ContactDao
import es.littledavity.database.di.DatabaseModule
import es.littledavity.testUtils.MainCoroutineRule
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
internal class ContactDaoTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var dao: ContactDao

    @Module(includes = [TestDatabaseModule::class])
    @InstallIn(SingletonComponent::class)
    class TestModule

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun insertContactAndGetsContactById() = runBlockingTest {
        dao.insertChorbos(DATABASE_CONTACTS)

        val expectContact = DATABASE_CONTACTS.first()

        assertThat(dao.getChorbo(expectContact.id)).isEqualTo(expectContact)
    }

    @Test
    fun insertContactsAndGetsNullForNonExistentContactID() = runBlockingTest {
        dao.insertChorbos(DATABASE_CONTACTS)
        assertThat(dao.getChorbo(chorboId = 5000)).isNull()
    }

    @Test
    fun insertContactsAndGetContacts() = runBlockingTest {
        dao.insertChorbos(DATABASE_CONTACTS)

        val expectedContacts = DATABASE_CONTACTS

        assertThat(dao.getChorbos()).isEqualTo(expectedContacts)
    }

    @Test
    fun insertContactsAndGetContactsBySearchingWithContactNameUpperCase() {
        runBlockingTest {
            dao.insertChorbos(DATABASE_CONTACTS)

            val expectedContacts = dao.searchContacts(
                searchQuery = "Contact",
                offset = 0,
                limit = DATABASE_CONTACTS.size
            )

            assertThat(
                dao.searchContacts(
                    searchQuery = "Contact",
                    offset = 0,
                    limit = DATABASE_CONTACTS.size
                )
            ).isEqualTo(expectedContacts)
        }
    }

    @Test
    fun insertContactsAndGetContactsBySearchingWithContactNameLowerCase() {
        runBlockingTest {
            dao.insertChorbos(DATABASE_CONTACTS)

            val expectedContacts = dao.searchContacts(
                searchQuery = "contact1",
                offset = 0,
                limit = DATABASE_CONTACTS.size
            )
            assertThat(
                dao.searchContacts(
                    searchQuery = "contact1",
                    offset = 0,
                    limit = DATABASE_CONTACTS.size
                )
            ).isEqualTo(expectedContacts)
        }
    }

    @Test
    fun insertContactsAndGetsEmptyContactsListBySearchingNotAvailableContactName() =
        runBlockingTest {
            dao.insertChorbos(DATABASE_CONTACTS)

            assertThat(
                dao.searchContacts(
                    searchQuery = "Pablo Martín",
                    offset = 0,
                    limit = DATABASE_CONTACTS.size
                )
            ).isEqualTo(emptyList<DatabaseContact>())
        }

    @Test
    fun insertContactsAndObservesContacts() = runBlockingTest {

        dao.insertChorbos(DATABASE_CONTACTS)

        dao.observeContacts(offset = 0, limit = DATABASE_CONTACTS.size).test {
            assertThat(awaitItem()).isEqualTo(DATABASE_CONTACTS)
        }
    }
}
