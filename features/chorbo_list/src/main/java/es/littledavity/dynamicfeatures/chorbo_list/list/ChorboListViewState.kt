package es.littledavity.dynamicfeatures.chorbo_list.list

import es.littledavity.commons.ui.base.BaseViewState

/**
 * Different states for [ChorboListFragment].
 *
 * @see BaseViewState
 */
sealed class ChorboListViewState : BaseViewState {

    /**
     * Refreshing chorbo list.
     */
    object Refreshing : ChorboListViewState()

    /**
     * Loaded chorbo list.
     */
    object Loaded : ChorboListViewState()

    /**
     * Loading chorbo list.
     */
    object Loading : ChorboListViewState()

    /**
     * Loading on add more elements into chorbo list.
     */
    object AddLoading : ChorboListViewState()

    /**
     * Empty chorbo list.
     */
    object Empty : ChorboListViewState()

    /**
     * Error on loading chorbo list.
     */
    object Error : ChorboListViewState()

    /**
     * Error on add more elements into chorbo list.
     */
    object AddError : ChorboListViewState()

    /**
     * No more elements for adding into chorbo list.
     */
    object NoMoreElements : ChorboListViewState()

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [Refreshing].
     *
     * @return True if is refreshing state, otherwise false.
     */
    fun isRefreshing() = this is Refreshing

    /**
     * Check if current view state is [Loaded].
     *
     * @return True if is loaded state, otherwise false.
     */
    fun isLoaded() = this is Loaded

    /**
     * Check if current view state is [Loading].
     *
     * @return True if is loading state, otherwise false.
     */
    fun isLoading() = this is Loading

    /**
     * Check if current view state is [AddLoading].
     *
     * @return True if is add loading state, otherwise false.
     */
    fun isAddLoading() = this is AddLoading

    /**
     * Check if current view state is [Empty].
     *
     * @return True if is empty state, otherwise false.
     */
    fun isEmpty() = this is Empty

    /**
     * Check if current view state is [Error].
     *
     * @return True if is error state, otherwise false.
     */
    fun isError() = this is Error

    /**
     * Check if current view state is [AddError].
     *
     * @return True if is add error state, otherwise false.
     */
    fun isAddError() = this is AddError

    /**
     * Check if current view state is [NoMoreElements].
     *
     * @return True if is no more elements state, otherwise false.
     */
    fun isNoMoreElements() = this is NoMoreElements
}