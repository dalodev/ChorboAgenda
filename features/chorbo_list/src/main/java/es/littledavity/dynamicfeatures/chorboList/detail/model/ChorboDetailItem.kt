/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail.model

import android.net.Uri

data class ChorboDetailItem(
    val id: Long,
    var name: String,
    var image: Uri?,
    val info: List<Info>
)






