/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.list

/**
 * Different interaction events for [ChorboListFragment].
 */
sealed class ChorboListViewEvent {

    /**
     * Open add chorbo options view.
     */
    object OpenChorboOptions : ChorboListViewEvent()
}
