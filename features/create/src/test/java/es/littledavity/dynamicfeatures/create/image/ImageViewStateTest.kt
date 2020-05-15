/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.image

import com.nhaarman.mockitokotlin2.any
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ImageViewStateTest {

    lateinit var state: ImageViewState

    @Test
    fun setStateAsOpenGalleryu_ShouldBeContinueFalse() {
        state = ImageViewState.OpenGallery

        assertFalse(state.isContinue())
    }

    @Test
    fun setStateAsContinue_ShouldBeSettled() {
        state = ImageViewState.Continue(any())

        assertTrue(state.isContinue())
    }
}
