/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.extensions

import android.view.View
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ViewExtensionsTest {

    @MockK(relaxed = true)
    lateinit var view: View

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getString_WhenIdIsNull_ReturnNull() {
        val resId = null
        val expectedResponse = null

        assertEquals(expectedResponse, null)
    }
}
