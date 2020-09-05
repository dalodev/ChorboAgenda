/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.contact

import es.littledavity.commons.ui.base.BaseViewState

/**
 * Different states for [ContactFragment].
 *
 * @see BaseViewState
 */
sealed class ContactViewState : BaseViewState {

    /**
     * Show Error
     */
    data class Error(val message: String?) : ContactViewState()

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [Error].
     *
     * @return True if is empty state, otherwise false.
     */
    fun isError() = this is Error
}
