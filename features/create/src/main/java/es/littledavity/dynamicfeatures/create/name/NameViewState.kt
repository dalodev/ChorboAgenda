/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.name

import es.littledavity.commons.ui.base.BaseViewState

/**
 * Different states for [NameFragment].
 *
 * @see BaseViewState
 */
sealed class NameViewState : BaseViewState {

    /**
     * Empty name.
     */
    object EmptyName : NameViewState()

    /**
     * Show continue
     */
    object Continue : NameViewState()

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [EmptyName].
     *
     * @return True if is empty state, otherwise false.
     */
    fun isEmptyName() = this is EmptyName

    /**
     * Check if current view state is [Continue].
     *
     * @return True if is empty state, otherwise false.
     */
    fun isContinue() = this is Continue
}
