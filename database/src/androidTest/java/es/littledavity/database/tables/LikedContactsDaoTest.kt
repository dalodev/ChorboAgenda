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
import es.littledavity.database.LIKED_CONTACT
import es.littledavity.database.LIKED_CONTACTS
import es.littledavity.database.chorbo.tables.ContactDao
import es.littledavity.database.chorbo.tables.LikedContactDao
import es.littledavity.database.di.DatabaseModule
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
internal class LikedContactsDaoTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var instantExcutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var contactsDao: ContactDao

    @Inject
    lateinit var dao: LikedContactDao

    @Module(includes = [TestDatabaseModule::class])
    @InstallIn(SingletonComponent::class)
    class TestModule

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun likesContactsAndVerifiesThatItIsLiked() = runBlockingTest {
        dao.saveLikeContact(LIKED_CONTACT)
        assertThat(dao.isContactLiked(LIKED_CONTACT.contactId)).isTrue
    }

    @Test
    fun likesContactUnlikesItAndVerifiesThatItIsUnlikedByChecking() = runBlockingTest {
        dao.saveLikeContact(LIKED_CONTACT)
        dao.deleteLikedContact(LIKED_CONTACT.contactId)
        assertThat(dao.isContactLiked(LIKED_CONTACT.contactId)).isFalse
    }

    @Test
    fun verifiesThatUnlikedContactIsUnliked() = runBlockingTest {
        assertThat(dao.isContactLiked(100)).isFalse()
    }

    @Test
    fun likesContactAndObservesThatItIsLiked() = runBlockingTest {
        dao.saveLikeContact(LIKED_CONTACT)

        dao.observeContactLikeState(LIKED_CONTACT.contactId).test {
            assertThat(awaitItem()).isTrue
        }
    }

    @Test
    fun likesContactUnlikesItAndVerifiesThatItIsUnlikedByObserving() = runBlockingTest {
        dao.saveLikeContact(LIKED_CONTACT)
        dao.deleteLikedContact(LIKED_CONTACT.contactId)

        dao.observeContactLikeState(LIKED_CONTACT.contactId).test {
            assertThat(awaitItem()).isFalse
        }
    }

    @Test
    fun likesContactsAndObservesLikedContacts() {
        runBlockingTest {
            LIKED_CONTACTS.forEach { dao.saveLikeContact(it) }

            contactsDao.insertChorbos(DATABASE_CONTACTS)

            val expectedContacts = DATABASE_CONTACTS
                .sortedByDescending { contact ->
                    LIKED_CONTACTS
                        .first { likedContact -> likedContact.contactId == contact.id }
                        .likeTimestamp
                }

            dao.observeLikedContacts(offset = 0, limit = LIKED_CONTACTS.size).test {
                assertThat(awaitItem()).isEqualTo(expectedContacts)
            }
        }
    }
}
