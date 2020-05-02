package es.littledavity.dynamicfeatures.home

import es.littledavity.commons.ui.base.BaseViewState

/**
 * Different states for [HomeFragment].
 *
 * @see BaseViewState
 */
sealed class HomeViewState : BaseViewState {

    /**
     * Refreshing chorbo list.
     */
    object Refreshing : HomeViewState()

    /**
     * Loaded chorbo list.
     */
    object Loaded : HomeViewState()

    /**
     * Loading chorbo list.
     */
    object Loading : HomeViewState()

    /**
     * Loading on add more elements into chorbo list.
     */
    object AddLoading : HomeViewState()

    /**
     * Empty chorbo list.
     */
    object Empty : HomeViewState()

    /**
     * Error on loading chorbo list.
     */
    object Error : HomeViewState()

    /**
     * Error on add more elements into chorbo list.
     */
    object AddError : HomeViewState()

    /**
     * No more elements for adding into chorbo list.
     */
    object NoMoreElements : HomeViewState()

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