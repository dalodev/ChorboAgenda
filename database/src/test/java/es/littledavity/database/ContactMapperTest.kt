/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import es.littledavity.core.providers.TimestampProvider
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.DatabaseImage
import es.littledavity.database.chorbo.datastores.ContactMapper
import es.littledavity.database.chorbo.entities.CreationDate
import es.littledavity.database.chorbo.entities.CreationDateCategory
import es.littledavity.testUtils.DATA_CONTACT
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ContactMapperTest {

    @MockK
    private lateinit var timestampProvider: TimestampProvider

    @MockK
    private lateinit var imageGalleryService: ImageGalleryService

    private lateinit var mapper: ContactMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mapper = ContactMapper(timestampProvider, imageGalleryService)
    }

    @Test
    fun mapToDatabaseContact_shouldReturnDatabaseModel() {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp() } returns 1L
        val contact = mapper.mapToDatabaseContact(DATA_CONTACT)
        assertThat(contact.id).isEqualTo(1)
    }

    @Test
    fun mapToDataContact_shouldReturnDatabaseModel() {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp() } returns 1L
        val contact = mapper.mapToDataContact(databaseContact)
        assertThat(contact.id).isEqualTo(1)
    }

    private val databaseContact = DatabaseContact(
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
}
