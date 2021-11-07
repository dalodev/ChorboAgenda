/*
 * Copyright 2021 dalodev
 */
package es.littledavity.imageLoading

import android.graphics.Bitmap
import com.squareup.picasso.Transformation
import es.littledavity.imageLoading.Transformation as MyTransformation

internal class PicassoTransformation(
    private val transformation: MyTransformation
) : Transformation {

    override fun transform(source: Bitmap) = transformation.transform(source)

    override fun key() = transformation.key
}
