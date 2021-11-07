/*
 * Copyright 2021 dalodev
 */
package es.littledavity.imageLoading

import android.graphics.drawable.Drawable
import android.widget.ImageView

class Config private constructor(
    val shouldCenterInside: Boolean,
    val shouldCenterCrop: Boolean,
    val shouldFit: Boolean,
    val targetWidth: Int,
    val targetHeight: Int,
    val imageUrl: String,
    val progressDrawable: Drawable?,
    val errorDrawable: Drawable?,
    val target: ImageView,
    val transformations: List<Transformation>,
    val onStart: (() -> Unit)?,
    val onSuccess: (() -> Unit)?,
    val onFailure: ((Exception) -> Unit)?
) {

    internal val hasTargetSize: Boolean
        get() = targetWidth > 0 && targetHeight > 0

    internal val hasTransformations: Boolean
        get() = transformations.isNotEmpty()

    internal val hasAtLeastOneResultListener: Boolean
        get() = onSuccess != null || onFailure != null

    class Builder {

        private var shouldCenterInside: Boolean = false
        private var shouldCenterCrop: Boolean = false
        private var shouldFit: Boolean = false
        private var targetWidth: Int = 0
        private var targetHeight: Int = 0
        private var imageUrl: String = ""
        private var progressDrawable: Drawable? = null
        private var errorDrawable: Drawable? = null
        private var target: ImageView? = null
        private var transformations: MutableList<Transformation> = mutableListOf()
        private var onStart: (() -> Unit)? = null
        private var onSuccess: (() -> Unit)? = null
        private var onFailure: ((Exception) -> Unit)? = null

        fun centerCrop() = apply { shouldCenterCrop = true }

        fun centerInside() = apply { shouldCenterInside = true }

        fun fit() = apply { shouldFit = true }

        fun resize(targetWidth: Int, targetHeight: Int) = apply {
            require(targetWidth > 0) { "The width must be larger tha 0." }
            require(targetHeight > 0) { "The height must be larger than 0." }

            this.targetWidth = targetWidth
            this.targetHeight = targetHeight
        }

        fun imageUrl(imageUrl: String) = apply {
            require(imageUrl.isNotBlank()) { "The image url is blank" }

            this.imageUrl = imageUrl
        }

        fun progressDrawable(drawable: Drawable) = apply { progressDrawable = drawable }

        fun errorDrawable(drawable: Drawable) = apply { errorDrawable = drawable }

        fun target(target: ImageView) = apply { this.target = target }

        fun transformation(transformation: Transformation) = apply {
            transformations.add(transformation)
        }

        fun transformations(transformations: List<Transformation>) = apply {
            this.transformations.addAll(transformations)
        }

        fun onStart(action: () -> Unit) = apply { onStart = action }

        fun onSuccess(action: () -> Unit) = apply { onSuccess = action }

        fun onFailure(action: (Exception) -> Unit) = apply { onFailure = action }

        fun build(): Config {
            check(imageUrl.isNotEmpty()) { "Image url is not set." }
            check(target != null) { "Destination ImageView is not set." }

            return Config(
                shouldCenterInside = shouldCenterInside,
                shouldCenterCrop = shouldCenterCrop,
                shouldFit = shouldFit,
                targetWidth = targetWidth,
                targetHeight = targetHeight,
                imageUrl = imageUrl,
                progressDrawable = progressDrawable,
                errorDrawable = errorDrawable,
                target = checkNotNull(target),
                transformations = transformations,
                onStart = onStart,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }
    }
}
