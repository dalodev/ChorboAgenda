/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.dashboard

import androidx.fragment.app.Fragment
import es.littledavity.features.dashboard.fragment.DashboardPage
import es.littledavity.features.dashboard.fragment.adapter.DashboardViewPagerAdapter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class DashboardViewPagerAdapterTest {

    @MockK
    private lateinit var adapter: DashboardViewPagerAdapter

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun createFragment_whenContacts_shouldReturnCorrectFragment() {
        coEvery { adapter.createFragment(any()) } returns Fragment()
        val fragment = adapter.createFragment(DashboardPage.CONTACTS.position)
        assertThat(fragment).isInstanceOf(Fragment::class.java)
    }

    @Test
    fun createFragment_whenAddContact_shouldReturnCorrectFragment() {
        coEvery { adapter.createFragment(any()) } returns Fragment()
        val fragment = adapter.createFragment(DashboardPage.ADD_CONTACT.position)
        assertThat(fragment).isInstanceOf(Fragment::class.java)
    }

    @Test
    fun createFragment_whenLikes_shouldReturnCorrectFragment() {
        coEvery { adapter.createFragment(any()) } returns Fragment()
        val fragment = adapter.createFragment(DashboardPage.LIKES.position)
        assertThat(fragment).isInstanceOf(Fragment::class.java)
    }

    @Test
    fun getItemCount() {
        coEvery { adapter.itemCount } returns DashboardPage.values().size
        val result = adapter.itemCount
        assertThat(result).isEqualTo(DashboardPage.values().size)
    }
}
