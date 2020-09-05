package es.littledavity.dynamicfeatures.chorboList.detail

import es.littledavity.commons.ui.base.BaseViewState
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailItem

/**
 * Different states for [ChorboDetailFragment].
 *
 * @see BaseViewState
 */
sealed class ChorboDetailViewState : BaseViewState {

    /**
     * Loaded chorbo detail.
     */
    data class Loaded(val chorbo: ChorboDetailItem) : ChorboDetailViewState()

    /**
     * Check if current view state is [Loaded].
     *
     * @return True if is loaded state, otherwise false.
     */
    fun isLoaded() = this is Loaded
}
