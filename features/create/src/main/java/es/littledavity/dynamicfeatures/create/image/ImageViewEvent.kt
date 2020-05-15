/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.image

/**
 * Different interaction events for [ImageFragment].
 */
sealed class ImageViewEvent {

    /**
     * Open chorbo image view.
     */
    object Next : ImageViewEvent()
}
