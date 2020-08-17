/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.list

/**
 * Different interaction events for [ChorboListFragment].
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
