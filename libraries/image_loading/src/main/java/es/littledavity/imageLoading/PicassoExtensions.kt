/*
 * Copyright 2021 dev.id
 */
package es.littledavity.imageLoading

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.RequestCreator

internal fun RequestCreator.into(
    target: ImageView,
    onSuccess: (() -> Unit)? = null,
    onFailure: ((Exception) -> Unit)? = null
) {
    val callback = object : Callback {

        override fun onSuccess() {
            onSuccess?.invoke()
        }

        override fun onError(error: java.lang.Exception) {
            onFailure?.invoke(error)
        }
    }

    into(target, callback)
}
