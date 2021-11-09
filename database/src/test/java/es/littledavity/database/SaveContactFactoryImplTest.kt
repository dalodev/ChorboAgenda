/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import es.littledavity.core.providers.TimestampProvider
import es.littledavity.core.service.ImageGalleryService
import es.littledavity.database.chorbo.datastores.ContactMapper
import es.littledavity.database.chorbo.datastores.SaveContactFactoryImpl
import es.littledavity.testUtils.DATA_CONTACT
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class SaveContactFactoryImplTest {

    private lateinit var contactMapper: ContactMapper
    private lateinit var factory: SaveContactFactoryImpl

    @MockK
    private lateinit var timestampProvider: TimestampProvider

    @MockK
    private lateinit var imageGalleryService: ImageGalleryService

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        contactMapper = ContactMapper(timestampProvider, imageGalleryService)
        factory = SaveContactFactoryImpl(contactMapper)
    }

    @Test
    fun createContactShouldReturnContact() {
        coEvery { imageGalleryService.createMediaFile(any(), any()) } returns "test"
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        val databaseContact = contactMapper.mapToDatabaseContact(DATA_CONTACT)
        val contact = factory.createContact(DATA_CONTACT)
        assertThat(contact).isEqualTo(databaseContact)
    }
}
