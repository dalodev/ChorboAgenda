package es.littledavity.dynamicfeatures.create.name

import org.junit.Assert
import org.junit.Test

class NameViewStateTest {

    lateinit var state: NameViewState

    @Test
    fun setStateAsEmptyName_ShouldBeSettled() {
        state = NameViewState.EmptyName

        Assert.assertTrue(state.isEmptyName())
        Assert.assertFalse(state.isContinue())
    }

    @Test
    fun setStateAsContinue_ShouldBeSettled() {
        state = NameViewState.Continue

        Assert.assertTrue(state.isContinue())
        Assert.assertFalse(state.isEmptyName())
    }
}