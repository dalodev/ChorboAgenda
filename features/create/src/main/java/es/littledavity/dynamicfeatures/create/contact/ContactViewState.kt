package es.littledavity.dynamicfeatures.create.contact

import es.littledavity.commons.ui.base.BaseViewState

/**
 * Different states for [ContactFragment].
 *
 * @see BaseViewState
 */
sealed class ContactViewState : BaseViewState {

    /**
     * Show continue
     */
    object Continue : ContactViewState()

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [Continue].
     *
     * @return True if is empty state, otherwise false.
     */
    fun isContinue() = this is Continue
}