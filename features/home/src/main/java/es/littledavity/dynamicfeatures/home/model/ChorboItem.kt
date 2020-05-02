package es.littledavity.dynamicfeatures.home.model

import es.littledavity.dynamicfeatures.home.HomeFragment

/**
 * Model view to display on the screen [HomeFragment].
 */
data class ChorboItem(
    val id: Long,
    val name: String,
    val description: String
)