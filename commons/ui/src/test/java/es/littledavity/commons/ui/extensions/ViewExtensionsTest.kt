package es.littledavity.commons.ui.extensions

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import com.nhaarman.mockitokotlin2.mock
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
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

        assertEquals(expectedResponse, view.showTopSnackbar(resId))
    }
}