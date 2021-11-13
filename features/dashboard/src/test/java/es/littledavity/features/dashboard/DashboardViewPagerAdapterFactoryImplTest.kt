/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.dashboard

import es.littledavity.features.dashboard.fragment.adapter.DashboardViewPagerAdapter
import es.littledavity.features.dashboard.fragment.adapter.DashboardViewPagerAdapterFactoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class DashboardViewPagerAdapterFactoryImplTest {

    @MockK
    private lateinit var adapterFactory: DashboardViewPagerAdapterFactoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun createAdapter_shouldReturnAdapter() {
        coEvery { adapterFactory.createAdapter(any()) } returns mockk()
        val adapter = adapterFactory.createAdapter(mockk())
        assertThat(adapter).isInstanceOf(DashboardViewPagerAdapter::class.java)
    }
}
