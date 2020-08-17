/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.list.model

import es.littledavity.dynamicfeatures.chorboList.list.ChorboListFragment

/**
 * Model view to display on the screen [ChorboListFragment].
 */
data class ChorboItem(
    val id: Long,
    val name: String,
    val image: String,
    var isSelected: Boolean
)
