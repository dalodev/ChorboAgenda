/*
 * Copyright 2021 dev.id
 */
package es.littledavity.imageLoading

import android.graphics.Bitmap
import com.squareup.picasso.Transformation
import es.littledavity.imageLoading.Transformation as MyTransformation

internal class PicassoTransformation(
    private val transformation: MyTransformation
) : Transformation {


    override fun transform(source: Bitmap): Bitmap {
        return transformation.transform(source)
    }


    override fun key(): String {
        return transformation.key
    }


}
