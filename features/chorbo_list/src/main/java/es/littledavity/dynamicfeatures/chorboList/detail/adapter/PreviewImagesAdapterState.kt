package es.littledavity.dynamicfeatures.chorboList.detail.adapter

import es.littledavity.commons.ui.base.BaseViewState
import es.littledavity.dynamicfeatures.chorboList.list.adapter.ChorbosListAdapter

/**
 * Different states for [ChorbosListAdapter].
 *
 * @see BaseViewState
 */
sealed class PreviewImagesAdapterState(
    val hasExtraRow: Boolean
) : BaseViewState {

    /**
     * Listed the added chorbos into list.
     */
    object Added : PreviewImagesAdapterState(hasExtraRow = true)

    /**
     * Loading for new chorbos to add into list.
     */
    object AddLoading : PreviewImagesAdapterState(hasExtraRow = true)

    /**
     * Error on add new chorbos into list.
     */
    object AddError : PreviewImagesAdapterState(hasExtraRow = true)

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
     * Check if current view state is [AddLoading].
     *
     * @return True if is add loading state, otherwise false.
     */
    fun isAddLoading() = this is AddLoading

    /**
     * Check if current view state is [AddError].
     *
     * @return True if is add error state, otherwise false.
     */
    fun isAddError() = this is AddError
}