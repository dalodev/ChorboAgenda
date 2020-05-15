package es.littledavity.dynamicfeatures.create.location

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LocationViewStateTest {

    lateinit var state: LocationViewState

    @Test
    fun setStateAsEmptyName_ShouldBeSettled() {
        state = LocationViewState.EmptyName

        assertTrue(state.isEmptyName())
        assertFalse(state.isContinue())
    }

    @Test
    fun setStateAsContinue_ShouldBeSettled() {
        state = LocationViewState.Continue

        assertTrue(state.isContinue())
        assertFalse(state.isEmptyName())
    }
}