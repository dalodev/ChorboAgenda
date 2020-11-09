package es.littledavity.dynamicfeatures.chorboList.detail.adapter

import es.littledavity.commons.ui.base.BaseViewState
import es.littledavity.dynamicfeatures.chorboList.list.adapter.ChorbosListAdapter

/**
 * Different states for [ChorbosListAdapter].
 *
 * @see BaseViewState
 */
sealed class ChorboDetailAdapterState(
    val hasExtraRow: Boolean
) : BaseViewState {

    /**
     * Listed the added chorbos into list.
     */
    object Added : ChorboDetailAdapterState(hasExtraRow = false)

    /**
     * Error on add new chorbos into list.
     */
    object AddError : ChorboDetailAdapterState(hasExtraRow = true)

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [Added].
     *
     * @return True if is added state, otherwise false.
     */
    fun isAdded() = this is Added

    /**
     * Check if current view state is [AddError].
     *
     * @return True if is add error state, otherwise false.
     */
    fun isAddError() = this is AddError
}