/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import es.littledavity.core.providers.TimestampProvider
import es.littledavity.database.chorbo.datastores.LikedContactFactoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class LikedContactFactoryImplTest {

    @InjectMockKs
    private lateinit var factory: LikedContactFactoryImpl

    @MockK
    private lateinit var timestampProvider: TimestampProvider

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun createLikeContact_shouldReturnLikedContact() {
        coEvery { timestampProvider.getUnixTimestamp(any()) } returns 1L
        val likedContact = factory.createLikeContact(1)
        assertThat(likedContact.contactId).isEqualTo(1)
    }
}
