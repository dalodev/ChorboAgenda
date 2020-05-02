package es.littledavity.dynamicfeatures.home.adapter

/**
 * Different states for [ChorbosListAdapter].
 *
 * @see BaseViewState
 */
sealed class ChorboListAdapterState(
    val hasExtraRow: Boolean
) {

    /**
     * Listed the added chorbos into list.
     */
    object Added : ChorboListAdapterState(hasExtraRow = true)

    /**
     * Loading for new chorbos to add into list.
     */
    object AddLoading : ChorboListAdapterState(hasExtraRow = true)

    /**
     * Error on add new chorbos into list.
     */
    object AddError : ChorboListAdapterState(hasExtraRow = true)

    /**
     * No more chorbos to add into list.
     */
    object NoMore : ChorboListAdapterState(hasExtraRow = false)

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

    /**
     * Check if current view state is [NoMore].
     *
     * @return True if is no more elements state, otherwise false.
     */
    fun isNoMore() = this is NoMore
}