/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import app.cash.turbine.test
import es.littledavity.core.providers.TimestampProvider
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.DatabaseImage
import es.littledavity.database.chorbo.datastores.ContactMapper
import es.littledavity.database.chorbo.datastores.LikedContactFactory
import es.littledavity.database.chorbo.datastores.LikedContactsDatabaseDataStore
import es.littledavity.database.chorbo.datastores.mapToDataContact
import es.littledavity.database.chorbo.entities.CreationDate
import es.littledavity.database.chorbo.entities.CreationDateCategory
import es.littledavity.database.chorbo.entities.LikedContact
import es.littledavity.database.chorbo.tables.LikedContactDao
import es.littledavity.testUtils.DATA_CONTACT
import es.littledavity.testUtils.DATA_PAGINATION
import es.littledavity.testUtils.FakeDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class LikedContactDatabaseDataStoreTest {

    @MockK
    private lateinit var likedContactDao: LikedContactDao
    private lateinit var contactMapper: ContactMapper
    private lateinit var dataStore: LikedContactsDatabaseDataStore

    @MockK
    private lateinit var timestampProvider: TimestampProvider

    @MockK
    private lateinit var imageGalleryService: ImageGalleryService

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        contactMapper = ContactMapper(timestampProvider, imageGalleryService)

        dataStore = LikedContactsDatabaseDataStore(
            likedContactDao = likedContactDao,
            contactMapper = contactMapper,
            dispatcherProvider = FakeDispatcherProvider(),
            likedContactFactory = FakeLikedContactFactory()
        )
    }

    @Test
    fun likeContactSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { likedContactDao.saveLikeContact(any()) } returns Unit
        Assertions.assertThat(dataStore.likeContact(DATA_CONTACT.id)).isNotNull
    }

    @Test
    fun unlikeContactSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { likedContactDao.deleteLikedContact(any()) } returns Unit
        Assertions.assertThat(dataStore.unlikeContact(DATA_CONTACT.id)).isNotNull
    }

    @Test
    fun isContactLikedTrueSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { likedContactDao.isContactLiked(any()) } returns true
        Assertions.assertThat(dataStore.isContactLiked(DATA_CONTACT.id)).isTrue()
    }

    @Test
    fun isContactLikedFalseSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { likedContactDao.isContactLiked(any()) } returns false
        Assertions.assertThat(dataStore.isContactLiked(DATA_CONTACT.id)).isFalse()
    }

    @Test
    fun observeContactLikeStateTrueSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { likedContactDao.observeContactLikeState(any()) } returns flowOf(true)
        dataStore.observeContactLikeState(DATA_CONTACT.id).test {
            Assertions.assertThat(awaitItem()).isTrue
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun observeContactLikeStateFalseSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { likedContactDao.observeContactLikeState(any()) } returns flowOf(false)
        dataStore.observeContactLikeState(DATA_CONTACT.id).test {
            Assertions.assertThat(awaitItem()).isFalse
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun observeLikedContactsSuccessfully() = runBlockingTest {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        coEvery { likedContactDao.observeLikedContacts(any(), any()) } returns flowOf(databaseContacts)
        dataStore.observeLikedContacts(DATA_PAGINATION).test {
            val dataContact = contactMapper.mapToDataContact(databaseContacts)
            Assertions.assertThat(awaitItem()).isEqualTo(dataContact)
            cancelAndConsumeRemainingEvents()
        }
    }

    private class FakeLikedContactFactory : LikedContactFactory {

        override fun createLikeContact(contactId: Int) = LikedContact(
            id = 1,
            contactId = 1,
            likeTimestamp = 1
        )
    }

    private val databaseContacts = listOf(

        DatabaseContact(
            id = 1,
            name = "Contact1",
            phone = "12345123",
            image = DatabaseImage("test", 1, 1, true),
            artworks = mutableListOf(),
            screenshots = mutableListOf(),
            creationDate = CreationDate(1L, 1, CreationDateCategory.YYYY_MMMM_DD),
            age = "18",
            country = "Espana",
            rating = "10/10",
            instagram = "@Littledavity",
            infoList = emptyList(),
            createTimestamp = 1000L
        )
    )
}
