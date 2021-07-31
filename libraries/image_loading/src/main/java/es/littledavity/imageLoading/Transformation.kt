/*
 * Copyright 2021 dev.id
 */
package es.littledavity.imageLoading

import android.graphics.Bitmap

interface Transformation {

    val key: String

    fun transform(source: Bitmap): Bitmap

}
