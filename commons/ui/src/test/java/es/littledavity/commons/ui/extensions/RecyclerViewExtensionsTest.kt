/*
 * Copyright 2020 littledavity
 */
package es.littledavity.commons.ui.extensions

import androidx.recyclerview.widget.RecyclerView
import es.littledavity.testUtils.roboelectric.TestRobolectric
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RecyclerViewExtensionsTest : TestRobolectric() {

    @Mock
    lateinit var recyclerView: RecyclerView

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    /*@Test
    fun obtainRecyclerLayoutManagerAsGridType() {
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val gridLayoutManager = recyclerView.gridLayoutManager
        assertNotNull(gridLayoutManager)
        assertThat(gridLayoutManager, instanceOf(GridLayoutManager::class.java))
    }*/

    @Test
    fun obtainNullRecyclerGridLayoutManagerWithoutConfigured() {
        val gridLayoutManager = recyclerView.gridLayoutManager
        assertNull(gridLayoutManager)
    }

    /*@Test
    fun obtainRecyclerLayoutManagerAsLinearType() {
        recyclerView.layoutManager = LinearLayoutManager(context)

        val linearLayoutManager = recyclerView.linearLayoutManager
        assertNotNull(linearLayoutManager)
        assertThat(linearLayoutManager, instanceOf(LinearLayoutManager::class.java))
    }*/

    @Test
    fun obtainNullRecyclerLinearLayoutManagerWithoutConfigured() {
        val gridLayoutManager = recyclerView.linearLayoutManager
        assertNull(gridLayoutManager)
    }
}
