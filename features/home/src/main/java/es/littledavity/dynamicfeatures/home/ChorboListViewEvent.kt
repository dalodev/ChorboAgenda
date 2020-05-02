package es.littledavity.dynamicfeatures.home

/**
 * Different interaction events for [HomeFragment].
 */
sealed class ChorboListViewEvent {

    /**
     * Open chorbo detail view.
     *
     * @param id chorbo identifier
     */
    data class OpenChorboDetail(val id: Long) : ChorboListViewEvent()

    /**
     * Open add chorbo options view.
     */
    object OpenChorboOptions : ChorboListViewEvent()
}