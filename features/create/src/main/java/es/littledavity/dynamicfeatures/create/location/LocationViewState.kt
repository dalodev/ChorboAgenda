package es.littledavity.dynamicfeatures.create.location

import es.littledavity.commons.ui.base.BaseViewState

/**
 * Different states for [LocationFragment].
 *
 * @see BaseViewState
 */
sealed class LocationViewState : BaseViewState {

    /**
     * Show continue
     */
    object Continue: LocationViewState()

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