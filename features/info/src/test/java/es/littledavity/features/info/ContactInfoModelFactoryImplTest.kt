/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info

import es.littledavity.features.info.mapping.ContactInfoHeaderModelFactory
import es.littledavity.features.info.mapping.ContactInfoModelFactoryImpl
import es.littledavity.features.info.widgets.model.ContactInfoHeaderModel
import es.littledavity.features.info.widgets.model.ContactInfoModel
import es.littledavity.testUtils.DOMAIN_CONTACT
import io.mockk.coEvery
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ContactInfoModelFactoryImplTest {

    private lateinit var factory: ContactInfoModelFactoryImpl
    private lateinit var headerModelFactory: ContactInfoHeaderModelFactory

    @Before
    fun setup() {
        headerModelFactory = mockk()
        factory = ContactInfoModelFactoryImpl(headerModelFactory)
    }

    @Test
    fun createInfoModel_shouldReturnInfoModel() {
        coEvery {
            headerModelFactory.createHeaderModel(
                any(),
                any()
            )
        } returns ContactInfoHeaderModel(
            emptyList(),
            "test",
            "test",
            "test",
            false, "test",
            "test",
            "test",
            "test",
            "test"
        )
        val result = factory.createInfoModel(DOMAIN_CONTACT, true)
        assertThat(result).isInstanceOf(ContactInfoModel::class.java)
    }
}
