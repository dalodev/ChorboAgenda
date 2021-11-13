/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info

import es.littledavity.domain.contacts.entities.Info
import es.littledavity.features.info.widgets.ContactInfoAdapter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ContactInfoAdapterTest {

    @MockK
    private lateinit var adapter: ContactInfoAdapter

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getData_shouldReutrnInfoList() {
        val list = mutableListOf(Info("test", "test"))
        coEvery { adapter.getData() } returns list
        val result = adapter.getData()
        assertThat(result).isEqualTo(list)
    }

    @Test
    fun init_shouldHasStableIds() {
        coEvery { adapter.hasStableIds() } returns true
        assertThat(adapter.hasStableIds()).isEqualTo(true)
    }
}
