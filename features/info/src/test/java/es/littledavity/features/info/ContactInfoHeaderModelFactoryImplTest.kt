/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info

import es.littledavity.core.factories.IgdbImageUrlFactory
import es.littledavity.core.formatters.ContactCreationDateFormatter
import es.littledavity.features.info.mapping.ContactInfoHeaderModelFactoryImpl
import es.littledavity.features.info.widgets.model.ContactInfoHeaderModel
import es.littledavity.testUtils.DOMAIN_CONTACT
import io.mockk.coEvery
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class ContactInfoHeaderModelFactoryImplTest {

    private lateinit var factory: ContactInfoHeaderModelFactoryImpl

    private lateinit var igdbImageUrlFactory: IgdbImageUrlFactory

    private lateinit var creationDateFormatter: ContactCreationDateFormatter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        igdbImageUrlFactory = mockk()
        creationDateFormatter = mockk()
        factory = ContactInfoHeaderModelFactoryImpl(igdbImageUrlFactory, creationDateFormatter)
    }

    @Test
    fun createHeaderModel_shouldReturnHeaderModel() {
        coEvery { igdbImageUrlFactory.createUrl(any(), any()) } returns "test"
        coEvery { creationDateFormatter.formatReleaseDate(any()) } returns "test"
        val result = factory.createHeaderModel(DOMAIN_CONTACT, true)
        assertThat(result).isInstanceOf(ContactInfoHeaderModel::class.java)
    }
}
